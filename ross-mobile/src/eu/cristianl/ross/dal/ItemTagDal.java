package eu.cristianl.ross.dal;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.stmt.Where;

import eu.cristianl.ross.dal.filters.BaseFilter;
import eu.cristianl.ross.entities.Item;
import eu.cristianl.ross.entities.ItemTag;
import eu.cristianl.ross.logging.AppLogger;

public class ItemTagDal extends BaseDal<ItemTag, Item> {
	private static ItemTagDal mInstance;

	protected ItemTagDal() throws SQLException {
		super(ItemTag.class);
	}

	public static synchronized ItemTagDal getDal() {
		if (mInstance == null) {
			try {
				mInstance = new ItemTagDal();
			} catch (SQLException e) {
				AppLogger.error(e, e.getMessage());
			}
		}
		return mInstance;
	}

	@Override
	protected int addWhereClauses(Where<ItemTag, Item> where, BaseFilter baseFilter) {
		return 0;
	}

	public List<ItemTag> getAllItemTags() {
		List<ItemTag> results = null;
		try {
			results = super.queryAll();
		} catch (SQLException e) {
			AppLogger.error(e, e.getMessage());
		}
		return results;
	}
}
