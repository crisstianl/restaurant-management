package eu.cristianl.ross.dal;

import java.util.List;

import eu.cristianl.ross.dal.filters.BaseCurrencyFilter;
import eu.cristianl.ross.dal.filters.BaseFilter;
import eu.cristianl.ross.dal.utils.QueryBuilder;
import eu.cristianl.ross.entities.BaseCurrency;
import eu.cristianl.ross.entities.tables.BaseCurrencyTable;

public class BaseCurrencyDal extends BaseDal<BaseCurrency> {
	private static BaseCurrencyDal mInstance;

	private BaseCurrencyDal() {
		super(BaseCurrency.class);
	}

	public static synchronized BaseCurrencyDal I() {
		if (mInstance == null) {
			mInstance = new BaseCurrencyDal();
		}

		return mInstance;
	}

	@Override
	protected void addWhereClauses(BaseFilter baseFilter, QueryBuilder query) {
		if (baseFilter instanceof BaseCurrencyFilter) {
			final BaseCurrencyFilter filter = (BaseCurrencyFilter) baseFilter;
			
			if (filter.getType() != null) {
				query.addWhereClause(filter, BaseCurrencyTable.CURRENCY_TYPE, filter.getType());
			}
		}
	}

	public BaseCurrency getBaseCurrency() {
		BaseCurrencyFilter filter = new BaseCurrencyFilter(true);
		filter.setType(BaseCurrencyTable.TABLE_NAME);

		List<BaseCurrency> results = super.query(new BaseCurrencyFilter[] { filter }, null, false, 0, 1);
		if (results != null && !results.isEmpty()) {
			return results.get(0);
		}
		return null;
	}

}
