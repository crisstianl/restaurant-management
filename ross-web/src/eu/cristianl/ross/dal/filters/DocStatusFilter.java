package eu.cristianl.ross.dal.filters;

public class DocStatusFilter extends BaseFilter {

	private String mDocStatusId;

	public DocStatusFilter(boolean and, int operator) {
		super(and, operator);
	}

	public DocStatusFilter(boolean and) {
		super(and);
	}

	public String getDocStatusId() {
		return mDocStatusId;
	}

	public DocStatusFilter setDocStatusId(String docStatusId) {
		mDocStatusId = docStatusId;
		return this;
	}

}
