package eu.cristianl.ross.android.fragments.order.components.sync;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import eu.cristianl.ross.R;
import eu.cristianl.ross.android.fragments.order.OrderFragment;
import eu.cristianl.ross.android.fragments.order.components.OrderComponent;
import eu.cristianl.ross.android.fragments.order.components.itemdetails.ItemDetailsComponent;
import eu.cristianl.ross.android.services.orderService.OrderSeed;
import eu.cristianl.ross.android.services.orderService.OrderUploadService;
import eu.cristianl.ross.android.utils.Resources;
import eu.cristianl.ross.android.widgets.Console;
import eu.cristianl.ross.android.widgets.Question;
import eu.cristianl.ross.dal.OrderDal;
import eu.cristianl.ross.dal.OrderRowDal;
import eu.cristianl.ross.entities.OrderRow;
import eu.cristianl.ross.utils.Utils;

public class SyncComponent extends OrderComponent implements View.OnClickListener {
	public static final String TAG = "SyncComponent";

	private TextView mTotalView;
	private SyncListAdapter mAdapter;

	public SyncComponent(OrderFragment parentFragment) {
		super(TAG, parentFragment);
		mAdapter = new SyncListAdapter(parentFragment.getActivity(), parentFragment.getOrderRows());
	}

	@Override
	public View createTabContent(String tag) {
		View retView = inflateLayout(R.layout.fragment_order_sync_component);

		setupContactView(retView);
		setupPartyView(retView);
		setupTotalView(retView);
		setupButtonsView(retView);
		setupListView(retView);

		return retView;
	}

	@Override
	public void onComponentSelected(Object arg) {
		refreshViews();
	}

	@Override
	public String getTitle() {
		return Resources.getString(R.string.fragment_order_item_sync_tab);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fragment_order_sync_cancel:
			Question.get(Resources.getString(R.string.fragment_order_sync_delete_order_warning),
					Resources.getString(R.string.dictionary_yes), Resources.getString(R.string.dictionary_no),
					getQuestionFeedback()).show();
			break;

		case R.id.fragment_order_sync_pend:
			pendOrder();
			break;

		case R.id.fragment_order_sync_send:
			sendOrder();
			break;
		}

	}

	private void pendOrder() {
		if (mParentFragment.getOrderRows().isEmpty()) {
			Console.get(Resources.getString(R.string.fragment_order_sync_pend_send_no_rows_error)).warn();
			return;
		}

		OrderDal.getDal().markOrderAsPend(mParentFragment.getOrder());
		mParentFragment.closeFragment();
	}

	private void sendOrder() {
		if (mParentFragment.getOrderRows().isEmpty()) {
			Console.get(Resources.getString(R.string.fragment_order_sync_pend_send_no_rows_error)).warn();
			return;
		}

		OrderDal.getDal().markOrderAsPend(mParentFragment.getOrder());

		Intent service = new Intent(mParentFragment.getActivity(), OrderUploadService.class);
		service.putExtra(OrderUploadService.INTENT_SEED_ID, new OrderSeed(mParentFragment.getOrder().getId()));
		mParentFragment.getActivity().startService(service);

		mParentFragment.closeFragment();
	}

	private Question.IFeedbackListener getQuestionFeedback() {
		return new Question.IFeedbackListener() {
			@Override
			public void doFeedback(boolean arg) {
				if (arg) {
					OrderDal.getDal().deleteOrder(mParentFragment.getOrder());
				}
				mParentFragment.closeFragment();
			}
		};
	}

	public boolean removeOrderRow(int position) {
		OrderRow orderRow = mAdapter.getItem(position);
		// Delete from database
		boolean success = OrderRowDal.getDal().deleteOrderRow(orderRow);
		// Update order only on success, else leave it as it is
		if (success) {
			// Update order prices, and rows list
			OrderDal.getDal().deleteOrderRow(mParentFragment.getOrder(), orderRow);

			// Change the current row, if it is the same with the deleted one
			if (mParentFragment.getCurrentRow().equals(orderRow)) {
				if (!mParentFragment.getOrderRows().isEmpty()) {
					mParentFragment.setCurrentRow(mParentFragment.getOrderRows().get(0));
				} else {
					mParentFragment.setCurrentRow(null);
				}
			}

			// Refresh views
			refreshViews();

		} else {
			Console.get(Resources.getString(R.string.fragment_order_sync_delete_error)).error();
		}
		return success;
	}

	private void refreshViews() {
		mTotalView.setText(Utils.format(mParentFragment.getOrder().getTotal()));
		mAdapter.notifyDataSetChanged();
	}

	private void setupContactView(View parent) {
		TextView textview = (TextView) parent.findViewById(R.id.fragment_order_sync_header_contact);
		textview.setText(mParentFragment.getOrder().getContact().getName());
	}

	private void setupPartyView(View parent) {
		TextView textview = (TextView) parent.findViewById(R.id.fragment_order_sync_header_party);
		textview.setText(String.valueOf(mParentFragment.getOrder().getContact().getConvFactor()));
	}

	private void setupTotalView(View parent) {
		mTotalView = (TextView) parent.findViewById(R.id.fragment_order_sync_header_total);
	}

	private void setupListView(View parent) {
		ListView listView = (ListView) parent.findViewById(R.id.fragment_order_sync_list);
		listView.setAdapter(mAdapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View row, int position, long id) {
				mParentFragment.setCurrentRow((OrderRow) adapterView.getItemAtPosition(position));
				mParentFragment.displayComponent(ItemDetailsComponent.TAG);
			}
		});

		mParentFragment.registerForContextMenu(listView);
	}

	private void setupButtonsView(View parent) {
		parent.findViewById(R.id.fragment_order_sync_cancel).setOnClickListener(this);
		parent.findViewById(R.id.fragment_order_sync_pend).setOnClickListener(this);
		parent.findViewById(R.id.fragment_order_sync_send).setOnClickListener(this);
	}
}
