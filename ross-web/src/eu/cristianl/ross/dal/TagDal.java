package eu.cristianl.ross.dal;

import eu.cristianl.ross.dal.filters.BaseFilter;
import eu.cristianl.ross.dal.utils.QueryBuilder;
import eu.cristianl.ross.entities.Tag;

public class TagDal extends BaseDal<Tag> {
	private static TagDal mInstance;

	private TagDal() {
		super(Tag.class);
	}

	public static synchronized TagDal I() {
		if (mInstance == null) {
			mInstance = new TagDal();
		}

		return mInstance;
	}

	@Override
	protected void addWhereClauses(BaseFilter baseFilter, QueryBuilder query) {
	}

}
