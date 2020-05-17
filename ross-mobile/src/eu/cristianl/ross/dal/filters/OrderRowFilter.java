package eu.cristianl.ross.dal.filters;

public class OrderRowFilter extends BaseFilter {
	private String[] mOrderId;

	public OrderRowFilter() {
		super();

	}

	public OrderRowFilter(boolean restrict, boolean alternative, boolean negated) {
		super(restrict, alternative, negated);

	}

	public OrderRowFilter(boolean restrict, boolean alternative) {
		super(restrict, alternative);

	}

	public OrderRowFilter(boolean restrict) {
		super(restrict);

	}

	public String[] getOrderId() {
		return mOrderId;
	}

	public void setOrderId(String[] orderId) {
		mOrderId = orderId;
	}

	public void setOrderId(String orderId) {
		setOrderId(new String[] { orderId });
	}

}
