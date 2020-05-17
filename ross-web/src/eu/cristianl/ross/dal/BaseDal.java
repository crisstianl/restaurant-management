package eu.cristianl.ross.dal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import eu.cristianl.ross.dal.database.DatabaseHelper;
import eu.cristianl.ross.dal.filters.BaseFilter;
import eu.cristianl.ross.dal.utils.QueryBuilder;
import eu.cristianl.ross.logging.AppLogger;

abstract class BaseDal<T> {
	private final DatabaseHelper mDbHelper;
	private final Class<?> mClazz;

	protected BaseDal(Class<?> clazz) {
		mDbHelper = DatabaseHelper.getInstance();
		mClazz = clazz;
	}

	/** Query table by id */
	@SuppressWarnings("unchecked")
	protected <ID extends Serializable> T get(ID id) {
		T retValue = null;
		Session session = null;
		try {
			session = mDbHelper.beginTransaction();
			retValue = (T) session.get(mClazz, id);
			mDbHelper.endTransaction(session);
		} catch (Exception e) {
			mDbHelper.cancelTransaction(session);
			AppLogger.error(e, "Failed to execute get by id");
		}

		return retValue;
	}

	@SuppressWarnings("unchecked")
	protected <ID extends Serializable> List<T> get(ID[] ids) {
		List<T> retList = new ArrayList<T>();
		Session session = null;
		try {
			session = mDbHelper.beginTransaction();

			for (ID id : ids) {
				retList.add((T) session.get(mClazz, id));
			}

			mDbHelper.endTransaction(session);
		} catch (Exception e) {
			mDbHelper.cancelTransaction(session);
			AppLogger.error(e, "Failed to execute get by id");
		}
		return retList;
	}

	/** Insert a row in database, immediately */
	protected void save(T item) {
		Session session = null;
		try {
			session = mDbHelper.beginTransaction();
			session.save(item);
			mDbHelper.endTransaction(session);
		} catch (Exception e) {
			mDbHelper.cancelTransaction(session);
			AppLogger.error(e, "Failed to execute save");
		}
	}

	/** Insert or update a row in database */
	protected void saveOrUpdate(T item) {
		Session session = null;
		try {
			session = mDbHelper.beginTransaction();
			session.saveOrUpdate(item);
			mDbHelper.endTransaction(session);
		} catch (Exception e) {
			mDbHelper.cancelTransaction(session);
			AppLogger.error(e, "Failed to execute save or update");
		}
	}

	/** Insert multiple rows in database, immediately, in one transaction */
	protected void save(T[] items) {
		Session session = null;
		try {
			session = mDbHelper.beginTransaction();

			for (T item : items) {
				session.save(item);
			}

			mDbHelper.endTransaction(session);
		} catch (Exception e) {
			mDbHelper.cancelTransaction(session);
			AppLogger.error(e, "Failed to execute save");
		}
	}

	/** Insert a row in database */
	protected void persist(T item) {
		Session session = null;
		try {
			session = mDbHelper.beginTransaction();
			session.persist(item);
			mDbHelper.endTransaction(session);
		} catch (Exception e) {
			mDbHelper.cancelTransaction(session);
			AppLogger.error(e, "Failed to execute persist");
		}
	}

	/** Insert multiple rows in database, in one transaction */
	protected void persist(T[] items) {
		Session session = null;
		try {
			session = mDbHelper.beginTransaction();

			for (T item : items) {
				session.persist(item);
			}

			mDbHelper.endTransaction(session);
		} catch (Exception e) {
			mDbHelper.cancelTransaction(session);
			AppLogger.error(e, "Failed to execute persist");
		}
	}

	/** Synchronize object's data with database values, local changes are lost */
	protected void refresh(T item) {
		Session session = null;
		try {
			session = mDbHelper.beginTransaction();
			session.refresh(item);
			mDbHelper.endTransaction(session);
		} catch (Exception e) {
			mDbHelper.cancelTransaction(session);
			AppLogger.error(e, "Failed to execute refresh");
		}
	}

