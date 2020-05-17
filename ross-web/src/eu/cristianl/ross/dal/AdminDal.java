package eu.cristianl.ross.dal;

import java.util.List;

import eu.cristianl.ross.dal.filters.EmployeeFilter;
import eu.cristianl.ross.dal.utils.SQLDate;
import eu.cristianl.ross.entities.Admin;
import eu.cristianl.ross.entities.tables.AdminTable;

public class AdminDal extends EmployeeDal<Admin> {
	private static AdminDal mInstance;

	private AdminDal() {
		super(Admin.class);
	}

	public static synchronized AdminDal I() {
		if (mInstance == null) {
			mInstance = new AdminDal();
		}

		return mInstance;
	}

	public List<Admin> query(int jobId, java.util.Date hireDate, int maxResults) {
		EmployeeFilter filters = new EmployeeFilter(true);
		if (jobId > -1) {
			filters.setJobId(String.valueOf(jobId));
		}
		if (hireDate != null) {
			filters.setHireTime(new SQLDate(hireDate.getTime()));
		}

		return super.query(new EmployeeFilter[] { filters }, AdminTable.NAME, false, 0, maxResults);
	}

	public long totalAdmins() {
		return super.count();
	}

	@Override
	public void update(Admin admin) {
		super.update(admin);
	}
}
