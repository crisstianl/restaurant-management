package eu.cristianl.ross.android.fragments.docs;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import eu.cristianl.ross.R;
import eu.cristianl.ross.android.fragments.GenericFragment;
import eu.cristianl.ross.android.fragments.docs.DocsListAdapter.AdapterItem;
import eu.cristianl.ross.android.fragments.docs.renderers.OrderRenderer;
import eu.cristianl.ross.android.services.orderService.OrderSeed;
import eu.cristianl.ross.android.services.orderService.OrderUpdateService;
import eu.cristianl.ross.android.services.orderService.OrderUploadService;
import eu.cristianl.ross.android.widgets.ListHeader;
import eu.cristianl.ross.android.widgets.Search;
import eu.cristianl.ross.dal.OrderDal;
import eu.cristianl.ross.entities.Order;
import eu.cristianl.ross.entities.support.DocStatusType;
import eu.cristianl.ross.utils.Utils;

public class DocsListFragment extends GenericFragment {
	public static final String DOC_STATUS_KEY = "DOC_STATUS_KEY";

	private UploadServiceReceiver mUploadServiceRec;

	private DocsListAdapter mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DocStatusType docStatus = DocStatusType.getByCode(getArguments().getString(DOC_STATUS_KEY));
		OrderRenderer.Factory factory = new OrderRenderer.Factory();

		mAdapter = new DocsListAdapter(docStatus, factory.getOrderRenderer(getActivity(), docStatus));
		mUploadServiceRec = new UploadServiceReceiver();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View retView = inflater.inflate(R.layout.fragment_docs_list, null);

		setupDocsList(retView);
		setupDocsListHeader(retView);
		mAdapter.getViewRenderer().onCreateOrderActions(retView, new OrdersManager());

		return retView;
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		if (mAdapter.getDocStatus() == DocStatusType.PENDING) {
			menu.findItem(R.id.main_options_menu_pending).setVisible(false);
		} else if (mAdapter.getDocStatus() == DocStatusType.READY) {
			menu.findItem(R.id.main_options_menu_ready).setVisible(false);
		} else if (mAdapter.getDocStatus() == DocStatusType.SUBMITTED) {
			menu.findItem(R.id.main_options_menu_inprogress).setVisible(false);
		}
	}

	@Override
	protected void search() {
		Search.get(getSearchFeedback()).show();
	}

	@Override
	public void onPause() {
		super.onPause();
		if (mUploadServiceRec.isRegistered) {
			getActivity().unregisterReceiver(mUploadServiceRec);
		}
	}

	private Search.IFeedbackListener getSearchFeedback() {
		return new Search.IFeedbackListener() {
			@Override
			public void doFeedback(String text) {
				mAdapter.setSearchFilter(text);
				mAdapter.reset();
			}
		};
	}

	private void setupDocsList(View parent) {
		ListView listview = (ListView) parent.findViewById(R.id.fragment_docs_list);
		listview.setAdapter(mAdapter);
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View row, int position, long id) {
				DocsListAdapter.AdapterItem item = (AdapterItem) adapterView.getItemAtPosition(position);
				item.setSelected(!item.isSelected());

				mAdapter.notifyDataSetChanged();
			}
		});
	}

	private void setupDocsListHeader(View parent) {
		ListHeader listheader = (ListHeader) parent.findViewById(R.id.fragment_docs_list_header);
		listheader.setSortTypeChangeListener(new ListHeader.SortTypeChangeListener() {
			@Override
			public void onSortTypeChanged(int columnId, boolean asc) {
				mAdapter.sort(columnId, asc);
			}
		});
	}

	public class OrdersManager {

		public void synchronizeOrders() {
			List<Order> orders = OrderDal.getDal().getOrdersSortedById(DocStatusType.SUBMITTED.getCode(), null, 100);
			if (orders == null || orders.isEmpty()) {
				return;
			}

			List<String> docIds = new ArrayList<String>(orders.size());
			for (Order order : orders) {
				docIds.add(order.getId());
			}

			getActivity().registerReceiver(mUploadServiceRec, new IntentFilter(OrderUpdateService.ACTION));
			mUploadServiceRec.isRegistered = true;

			Intent service = new Intent(getActivity(), OrderUpdateService.class);
			service.putExtra("ACTION", OrderUpdateService.ACTION_GET);
			service.putExtra(OrderUpdateService.INTENT_SEED_ID,
					new OrderSeed(docIds, DocStatusType.SUBMITTED.getCode()));
			getActivity().startService(service);
		}

		public void deleteOrders() {
			for (Order order : mAdapter.getSelectedOrders()) {
				OrderDal.getDal().deleteOrder(order);
			}

			mAdapter.removeSelectedRows();
			mAdapter.notifyDataSetChanged();
		}

		public void sendOrders() {
			List<Order> orders = mAdapter.getSelectedOrders();
			if (Utils.isEmptyOrNull(orders)) {
				return;
			}
			List<String> docIds = new ArrayList<String>(orders.size());
			for (Order order : orders) {
				docIds.add(order.getId());
			}

			getActivity().registerReceiver(mUploadServiceRec, new IntentFilter(OrderUploadService.ACTION));
			mUploadServiceRec.isRegistered = true;

			Intent service = new Intent(getActivity(), OrderUploadService.class);
			service.putExtra(OrderUploadService.INTENT_SEED_ID, new OrderSeed(docIds, null));
			getActivity().startService(service);
		}

		public void closeOrders() {
			List<Order> orders = mAdapter.getSelectedOrders();
			if (Utils.isEmptyOrNull(orders)) {
				return;
			}
			List<String> docIds = new ArrayList<String>(orders.size());
			for (Order order : orders) {
				docIds.add(order.getId());
			}

			getActivity().registerReceiver(mUploadServiceRec, new IntentFilter(OrderUpdateService.ACTION));
			mUploadServiceRec.isRegistered = true;

			Intent service = new Intent(getActivity(), OrderUpdateService.class);
			service.putExtra("ACTION", OrderUpdateService.ACTION_PUT);
			service.putExtra(OrderUpdateService.INTENT_SEED_ID, new OrderSeed(docIds, DocStatusType.CLOSED.getCode()));
			getActivity().startService(service);
		}
	}

	private class UploadServiceReceiver extends BroadcastReceiver {
		boolean isRegistered = false;

		@Override
		public void onReceive(Context context, Intent intent) {
			String orderId = intent.getStringExtra(OrderUploadService.MESSAGE_KEY);
			if (orderId != null) {
				mAdapter.removeRow(orderId);
				mAdapter.notifyDataSetChanged();
			} else {
				mAdapter.reset();
			}
		}
	}

}
