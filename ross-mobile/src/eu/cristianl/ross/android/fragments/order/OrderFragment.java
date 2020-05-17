package eu.cristianl.ross.android.fragments.order;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.TabHost;
import eu.cristianl.ross.R;
import eu.cristianl.ross.android.RossApplication;
import eu.cristianl.ross.android.fragments.GenericFragment;
import eu.cristianl.ross.android.fragments.order.components.OrderComponent;
import eu.cristianl.ross.android.fragments.order.components.itemdetails.ItemDetailsComponent;
import eu.cristianl.ross.android.fragments.order.components.items.ItemsComponent;
import eu.cristianl.ross.android.fragments.order.components.sync.SyncComponent;
import eu.cristianl.ross.android.utils.Resources;
import eu.cristianl.ross.android.widgets.Console;
import eu.cristianl.ross.android.widgets.Question;
import eu.cristianl.ross.android.widgets.Search;
import eu.cristianl.ross.dal.OrderDal;
import eu.cristianl.ross.dal.OrderRowDal;
import eu.cristianl.ross.entities.Order;
import eu.cristianl.ross.entities.OrderRow;
import eu.cristianl.ross.exceptions.AppException;
import eu.cristianl.ross.logging.AppLogger;
import eu.cristianl.ross.utils.Utils;

public class OrderFragment extends GenericFragment implements TabHost.OnTabChangeListener {
	public static final String CONTACT_ID_KEY = "CONTACT_ID_KEY";

	private Map<String, OrderComponent> mComponents = new LinkedHashMap<String, OrderComponent>();
	private TabHost mTabHost;

	private Order mOrder;
	private OrderRow mCurrentRow;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		loadOrder(getArguments().getInt(CONTACT_ID_KEY));
		loadOrderComponents();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		View retView = inflater.inflate(R.layout.fragment_order, null);
		setupTabHost(retView);

		return retView;
	}

	@Override
	public void onTabChanged(String tabTag) {
		OrderComponent component = mComponents.get(tabTag);
		component.onComponentSelected(null);
	}

	@Override
	public boolean backContent() {
		if (mTabHost.getCurrentTab() > 0) {
			mTabHost.setCurrentTab(mTabHost.getCurrentTab() - 1);
		} else {
			final Question.IFeedbackListener callback = new Question.IFeedbackListener() {
				@Override
				public void doFeedback(boolean arg) {
					if (arg)
						mNavigator.backContent();
				}
			};
			Question.get(Resources.getString(R.string.fragment_order_exit_warning),
					Resources.getString(R.string.dictionary_yes), Resources.getString(R.string.dictionary_no), callback)
					.show();
		}
		return true;
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		menu.findItem(R.id.main_options_menu_new).setVisible(false);
	}

	@Override
	protected void search() {
		Search.get(getSearchFeedback()).show();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		getActivity().getMenuInflater().inflate(R.menu.sync_component_context_menu, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.sync_component_context_menu_delete) {
			AdapterView.AdapterContextMenuInfo menu = (AdapterContextMenuInfo) item.getMenuInfo();
			SyncComponent component = (SyncComponent) mComponents.get(SyncComponent.TAG);
			component.removeOrderRow(menu.position);

		} else {
			return super.onContextItemSelected(item);
		}
		return true;
	}

	public void closeFragment() {
		goToHome();
	}

	public void displayComponent(String tag) {
		mTabHost.setCurrentTabByTag(tag);
	}

	public Order getOrder() {
		return mOrder;
	}

	public List<OrderRow> getOrderRows() {
		return mOrder.getOrderRows();
	}

	public OrderRow getCurrentRow() {
		if (mCurrentRow == null && !Utils.isEmptyOrNull(mOrder.getOrderRows())) {
			mCurrentRow = mOrder.getOrderRows().get(0);
		}
		return mCurrentRow;
	}

	public void setCurrentRow(OrderRow orderRow) {
		mCurrentRow = orderRow;
	}

	private void setupTabHost(View parent) {
		mTabHost = (TabHost) parent;
		mTabHost.setup();
		mTabHost.setOnTabChangedListener(this);

		TabHost.TabSpec tab = null;
		for (OrderComponent component : mComponents.values()) {
			tab = mTabHost.newTabSpec(component.getTag());
			tab.setIndicator(component.getTitle());
			tab.setContent(component);
			mTabHost.addTab(tab);
		}
	}

	private void loadOrder(int contactId) {
		mOrder = OrderDal.getDal().getDraftOrder(contactId);

		if (mOrder != null) {
			final Question.IFeedbackListener callback = new Question.IFeedbackListener() {
				@Override
				public void doFeedback(boolean arg) {
					handleDraftReuse(arg);
				}
			};

			Question.get(Resources.getString(R.string.fragment_order_reuse_draft),
					Resources.getString(R.string.dictionary_yes), Resources.getString(R.string.dictionary_no), callback)
					.show();

		} else {
			createOrder(contactId);
		}
	}

	private void createOrder(int contactId) {
		try {
			final String userId = RossApplication.getInstance().getUserId();
			final String userName = RossApplication.getInstance().getUserName();
			mOrder = OrderDal.getDal().newOrder(contactId, userId, userName);
		} catch (AppException e) {
			AppLogger.error(e, e.getMessage());
			Console.get("Failed to create new order").error();
		}
	}

	private void handleDraftReuse(boolean reuse) {
		if (reuse) {
			List<OrderRow> orderRows = OrderRowDal.getDal().query(mOrder.getId());
			mOrder.getOrderRows().addAll(orderRows);
			mComponents.get(ItemsComponent.TAG).onComponentSelected(null);

		} else {
			OrderDal.getDal().deleteAllOrderRows(mOrder);
		}
	}

	private Search.IFeedbackListener getSearchFeedback() {
		return new Search.IFeedbackListener() {
			@Override
			public void doFeedback(String text) {
				if (mTabHost.getCurrentTabTag().equals(ItemsComponent.TAG)) {
					ItemsComponent component = (ItemsComponent) mComponents.get(ItemsComponent.TAG);
					component.setSearchFilter(text);
				}
			}
		};
	}

	private void loadOrderComponents() {
		mComponents.put(ItemsComponent.TAG, new ItemsComponent(this));
		mComponents.put(SyncComponent.TAG, new SyncComponent(this));
		mComponents.put(ItemDetailsComponent.TAG, new ItemDetailsComponent(this));
	}

}
