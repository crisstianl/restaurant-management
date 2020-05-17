package eu.cristianl.ross.dal;

import java.util.List;

import eu.cristianl.ross.dal.filters.BaseFilter;
import eu.cristianl.ross.dal.filters.UserSessionFilter;
import eu.cristianl.ross.dal.utils.QueryBuilder;
import eu.cristianl.ross.dal.utils.SQLDatetime;
import eu.cristianl.ross.entities.UserSession;
import eu.cristianl.ross.entities.tables.UserSessionTable;

public class UserSessionDal extends BaseDal<UserSession> {
	private static UserSessionDal mInstance;

	private UserSessionDal() {
		super(UserSession.class);
	}

	public static synchronized UserSessionDal I() {
		if (mInstance == null) {
			mInstance = new UserSessionDal();
		}

		return mInstance;
	}

	@Override
	protected void addWhereClauses(BaseFilter baseFilter, QueryBuilder query) {
		if (baseFilter instanceof UserSessionFilter) {
			UserSessionFilter filter = (UserSessionFilter) baseFilter;

			if (filter.getEmployeeId() != null) {
				query.addWhereClause(filter, UserSessionTable.EMPLOYEE_ID, filter.getEmployeeId());
			}

			if (filter.getEndTime() != null) {
				query.addWhereClause(filter, UserSessionTable.END_TIME, filter.getEndTime().toString(), null);
			}
		}
	}

	public UserSession getActiveSession(Integer employeeId) {
		UserSessionFilter filter = new UserSessionFilter(true);
		filter.setEmployeeId(employeeId.toString());
		filter.setEndTime(new SQLDatetime());

		List<UserSession> results = super.query(new UserSessionFilter[] { filter }, null, false, 0, 1);
		if (results != null && results.size() > 0) {
			return results.get(0);
		}
		return null;
	}

	public UserSession getSession(Integer employeeId) {
		UserSessionFilter filter = new UserSessionFilter(true);
		filter.setEmployeeId(employeeId.toString());

		List<UserSession> results = super.query(new UserSessionFilter[] { filter }, null, false, 0, 1);
		if (results != null && results.size() > 0) {
			return results.get(0);
		}
		return null;
	}

	public UserSession getSession(String sessionId) {
		return super.get(sessionId);
	}

	public void updateSession(UserSession session) {
		super.update(session);
	}

	public void createSession(UserSession session) {
		super.persist(session);
	}
}
