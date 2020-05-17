package eu.cristianl.ross.dal;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.stmt.Where;

import eu.cristianl.ross.dal.filters.BaseFilter;
import eu.cristianl.ross.dal.filters.ContactFilter;
import eu.cristianl.ross.entities.Contact;
import eu.cristianl.ross.entities.tables.ContactTable;
import eu.cristianl.ross.logging.AppLogger;

public class ContactDal extends BaseDal<Contact, Integer> {
	private static ContactDal mInstance;

	protected ContactDal() throws SQLException {
		super(Contact.class);
	}

	public static synchronized ContactDal getDal() {
		if (mInstance == null) {
			try {
				mInstance = new ContactDal();
			} catch (SQLException e) {
				AppLogger.error(e, e.getMessage());
			}
		}
		return mInstance;
	}

	@Override
	protected int addWhereClauses(Where<Contact, Integer> where, BaseFilter baseFilter) {
		int clauseCount = 0;
		if (baseFilter instanceof ContactFilter) {
			ContactFilter filter = (ContactFilter) baseFilter;

			if (filter.getContactId() != null) {
				addWhereClause(where, filter, ContactTable.ID, filter.getContactId());
				clauseCount++;
			}

			if (filter.getContactName() != null) {
				addWhereClause(where, filter, ContactTable.NAME, filter.getContactName());
				clauseCount++;
			}
		}
		return clauseCount;
	}

	public List<Contact> getContactsOrderedById(String contact, int maxResults) {
		return query(contact, ContactTable.ID, maxResults);
	}

	public List<Contact> getContactsOrderedByName(String contact, int maxResults) {
		return query(contact, ContactTable.NAME, maxResults);
	}

	public List<Contact> getContactsOrderedByParty(String contact, int maxResults) {
		return query(contact, ContactTable.CONV_FACTOR, maxResults);
	}

	public List<Contact> getContactsOrderedByPriority(String contact, int maxResults) {
		return query(contact, ContactTable.PRIORITY, maxResults);
	}

	private List<Contact> query(String contact, String orderColumn, int maxResults) {
		List<Contact> results = null;
		try {
			if (contact == null) {
				results = super.query(null, false, orderColumn, false, 0, maxResults);
			} else {
				ContactFilter filter = new ContactFilter(false, true);
				filter.setContactId(contact);
				filter.setContactName(contact);
				results = super.query(new ContactFilter[] { filter }, false, orderColumn, false, 0, maxResults);
			}
		} catch (SQLException e) {
			AppLogger.error(e, e.getMessage());
		}
		return results;
	}

}
