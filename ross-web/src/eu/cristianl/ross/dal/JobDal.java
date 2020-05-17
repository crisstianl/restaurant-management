package eu.cristianl.ross.dal;

import eu.cristianl.ross.dal.filters.BaseFilter;
import eu.cristianl.ross.dal.utils.QueryBuilder;
import eu.cristianl.ross.entities.Job;

public class JobDal extends BaseDal<Job> {
	private static JobDal mInstance;

	private JobDal() {
		super(Job.class);
	}

	public static synchronized JobDal I() {
		if (mInstance == null) {
			mInstance = new JobDal();
		}

		return mInstance;
	}

	@Override
	protected void addWhereClauses(BaseFilter baseFilter, QueryBuilder query) {
	}

	public long totalJobs() {
		return super.count();
	}

	public java.util.List<Job> queryAll() {
		return super.query(null, null, false, 0, 100);
	}

}
