package eu.cristianl.ross.dal;

import java.util.List;

import eu.cristianl.ross.dal.filters.BaseFilter;
import eu.cristianl.ross.dal.utils.QueryBuilder;
import eu.cristianl.ross.entities.Contact;
import eu.cristianl.ross.entities.tables.ContactTable;

public class ContactDal extends BaseDal<Contact> {
	private static ContactDal mInstance;

	private ContactDal() {
		super(Contact.class);
	}

	public static synchronized ContactDal I() {
		if (mInstance == null) {
			mInstance = new ContactDal();
		}

		return mInstance;
	}

	@Override
	protected void addWhereClauses(BaseFilter baseFilter, QueryBuilder query) {
	}

	public long totalContacts() {
		return super.count();
	}

	public List<Contact> getAll() {
		return super.query(null, ContactTable.NAME, false, 0, 100);
	}

	@Override
	public void update(Contact contact) {
		super.update(contact);
	}
}
