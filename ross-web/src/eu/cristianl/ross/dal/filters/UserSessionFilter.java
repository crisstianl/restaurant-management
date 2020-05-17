package eu.cristianl.ross.dal.filters;

import eu.cristianl.ross.dal.utils.SQLDatetime;

public class UserSessionFilter extends BaseFilter {
	private String mEmployeeId;

	private SQLDatetime mEndTime;

	public UserSessionFilter(boolean and, int operator) {
		super(and, operator);
	}

	public UserSessionFilter(boolean and) {
		super(and);
	}

	public String getEmployeeId() {
		return mEmployeeId;
	}

	public UserSessionFilter setEmployeeId(String employeeId) {
		mEmployeeId = employeeId;
		return this;
	}

	public SQLDatetime getEndTime() {
		return mEndTime;
	}

	public UserSessionFilter setEndTime(SQLDatetime endTime) {
		mEndTime = endTime;
		return this;
	}

}
