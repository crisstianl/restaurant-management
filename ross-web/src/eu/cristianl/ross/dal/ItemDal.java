package eu.cristianl.ross.dal;

import java.util.List;

import eu.cristianl.ross.dal.filters.BaseFilter;
import eu.cristianl.ross.dal.filters.ItemFilter;
import eu.cristianl.ross.dal.utils.QueryBuilder;
import eu.cristianl.ross.entities.Item;
import eu.cristianl.ross.entities.tables.ItemTable;

public class ItemDal extends BaseDal<Item> {
	private static ItemDal mInstance;

	private ItemDal() {
		super(Item.class);
	}

	public static synchronized ItemDal I() {
		if (mInstance == null) {
			mInstance = new ItemDal();
		}

		return mInstance;
	}

	@Override
	protected void addWhereClauses(BaseFilter baseFilter, QueryBuilder query) {
		if (baseFilter instanceof ItemFilter) {
			final ItemFilter filter = (ItemFilter) baseFilter;

			if (filter.getCategoryId() != null) {
				query.addWhereClause(filter, ItemTable.CATEGORY_ID, filter.getCategoryId());
			}
		}
	}

	public long totalItems() {
		return super.count();
	}

	public List<Item> getItems(String categoryId, int maxResults) {
		final ItemFilter filters = new ItemFilter(true);
		if (categoryId != null && !categoryId.isEmpty()) {
			filters.setCategoryId(categoryId);
		}

		return super.query(new ItemFilter[] { filters }, ItemTable.TITLE, false, 0, maxResults);
	}

	@Override
	public void update(Item item) {
		super.update(item);
	}

}
