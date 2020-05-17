package eu.cristianl.ross.dal;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedDelete;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.StatementBuilder;
import com.j256.ormlite.stmt.Where;

import eu.cristianl.ross.dal.database.DatabaseHelper;
import eu.cristianl.ross.dal.filters.BaseFilter;
import eu.cristianl.ross.dal.iterators.ResultsIterator;
import eu.cristianl.ross.dal.utils.OrmDalUtils;
import eu.cristianl.ross.logging.AppLogger;

abstract class BaseDal<T, ID> {
	private final Class<?> mClazz;

	private Dao<T, ID> mDao;

	@SuppressWarnings("unchecked")
	protected BaseDal(Class<?> clazz) throws SQLException {
		mClazz = clazz;
		mDao = (Dao<T, ID>) DatabaseHelper.getInstance().getDao(clazz);
	}

	/** Query by id */
	protected T get(ID id) throws SQLException {
		logQuery("SELECT * FROM {} WHERE Id = {}", DatabaseHelper.getTable(mClazz), id);
		return mDao.queryForId(id);
	}

	/** Insert object in database, return true if succeeds */
	protected boolean save(T item) throws SQLException {
		logQuery("INSERT INTO {} VALUES {}", DatabaseHelper.getTable(mClazz), item);
		return (mDao.create(item) == 1);
	}

	/** Insert object in database if not exists, or return the existing one */
	protected T saveIfNotExits(T item) throws SQLException {
		logQuery("INSERT INTO {} IF NOT EXISTS VALUES ?", DatabaseHelper.getTable(mClazz));
		return mDao.createIfNotExists(item);
	}

	/** Flush local changes to an object in database, except for field ID */
	protected boolean persist(T item) throws SQLException {
		logQuery("UPDATE TABLE {} SET ? WHERE ID={}", DatabaseHelper.getTable(mClazz), item);
		return (mDao.update(item) == 1);
	}

	/** Revert local changes to an object with database values */
	protected boolean refresh(T item) throws SQLException {
		logQuery("REFRESH ITEM {}", item);
		return (mDao.refresh(item) == 1);
	}

	/** Delete the object from the database using the ID field */
	protected boolean delete(T item) throws SQLException {
		logQuery("DELETE FROM {} WHERE Id = {}", DatabaseHelper.getTable(mClazz), item);
		return (mDao.delete(item) == 1);
	}

	/** Delete the object from the database by ID */
	protected boolean deleteById(ID id) throws SQLException {
		logQuery("DELETE FROM {} WHERE Id = {}", DatabaseHelper.getTable(mClazz), id);
		return (mDao.deleteById(id) == 1);
	}

	/** Delete all objects from the database that match the where clauses */
	protected boolean delete(BaseFilter[] filters, boolean or) throws SQLException {
		final DeleteBuilder<T, ID> delete = mDao.deleteBuilder();
		if (filters != null && filters.length > 0) {
			prepareWhereClauses(delete, filters, or);
		}

		PreparedDelete<T> statement = delete.prepare();
		logQuery(statement.getStatement());
		return (mDao.delete(statement) == 1);
	}

	/** Query for a single value, such as count, sum, average */
	protected double queryRawForInteger(String query) throws SQLException {
		final GenericRawResults<String[]> results = mDao.queryRaw(query);
		final String[] firstRow = results.getFirstResult();
		if (firstRow == null || firstRow.length == 0) {
			return 0;
		} else if (firstRow.length > 1) {
			throw new SQLException("Query for single value returned more than one result");
		} else {
			try {
				return Double.parseDouble(firstRow[0]);
			} catch (Exception e) {
				throw new SQLException("Failed to parse query result " + firstRow[0]);
			}
		}
	}

	/**
	 * Query all rows of the table, using with caution might throw
	 * OutOfMemoryException
	 */
	protected List<T> queryAll() throws SQLException {
		logQuery("SELECT * FROM {}", DatabaseHelper.getTable(mClazz));
		return mDao.queryForAll();
	}

	/**
	 * Query all rows matching the where clauses, returns an iterator.</br>
	 * Close the iterator.
	 */
	protected ResultsIterator<T> iterator(BaseFilter[] filters, boolean or, String orderBy, boolean desc, int offset,
			int limit) throws SQLException {
		final PreparedQuery<T> statement = prepareQuery(filters, or, orderBy, desc, offset, limit);
		logQuery(statement.getStatement());
		return new ResultsIterator<T>(mDao.iterator(statement));
	}

	/** Query all rows matching the where clauses */
	protected List<T> query(BaseFilter[] filters, boolean or, String orderBy, boolean desc, int offset, int limit)
			throws SQLException {
		final PreparedQuery<T> statement = prepareQuery(filters, or, orderBy, desc, offset, limit);
		logQuery(statement.getStatement());
		return mDao.query(statement);
	}

	private PreparedQuery<T> prepareQuery(BaseFilter[] filters, boolean or, String orderBy, boolean desc, int offset,
			int limit) throws SQLException {
		final QueryBuilder<T, ID> query = mDao.queryBuilder();
		if (filters != null && filters.length > 0) {
			prepareWhereClauses(query, filters, or);
		}
		if (orderBy != null) {
			query.orderBy(orderBy, !desc);
		}
		if (offset > 0) {
			query.offset(Long.valueOf(offset));
		}
		if (limit > 0) {
			query.limit(Long.valueOf(limit));
		}

		return query.prepare();
	}

	private void prepareWhereClauses(StatementBuilder<T, ID> statement, BaseFilter[] filters, boolean or)
			throws SQLException {

		int clauseCount;
		final Where<T, ID> where = statement.where();
		for (BaseFilter singleFilter : filters) {
			clauseCount = addWhereClauses(where, singleFilter);
			if (clauseCount > 0) {
				OrmDalUtils.groupWhereClause(where, clauseCount, singleFilter.isAlternative());
			}
		}

		OrmDalUtils.groupWhereClause(where, filters.length, or);
	}

	protected abstract int addWhereClauses(Where<T, ID> where, BaseFilter baseFilter);

	protected final void addWhereClause(Where<T, ID> where, BaseFilter filter, String column, String[] values) {
		OrmDalUtils.fillRawTextFieldAlternatives(filter, column, values, where);
	}

	protected static void logQuery(String query, Object... args) {
		AppLogger.info(query, args);
	}
}
