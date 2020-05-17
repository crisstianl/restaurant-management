package eu.cristianl.ross.dal;

import java.util.List;

import eu.cristianl.ross.dal.filters.BaseCurrencyFilter;
import eu.cristianl.ross.dal.filters.BaseFilter;
import eu.cristianl.ross.dal.utils.QueryBuilder;
import eu.cristianl.ross.entities.Currency;
import eu.cristianl.ross.entities.tables.CurrencyTable;

public class CurrencyDal extends BaseDal<Currency> {
	private static CurrencyDal mInstance;

	private CurrencyDal() {
		super(Currency.class);
	}

	public static synchronized CurrencyDal I() {
		if (mInstance == null) {
			mInstance = new CurrencyDal();
		}

		return mInstance;
	}

	@Override
	protected void addWhereClauses(BaseFilter baseFilter, QueryBuilder query) {

	}

	public List<Currency> getCurrencies() {
		BaseCurrencyFilter filter = new BaseCurrencyFilter(true);
		filter.setType(CurrencyTable.TABLE_NAME);

		return super.query(new BaseCurrencyFilter[] { filter }, null, false, 0, 1);
	}

}
