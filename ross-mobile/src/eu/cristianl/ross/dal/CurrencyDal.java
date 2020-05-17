package eu.cristianl.ross.dal;

import java.sql.SQLException;

import com.j256.ormlite.stmt.Where;

import eu.cristianl.ross.dal.filters.BaseFilter;
import eu.cristianl.ross.entities.Currency;
import eu.cristianl.ross.logging.AppLogger;

public class CurrencyDal extends BaseDal<Currency, String> {
	private static CurrencyDal mInstance;

	protected CurrencyDal() throws SQLException {
		super(Currency.class);
	}

	public static synchronized CurrencyDal getDal() {
		if (mInstance == null) {
			try {
				mInstance = new CurrencyDal();
			} catch (SQLException e) {
				AppLogger.error(e, e.getMessage());
			}
		}
		return mInstance;
	}

	@Override
	protected int addWhereClauses(Where<Currency, String> where, BaseFilter baseFilter) {
		return 0;
	}
}
