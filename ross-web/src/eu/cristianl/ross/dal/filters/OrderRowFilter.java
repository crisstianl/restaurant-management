package eu.cristianl.ross.dal.filters;

public class OrderRowFilter extends BaseFilter {
	private String mOrderId;

	public OrderRowFilter(boolean and, int operator) {
		super(and, operator);
	}

	public OrderRowFilter(boolean and) {
		super(and);
	}

	public String getOrderId() {
		return mOrderId;
	}

	public OrderRowFilter setOrderId(String orderId) {
		mOrderId = orderId;
		return this;
	}

}
