package eu.cristianl.ross.dal;

import java.util.List;

import eu.cristianl.ross.dal.filters.EmployeeFilter;
import eu.cristianl.ross.dal.utils.SQLDate;
import eu.cristianl.ross.entities.Worker;
import eu.cristianl.ross.entities.tables.WorkerTable;

public class WorkerDal extends EmployeeDal<Worker> {
	private static WorkerDal mInstance;

	private WorkerDal() {
		super(Worker.class);
	}

	public static synchronized WorkerDal I() {
		if (mInstance == null) {
			mInstance = new WorkerDal();
		}

		return mInstance;
	}

	public List<Worker> queryAll() {
		return super.query(null, WorkerTable.NAME, false, 0, 100);
	}

	public List<Worker> query(int jobId, java.util.Date hireDate, int maxResults) {
		EmployeeFilter filters = new EmployeeFilter(true);
		if (jobId > -1) {
			filters.setJobId(String.valueOf(jobId));
		}
		if (hireDate != null) {
			filters.setHireTime(new SQLDate(hireDate.getTime()));
		}

		return super.query(new EmployeeFilter[] { filters }, WorkerTable.NAME, false, 0, maxResults);
	}

	public long totalWorkers() {
		return super.count();
	}

	@Override
	public void update(Worker worker) {
		super.update(worker);
	}

}