	/** Persist local changes of an object in database */
	protected void update(T item) {
		Session session = null;
		try {
			session = mDbHelper.beginTransaction();
			session.update(item);
			mDbHelper.endTransaction(session);
		} catch (Exception e) {
			mDbHelper.cancelTransaction(session);
			AppLogger.error(e, "Failed to execute update");
		}
	}

	/** Returns number of inserted rows */
	protected int insert(String statement) {
		int retValue = 0;
		Session session = null;
		try {
			session = mDbHelper.beginTransaction();

			SQLQuery query = session.createSQLQuery(statement);
			retValue = query.executeUpdate();

			mDbHelper.endTransaction(session);
		} catch (Exception e) {
			mDbHelper.cancelTransaction(session);
			AppLogger.error(e, "Failed to execute insert");
		}
		return retValue;
	}

	/** Returns the number of inserted rows */
	protected int insert(String[] statements) {
		int retValue = 0;
		Session session = null;
		try {
			session = mDbHelper.beginTransaction();

			for (String statement : statements) {
				SQLQuery query = session.createSQLQuery(statement);
				retValue += query.executeUpdate();
			}

			mDbHelper.endTransaction(session);
		} catch (Exception e) {
			mDbHelper.cancelTransaction(session);
			AppLogger.error(e, "Failed to execute insert");
		}
		return retValue;
	}

	/** SQL query */
	protected List<T> query(final BaseFilter[] filters, String orderBy, boolean desc, int offset, int limit) {
		final String table = DatabaseHelper.getInstance().getTableName(mClazz);
		final QueryBuilder query = new QueryBuilder(table);
		// where
		if (filters != null && filters.length > 0) {
			for (BaseFilter filter : filters) {
				addWhereClauses(filter, query);
			}
		}
		// order by
		if (orderBy != null) {
			query.addOrderClause(orderBy, desc);
		}
		if (offset > 0 || limit > 0) {
			query.addPagination(offset, limit);
		}

		return sql(query.toString(), 0, 0);
	}

	/** Hibernate query */
	@SuppressWarnings("unchecked")
	protected List<T> hql(final String hql, int offset, int limit) {
		List<T> retList = null;
		Session session = null;
		try {
			session = mDbHelper.beginTransaction();

			Query query = session.createQuery(hql);
			if (offset > 0) {
				query.setFirstResult(offset);
			}
			if (limit > 0) {
				query.setMaxResults(limit);
			}
			retList = (List<T>) query.list();

			mDbHelper.endTransaction(session);
		} catch (Exception e) {
			mDbHelper.cancelTransaction(session);
			AppLogger.error(e, "Failed to execute query");
		}
		return retList;
	}

	/** SQL query */
	@SuppressWarnings("unchecked")
	protected List<T> sql(final String sql, int offset, int limit) {
		List<T> retList = null;
		Session session = null;
		try {
			session = mDbHelper.beginTransaction();

			SQLQuery query = session.createSQLQuery(sql);
			if (offset > 0) {
				query.setFirstResult(offset);
			}
			if (limit > 0) {
				query.setMaxResults(limit);
			}
			retList = (List<T>) query.addEntity(mClazz).list();

			mDbHelper.endTransaction(session);
		} catch (Exception e) {
			mDbHelper.cancelTransaction(session);
			AppLogger.error(e, "Failed to execute query");
		}
		return retList;
	}

	protected long count() {
		Long count = 0L;
		Session session = null;
		try {
			session = mDbHelper.beginTransaction();

			final Query query = session.createQuery("Select COUNT(*) From " + mClazz.getSimpleName());
			count = (Long) query.uniqueResult();

			mDbHelper.endTransaction(session);
		} catch (Exception e) {
			mDbHelper.cancelTransaction(session);
			AppLogger.error(e, "Failed to execute query");
		}
		return count.longValue();
	}

	/** Add where clauses */
	protected abstract void addWhereClauses(BaseFilter baseFilter, QueryBuilder query);
}
