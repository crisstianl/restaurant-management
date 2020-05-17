package eu.cristianl.ross.dal.filters;


public class OrderFilter extends BaseFilter {
	private String[] mContactId;
	private String[] mDocStatusId;

	public OrderFilter(boolean restrict, boolean alternative, boolean negated) {
		super(restrict, alternative, negated);
	}

	public OrderFilter(boolean restrict, boolean alternative) {
		super(restrict, alternative);
	}

	public OrderFilter(boolean restrict) {
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

	public String[] getDocStatusId() {
		return mDocStatusId;
	}

	public void setDocStatusId(String[] docStatusId) {
		mDocStatusId = docStatusId;
	}

	public void setDocStatusId(String docStatusId) {
		setDocStatusId(new String[] { docStatusId });
	}
}
