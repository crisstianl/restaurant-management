package eu.cristianl.ross.dal.filters;

import eu.cristianl.ross.dal.utils.SQLDate;

public class EmployeeFilter extends BaseFilter {
	private String mUsername;

	private String mName;

	private String mPassword;

	private String mJobId;

	private SQLDate mHireTime;

	public EmployeeFilter(boolean and, int operator) {
		super(and, operator);
	}

	public EmployeeFilter(boolean and) {
		super(and);
	}

	public String getUsername() {
		return mUsername;
	}

	public EmployeeFilter setUsername(String name) {
		mUsername = name;
		return this;
	}

	public String getName() {
		return mName;
	}

	public EmployeeFilter setName(String name) {
		mName = name;
		return this;
	}

	public String getPassword() {
		return mPassword;
	}

	public EmployeeFilter setPassword(String password) {
		mPassword = password;
		return this;
	}

	public String getJobId() {
		return mJobId;
	}

	public EmployeeFilter setJobId(String jobId) {
		mJobId = jobId;
		return this;
	}

	public SQLDate getHireTime() {
		return mHireTime;
	}

	public EmployeeFilter setHireTime(SQLDate hireTime) {
		mHireTime = hireTime;
		return this;
	}

}
