package eu.cristianl.ross.android.fragments.order.components.items;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import eu.cristianl.ross.R;
import eu.cristianl.ross.android.adapters.LazyListAdapter;
import eu.cristianl.ross.dal.ItemDal;
import eu.cristianl.ross.dal.ItemTagDal;
import eu.cristianl.ross.dal.iterators.ResultsIterator;
import eu.cristianl.ross.entities.Item;
import eu.cristianl.ross.entities.ItemTag;
import eu.cristianl.ross.entities.OrderRow;
import eu.cristianl.ross.entities.Tag;
import eu.cristianl.ross.utils.Utils;

public class ItemsListAdapter extends LazyListAdapter<Item> {
	private static final int ORDER_BY_ID = 0;
	private static final int ORDER_BY_TITLE = 1;
	private static final int ORDER_BY_CATEGORY = 2;
	private static final int ORDER_BY_PRICE = 3;
	private static final int ORDER_BY_STATE = 4;

	private static final int MAX_ITEM_TAGS = 2;

	private final Context mContext;

	// filters
	private String mFilterCategory;
	private String mFilterSearch;

	// order
	private int mOrderColumn;
	private boolean mOrderDesc;

	// data
	private Map<Item, List<Tag>> mItemTags = new TreeMap<Item, List<Tag>>(new ItemComparator());
	private List<OrderRow> m_SelectedRows;

	ItemsListAdapter(Context context, List<OrderRow> selectedRows) {
		this.mContext = context;
		m_SelectedRows = selectedRows;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView != null) {
			holder = (ViewHolder) convertView.getTag();
		} else {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_order_items_component_adapter, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}

		Item item = getItem(position);

		fillViewFolderWithData(holder, item);
		fillViewFolderExtrasWithData(holder, item);

		return convertView;
	}

	public void reorderList(int columnId, boolean asc) {
		this.mOrderColumn = columnId;
		this.mOrderDesc = !asc;
		resetAdapter();
	}

	public void setCategoryFilter(String cateogryId) {
		mFilterCategory = cateogryId;
		mFilterSearch = null;
	}

	public void setSearchFilter(String query) {
		mFilterSearch = query;
		mFilterCategory = null;
	}

	@Override
	protected ResultsIterator<Item> internalInitIterator() {
		// query tags
		queryForTags();

		// query items
		switch (mOrderColumn) {
		case ORDER_BY_TITLE:
			return ItemDal.getDal().getItemsOrderedByTitle(mFilterSearch, mFilterCategory, mOrderDesc, 100);
		case ORDER_BY_CATEGORY:
			return ItemDal.getDal().getItemsOrderedByCategory(mFilterSearch, mFilterCategory, mOrderDesc, 100);
		case ORDER_BY_PRICE:
			return ItemDal.getDal().getItemsOrderedByPrice(mFilterSearch, mFilterCategory, mOrderDesc, 100);
		case ORDER_BY_STATE:
		case ORDER_BY_ID:
		default:
			return ItemDal.getDal().getItemsOrderedById(mFilterSearch, mFilterCategory, 100);
		}
	}

	@Override
	public void resetAdapter() {
		mItemTags.clear();
		super.resetAdapter();
	}

	private void fillViewFolderWithData(ViewHolder holder, Item item) {
		holder.mIdView.setText(String.valueOf(item.getId()));
		holder.mTitleView.setText(item.getTitle());
		holder.mCategoryView.setText(item.getCategory().getDescription());
		holder.mPriceView.setText(
				Html.fromHtml(String.format("<b>%1$.2f</b> %2$s", item.getPrice(), item.getCurrency().getName())));
		holder.mCartIcon.setBackgroundResource(isSelected(item) ? R.drawable.ic_image_on : R.drawable.ic_image_off);
	}

	private void fillViewFolderExtrasWithData(ViewHolder holder, Item item) {
		holder.mTagsContainer.removeAllViews();

		List<Tag> tags = mItemTags.get(item);
		if (!Utils.isEmptyOrNull(tags)) {
			for (int i = 0; i < MAX_ITEM_TAGS && i < tags.size(); i++) {
				fillTag(holder.mTagsContainer, tags.get(i));
			}
		}
	}

	private void fillTag(LinearLayout parent, Tag tag) {
		TextView tagView = new TextView(parent.getContext());

		tagView.setText(tag.getDescription());
		tagView.setTextColor((int) tag.getForeground());
		tagView.setBackgroundColor((int) tag.getBackground());
		tagView.setLines(1);

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(90, LayoutParams.WRAP_CONTENT);
		params.setMargins(1, 1, 5, 1);
		parent.addView(tagView, params);
	}

	private boolean isSelected(Item item) {
		for (OrderRow orderRow : m_SelectedRows) {
			if (orderRow.getItem().equals(item)) {
				return true;
			}
		}
		return false;
	}

	private void queryForTags() {
		List<ItemTag> results = ItemTagDal.getDal().getAllItemTags();
		if (Utils.isEmptyOrNull(results)) {
			return;
		}

		for (ItemTag itemTag : results) {
			List<Tag> tags = mItemTags.get(itemTag.getItem());

			if (tags == null) {
				tags = new ArrayList<Tag>();
				mItemTags.put(itemTag.getItem(), tags);
			}

			tags.add(itemTag.getTag());
		}
	}

	private static class ItemComparator implements Comparator<Item> {
		@Override
		public int compare(Item arg0, Item arg1) {
			return arg0.getId() - arg1.getId();
		}
	}

	private static class ViewHolder {
		private TextView mIdView;
		private TextView mTitleView;
		private TextView mCategoryView;
		private TextView mPriceView;
		private ImageView mCartIcon;
		private LinearLayout mTagsContainer;

		private ViewHolder(View parent) {
			mIdView = (TextView) parent.findViewById(R.id.fragment_items_adapter_id);
			mTitleView = (TextView) parent.findViewById(R.id.fragment_items_adapter_title);
			mCategoryView = (TextView) parent.findViewById(R.id.fragment_items_adapter_category);
			mPriceView = (TextView) parent.findViewById(R.id.fragment_items_adapter_price);
			mCartIcon = (ImageView) parent.findViewById(R.id.fragment_items_adapter_cart);
			mTagsContainer = (LinearLayout) parent.findViewById(R.id.fragment_items_adapter_tags_container);
		}
	}
}
