package eu.cristianl.ross.dal;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.stmt.Where;

import eu.cristianl.ross.dal.filters.BaseFilter;
import eu.cristianl.ross.entities.Category;
import eu.cristianl.ross.logging.AppLogger;

public class CategoryDal extends BaseDal<Category, String> {
	private static CategoryDal mInstance;

	protected CategoryDal() throws SQLException {
		super(Category.class);
	}

	public static synchronized CategoryDal getDal() {
		if (mInstance == null) {
			try {
				mInstance = new CategoryDal();
			} catch (SQLException e) {
				AppLogger.error(e, e.getMessage());
			}
		}
		return mInstance;
	}

	@Override
	protected int addWhereClauses(Where<Category, String> where, BaseFilter baseFilter) {
		return 0;
	}

	public List<Category> getAllCategories() {
		List<Category> results = null;
		try {
			results = super.queryAll();
		} catch (SQLException e) {
			AppLogger.error(e, e.getMessage());
		}
		return results;
	}
}
