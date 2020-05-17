package eu.cristianl.ross.dal;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.stmt.Where;

import eu.cristianl.ross.dal.filters.BaseFilter;
import eu.cristianl.ross.dal.filters.ItemFilter;
import eu.cristianl.ross.dal.iterators.ResultsIterator;
import eu.cristianl.ross.entities.Item;
import eu.cristianl.ross.entities.tables.ItemTable;
import eu.cristianl.ross.logging.AppLogger;

public class ItemDal extends BaseDal<Item, Integer> {
	private static ItemDal mInstance;

	protected ItemDal() throws SQLException {
		super(Item.class);
	}

	public static synchronized ItemDal getDal() {
		if (mInstance == null) {
			try {
				mInstance = new ItemDal();
			} catch (SQLException e) {
				AppLogger.error(e, e.getMessage());
			}
		}
		return mInstance;
	}

	@Override
	protected int addWhereClauses(Where<Item, Integer> where, BaseFilter baseFilter) {
		int clauseCount = 0;
		if (baseFilter instanceof ItemFilter) {
			final ItemFilter filter = (ItemFilter) baseFilter;

			if (filter.getItemId() != null) {
				addWhereClause(where, filter, ItemTable.ID, filter.getItemId());
				clauseCount++;
			}

			if (filter.getItemTitle() != null) {
				addWhereClause(where, filter, ItemTable.TITLE, filter.getItemTitle());
				clauseCount++;
			}

			if (filter.getItemDesc() != null) {
				addWhereClause(where, filter, ItemTable.DESCRIPTION, filter.getItemDesc());
				clauseCount++;
			}

			if (filter.getCategoryId() != null) {
				addWhereClause(where, filter, ItemTable.CATEGORY_ID, filter.getCategoryId());
				clauseCount++;
			}
		}
		return clauseCount;
	}

	public ResultsIterator<Item> getItemsOrderedById(String item, String categoryId, int maxResults) {
		return getItems(item, categoryId, ItemTable.ID, false, maxResults);
	}

	public ResultsIterator<Item> getItemsOrderedByTitle(String item, String categoryId, boolean desc, int maxResults) {
		return getItems(item, categoryId, ItemTable.TITLE, desc, maxResults);
	}

	public ResultsIterator<Item> getItemsOrderedByCategory(String item, String categoryId, boolean desc,
			int maxResults) {
		return getItems(item, categoryId, ItemTable.CATEGORY_ID, desc, maxResults);
	}

	public ResultsIterator<Item> getItemsOrderedByPrice(String item, String categoryId, boolean desc, int maxResults) {
		return getItems(item, categoryId, ItemTable.PRICE, desc, maxResults);
	}

	private ResultsIterator<Item> getItems(String item, String categoryId, String orderColumn, boolean desc,
			int maxResults) {
		try {
			List<ItemFilter> filters = new ArrayList<ItemFilter>(2);
			if (item != null) {
				ItemFilter filter = new ItemFilter(false, true);
				filter.setItemId(item);
				filter.setItemTitle(item);
				filters.add(filter);
			}
			if (categoryId != null) {
				ItemFilter filter = new ItemFilter(true);
				filter.setCategoryId(categoryId);
				filters.add(filter);
			}
			return super.iterator(filters.toArray(new ItemFilter[filters.size()]), false, orderColumn, desc, 0,
					maxResults);
		} catch (SQLException e) {
			AppLogger.error(e, e.getMessage());
		}
		return null;
	}
}
