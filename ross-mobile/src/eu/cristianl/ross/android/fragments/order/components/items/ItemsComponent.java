package eu.cristianl.ross.android.fragments.order.components.items;

import java.util.List;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import eu.cristianl.ross.R;
import eu.cristianl.ross.android.adapters.paging.PagingAdapter;
import eu.cristianl.ross.android.fragments.order.OrderFragment;
import eu.cristianl.ross.android.fragments.order.components.OrderComponent;
import eu.cristianl.ross.android.utils.Resources;
import eu.cristianl.ross.android.widgets.Console;
import eu.cristianl.ross.android.widgets.ListHeader;
import eu.cristianl.ross.android.widgets.SingleChoice;
import eu.cristianl.ross.dal.CategoryDal;
import eu.cristianl.ross.dal.OrderDal;
import eu.cristianl.ross.dal.OrderRowDal;
import eu.cristianl.ross.entities.Category;
import eu.cristianl.ross.entities.Item;
import eu.cristianl.ross.entities.Order;
import eu.cristianl.ross.entities.OrderRow;
import eu.cristianl.ross.exceptions.AppException;
import eu.cristianl.ross.logging.AppLogger;
import eu.cristianl.ross.utils.Utils;

public class ItemsComponent extends OrderComponent implements View.OnClickListener {
	public static final String TAG = "ItemsComponent";

	private TextView mCategoryView;

	private ItemsListAdapter mAdapter;
	private List<Category> mCategories;

	public ItemsComponent(OrderFragment parentFragment) {
		super(TAG, parentFragment);
		mAdapter = new ItemsListAdapter(mParentFragment.getActivity(), mParentFragment.getOrderRows());
		mCategories = CategoryDal.getDal().getAllCategories();
	}

	@Override
	public View createTabContent(String tag) {
		View retView = inflateLayout(R.layout.fragment_order_items_component);

		setupItemsListHeader(retView);
		setupItemsListView(retView);
		setupCategoryView(retView);

		return retView;
	}

	@Override
	public void onComponentSelected(Object arg) {
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public String getTitle() {
		return Resources.getString(R.string.fragment_order_item_list_tab);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fragment_order_items_component_categories:
			SingleChoice.get(mCategories, getCategoryFeedback()).show();
			break;
		case R.id.fragment_order_items_component_category_remove:
			resetCategory();
			break;
		}
	}

	public void setSearchFilter(String query) {
		if (!Utils.isEmptyOrBlank(query)) {
			mAdapter.setSearchFilter(query);
			mAdapter.resetAdapter();
		}
	}

	private void setupItemsListHeader(View parent) {
		ListHeader listheader = (ListHeader) parent.findViewById(R.id.fragment_order_items_component_list_header);
		listheader.setSortTypeChangeListener(new ListHeader.SortTypeChangeListener() {
			@Override
			public void onSortTypeChanged(int columnId, boolean asc) {
				mAdapter.reorderList(columnId, asc);
			}
		});
	}

	private void setupItemsListView(View parent) {
		ListView listView = (ListView) parent.findViewById(R.id.fragment_order_items_component_list);

		PagingAdapter.registerAdapter(listView, mAdapter);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View row, int position, long id) {
				Item item = (Item) adapterView.getItemAtPosition(position);

				if (manageListItemClick(item)) {
					mAdapter.getView(position, row, null);
				} else {
					Console.get(Resources.getString(R.string.fragment_order_items_add_error)).error();
				}
			}
		});
	}

	private void setupCategoryView(View parent) {
		parent.findViewById(R.id.fragment_order_items_component_categories).setOnClickListener(this);
		parent.findViewById(R.id.fragment_order_items_component_category_remove).setOnClickListener(this);

		mCategoryView = (TextView) parent.findViewById(R.id.fragment_order_items_component_category);
		refreshCategoryView(null);
	}

	private void refreshCategoryView(Category category) {
		if (category != null) {
			mCategoryView.setText(category.getDescription());
		} else {
			mCategoryView.setText(R.string.fragment_order_items_category);
		}
	}

	private void resetCategory() {
		refreshCategoryView(null);
		mAdapter.setCategoryFilter(null);
		mAdapter.resetAdapter();
	}

	/** Return true if item is in order list, false if cannot be added */
	private boolean manageListItemClick(Item item) {
		boolean retValue = false;
		OrderRow newOrderRow = findByItem(item);
		if (newOrderRow == null) {
			newOrderRow = addOrderRow(item);
			if (newOrderRow != null) {
				retValue = true;
			}
		}

		mParentFragment.setCurrentRow(newOrderRow);
		return retValue;
	}

	private OrderRow addOrderRow(Item item) {
		Order order = mParentFragment.getOrder();
		try {
			// create and save a new order row
			OrderRow newOrderRow = OrderRowDal.getDal().newOrderRow(order, item);
			// Add it to our order and save
			OrderDal.getDal().addOrderRow(order, newOrderRow);

			return newOrderRow;
		} catch (AppException e) {
			AppLogger.error(e, e.getMessage());
		}
		return null;
	}

	private OrderRow findByItem(Item item) {
		for (OrderRow orderRow : mParentFragment.getOrderRows()) {
			if (orderRow.getItem().equals(item)) {
				return orderRow;
			}
		}
		return null;
	}

	private SingleChoice.IFeedbackListener<Category> getCategoryFeedback() {
		return new SingleChoice.IFeedbackListener<Category>() {
			@Override
			public void doFeedback(Category arg) {
				refreshCategoryView(arg);
				mAdapter.setCategoryFilter(arg.getId());
				mAdapter.resetAdapter();
			}
		};
	}

}
