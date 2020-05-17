package eu.cristianl.ross.dal;

import java.util.List;

import eu.cristianl.ross.dal.filters.BaseFilter;
import eu.cristianl.ross.dal.utils.QueryBuilder;
import eu.cristianl.ross.entities.Category;

public class CategoryDal extends BaseDal<Category> {
	private static CategoryDal mInstance;

	private CategoryDal() {
		super(Category.class);
	}

	public static synchronized CategoryDal I() {
		if (mInstance == null) {
			mInstance = new CategoryDal();
		}

		return mInstance;
	}

	@Override
	protected void addWhereClauses(BaseFilter baseFilter, QueryBuilder query) {
	}

	public List<Category> getAll() {
		return super.query(null, null, false, 0, 100);
	}

}
