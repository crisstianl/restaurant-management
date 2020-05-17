package eu.cristianl.ross.dal.filters;

import eu.cristianl.ross.dal.utils.SQLDate;

public class OrderFilter extends BaseFilter {
	private String[] mOrderId;
	private String mDocStatusId;
	private String mEmployeeId;
	private SQLDate mOrderDate;

	public OrderFilter(boolean and, int operator) {
		super(and, operator);
	}

	public OrderFilter(boolean and) {
		super(and);
	}

	public String getDocStatusId() {
		return mDocStatusId;
	}

	public OrderFilter setDocStatusId(String docStatusId) {
		mDocStatusId = docStatusId;
		return this;
	}

	public SQLDate getOrderDate() {
		return mOrderDate;
	}

	public OrderFilter setOrderDate(SQLDate orderDate) {
		mOrderDate = orderDate;
		return this;
	}

	public String getEmployeeId() {
		return mEmployeeId;
	}

	public OrderFilter setEmployeeId(String employeeId) {
		mEmployeeId = employeeId;
		return this;
	}

	public String[] getOrderId() {
		return mOrderId;
	}

	public OrderFilter setOrderId(String[] orderId) {
		this.mOrderId = orderId;
		return this;
	}

}
