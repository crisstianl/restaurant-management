package eu.cristianl.ross.dal;

import eu.cristianl.ross.dal.filters.BaseFilter;
import eu.cristianl.ross.dal.utils.QueryBuilder;
import eu.cristianl.ross.entities.Unit;

public class UnitDal extends BaseDal<Unit> {
	private static UnitDal mInstance;

	private UnitDal() {
		super(Unit.class);
	}

	public static synchronized UnitDal I() {
		if (mInstance == null) {
			mInstance = new UnitDal();
		}

		return mInstance;
	}

	@Override
	protected void addWhereClauses(BaseFilter baseFilter, QueryBuilder query) {

	}

}
