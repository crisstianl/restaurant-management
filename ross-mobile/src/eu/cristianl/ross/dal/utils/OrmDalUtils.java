package eu.cristianl.ross.dal.utils;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.j256.ormlite.stmt.Where;

import eu.cristianl.ross.dal.filters.BaseFilter;
import eu.cristianl.ross.dal.filters.BaseFilter.RangeFilter;
import eu.cristianl.ross.entities.support.PersistableCodeEnum;

public class OrmDalUtils {
	public static final String SQL_ORDERBY = "ORDER BY ";
	public static final String SQL_GROUPBY = "GROUP BY ";

	public static final String SQL_LIMIT = "LIMIT ";
	public static final String SQL_OFFSET = "OFFSET ";

	public static boolean fillRangeField(BaseFilter filter, String columnName,
			RangeFilter<?> filterRange, Where<?, ?> where) throws SQLException {
		return addRangeClause(filter.isNegated(), filter.isRestricted(), columnName, filterRange,
				where);
	}

	public static void fillTextFieldAlternatives(BaseFilter filter, String columnName,
			String[] alternatives, Where<?, ?> where) throws SQLException {
		for (String value : alternatives) {
			addEqualityClause(filter.isNegated(), filter.isRestricted(), columnName, value, where);
		}

		groupWhereClause(where, alternatives.length, true);
	}

	public static void fillCodeEnumFieldAlternatives(BaseFilter filter, String columnName,
			PersistableCodeEnum[] alternatives, Where<?, ?> where) throws SQLException {
		for (PersistableCodeEnum value : alternatives) {
			addEqualityClause(filter.isNegated(), filter.isRestricted(), columnName, value, where);
		}

		groupWhereClause(where, alternatives.length, true);
	}

	public static void fillRawTextFieldAlternatives(BaseFilter filter, String columnName,
			String[] alternatives, Where<?, ?> where) {
		for (String value : alternatives) {
			addRawEqualityClause(filter.isNegated(), filter.isRestricted(), columnName, value,
					where);
		}

		groupWhereClause(where, alternatives.length, true);
	}

	/*
	 * Throw now RuntimeException if isResticted (LIKE)
	 */
	public static void fillTextFieldAlternativeIN(BaseFilter filter, String columnName,
			String[] alternatives, Where<?, ?> where) throws SQLException {

		if (!filter.isRestricted())
			throw new RuntimeException("Cannot create a IN query with LIKE wildcards");

		if (filter.isNegated())
			where.not();

		where.in(columnName, (Object[]) alternatives);
	}

	public static void groupWhereClause(Where<?, ?> where, int count, boolean alternatives) {
		if (count < 2) {
			return;
		}

		if (alternatives) {
			where.or(count);
		} else {
			where.and(count);
		}
	}

	public static void addEqualityClause(boolean negated, boolean restricted, String columnName,
			Object columnValue, Where<?, ?> where) throws SQLException {
		if (!(columnValue instanceof String)) {
			// force restricted clause for not text fields
			restricted = true;
		}

		if (restricted) {
			if (negated) {
				where.ne(columnName, columnValue);
			} else {
				where.eq(columnName, columnValue);
			}
		} else {
			if (negated) {
				where.not();
			}
			where.like(columnName, "%" + columnValue + "%");
		}
	}

	public static void addRawEqualityClause(boolean negated, boolean restricted, String columnName,
			String columnValue, Where<?, ?> where) {
		String stm = null;

		String escapedColumnValue = '\'' + columnValue + '\'';

		if (negated) {
			stm = columnName + "<>" + escapedColumnValue;
		} else if (restricted) {
			stm = columnName + "=" + escapedColumnValue;
		} else {
			stm = columnName + " LIKE " + "'%" + columnValue + "%'";
		}

		where.raw(stm);
	}

	public static void addInequalityClause(boolean inverse, boolean restricted, String columnName,
			Object value, Where<?, ?> where) throws SQLException {
		// We create these clauses:
		// > or >= if not inverted
		// < or <= if inverted

		if (inverse) {
			if (restricted) {
				where.lt(columnName, value);
			} else {
				where.le(columnName, value);
			}
		} else {
			if (restricted) {
				where.gt(columnName, value);
			} else {
				where.ge(columnName, value);
			}
		}
	}

	public static boolean addRangeClause(boolean negated, boolean restricted, String columnName,
			RangeFilter<?> rangeValue, Where<?, ?> where) throws SQLException {
		int rangeClauseCount = 0;
		Object minValue = rangeValue.getMin();
		Object maxValue = rangeValue.getMax();

		if (minValue != null) {
			addInequalityClause(negated, restricted, columnName, minValue, where);

			rangeClauseCount++;
		}

		if (maxValue != null) {
			addInequalityClause(!negated, restricted, columnName, maxValue, where);

			rangeClauseCount++;
		}

		if (rangeClauseCount > 1) {
			if (negated) {
				groupWhereClause(where, rangeClauseCount, true);
			} else {
				groupWhereClause(where, rangeClauseCount, false);
			}
		}

		return rangeClauseCount > 0;
	}

	public static String tableColumn(String table, String column) {
		return table + "." + column;
	}

	public static String prepareOrderBy(String[] fields, boolean ascendent) {
		StringBuilder strBuilder = new StringBuilder();

		if (fields != null && fields.length > 0) {
			strBuilder.append(SQL_ORDERBY);

			strBuilder.append(join(Arrays.asList(fields), ","));

			if (!ascendent) {
				strBuilder.append(" DESC");
			}

			strBuilder.append(' ');
		}

		return strBuilder.toString();
	}

	public static String prepareLimit(int limit) {
		return SQL_LIMIT + limit;
	}

	public static String prepareOffset(int offset) {
		return SQL_OFFSET + offset;
	}

	private static String join(List<? extends CharSequence> s, String delimiter) {
		int capacity = 0;
		int delimLength = delimiter.length();
		Iterator<? extends CharSequence> iter = s.iterator();

		if (iter.hasNext()) {
			capacity += iter.next().length() + delimLength;
		}

		StringBuilder buffer = new StringBuilder(capacity);
		iter = s.iterator();
		if (iter.hasNext()) {
			buffer.append(iter.next());
			while (iter.hasNext()) {
				buffer.append(delimiter).append(iter.next());
			}
		}

		return buffer.toString();
	}

}
