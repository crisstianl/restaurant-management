package eu.cristianl.ross.dal.filters;

public class ContactFilter extends BaseFilter {
	private String[] mContactId;
	private String[] mContactName;

	public ContactFilter() {
		super();
	}

	public ContactFilter(boolean restrict, boolean alternative, boolean negated) {
		super(restrict, alternative, negated);
	}

	public ContactFilter(boolean restrict, boolean alternative) {
		super(restrict, alternative);
	}

	public ContactFilter(boolean restrict) {
		super(restrict);
	}

	public String[] getContactId() {
		return mContactId;
	}

	public void setContactId(String[] contactId) {
		mContactId = contactId;
	}

	public void setContactId(String contactId) {
		setContactId(new String[] { contactId });
	}

	public String[] getContactName() {
		return mContactName;
	}

	public void setContactName(String[] contactName) {
		mContactName = contactName;
	}

	public void setContactName(String contactName) {
		setContactName(new String[] { contactName });
	}

}
