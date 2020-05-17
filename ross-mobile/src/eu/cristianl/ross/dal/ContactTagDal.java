package eu.cristianl.ross.dal;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.stmt.Where;

import eu.cristianl.ross.dal.filters.BaseFilter;
import eu.cristianl.ross.entities.Contact;
import eu.cristianl.ross.entities.ContactTag;
import eu.cristianl.ross.logging.AppLogger;

public class ContactTagDal extends BaseDal<ContactTag, Contact> {
	private static ContactTagDal mInstance;

	protected ContactTagDal() throws SQLException {
		super(ContactTag.class);
	}

	public static synchronized ContactTagDal getDal() {
		if (mInstance == null) {
			try {
				mInstance = new ContactTagDal();
			} catch (SQLException e) {
				AppLogger.error(e, e.getMessage());
			}
		}
		return mInstance;
	}

	@Override
	protected int addWhereClauses(Where<ContactTag, Contact> where, BaseFilter baseFilter) {
		return 0;
	}

	public List<ContactTag> getAll() {
		List<ContactTag> results = null;
		try {
			results = super.queryAll();
		} catch (SQLException e) {
			AppLogger.error(e, e.getMessage());
		}
		return results;
	}
}
