package eu.cristianl.ross.dal;

import java.util.List;

import eu.cristianl.ross.dal.filters.BaseFilter;
import eu.cristianl.ross.dal.filters.EmployeeFilter;
import eu.cristianl.ross.dal.utils.QueryBuilder;
import eu.cristianl.ross.entities.Employee;
import eu.cristianl.ross.entities.tables.EmployeeTable;
import eu.cristianl.ross.utils.Utils;

public abstract class EmployeeDal<T extends Employee> extends BaseDal<T> {

	protected EmployeeDal(Class<? extends Employee> clazz) {
		super(clazz);
	}

	@Override
	protected void addWhereClauses(BaseFilter baseFilter, QueryBuilder query) {
		if (baseFilter instanceof EmployeeFilter) {
			final EmployeeFilter filter = (EmployeeFilter) baseFilter;

			if (filter.getUsername() != null) {
				query.addWhereClause(filter, EmployeeTable.USERNAME, filter.getUsername());
			}

			if (filter.getName() != null) {
				query.addWhereClause(filter, EmployeeTable.NAME, filter.getName());
			}

			if (filter.getPassword() != null) {
				query.addWhereClause(filter, EmployeeTable.PASSWORD, filter.getPassword());
			}

			if (filter.getJobId() != null) {
				query.addWhereClause(filter, EmployeeTable.JOB_ID, filter.getJobId());
			}

			if (filter.getHireTime() != null) {
				query.addWhereClause(filter, EmployeeTable.HIRE_DATE, filter.getHireTime().toString(), null);
			}
		}
	}

	public T getEmployee(String name, String password) {
		EmployeeFilter filter1 = new EmployeeFilter(true);
		filter1.setUsername(name).setPassword(password);

		EmployeeFilter filter2 = new EmployeeFilter(false);
		filter2.setName(name);

		EmployeeFilter filter3 = new EmployeeFilter(true);
		filter3.setPassword(password);

		final List<T> results = super.query(new EmployeeFilter[] { filter1, filter2, filter3 }, null, false, 0, 1);
		return (!Utils.isEmptyOrNull(results)) ? results.get(0) : null;
	}

}
