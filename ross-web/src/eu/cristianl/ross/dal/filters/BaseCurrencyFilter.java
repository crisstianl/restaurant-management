package eu.cristianl.ross.dal.filters;

public class BaseCurrencyFilter extends BaseFilter {

	private String mType;

	public BaseCurrencyFilter(boolean and) {
		super(and);
	}

	public BaseCurrencyFilter(boolean and, int operator) {
		super(and, operator);
	}

	public String getType() {
		return mType;
	}

	public BaseCurrencyFilter setType(String type) {
		this.mType = type;
		return this;
	}

}
