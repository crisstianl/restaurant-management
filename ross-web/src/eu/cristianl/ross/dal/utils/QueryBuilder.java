package eu.cristianl.ross.dal.utils;

import eu.cristianl.ross.dal.filters.BaseFilter;

public class QueryBuilder {

	private StringBuilder select = new StringBuilder();

	private StringBuilder where = new StringBuilder();

	private StringBuilder order = new StringBuilder();

	private int offset = 0, limit = 1000;

	/** Select * from Table */
	public QueryBuilder(final String table) {
		this.select.append("SELECT * FROM ").append(table);
	}

	/** Select foreach column in columns from Table */
	public QueryBuilder(final String table, final String[] columns) {
		this.select.append("SELECT ");
		for (String column : columns) {
			this.select.append(column).append(',');
		}
		this.select.delete(this.select.length() - 1, this.select.length()); // last ","

		this.select.append(" FROM ").append(table);
	}

	@Override
	public String toString() {
		final StringBuilder statement = new StringBuilder();

		// select from table
		if (this.select.length() > 0) {
			statement.append(this.select);
		}

		// where clauses
		if (this.where.length() > 0) {
			statement.append(this.where);
		}

		// order
		if (this.order.length() > 0) {
			statement.append(this.order);
		}

		// offset
		if (this.offset > 0) {
			statement.append(" OFFSET ").append(this.offset);
		}

		// limit
		if (this.limit > 0) {
			statement.append(" LIMIT ").append(this.limit);
		}

		return statement.toString();
	}

	@Override
	protected void finalize() throws Throwable {
		this.select.setLength(0);
		this.where.setLength(0);
		this.order.setLength(0);
		super.finalize();
	}

	public QueryBuilder addWhereClause(BaseFilter filter, String column, String value) {
		if (this.where.length() == 0) { // first time
			this.where.append(" WHERE ");
		} else {
			this.where.append(' ').append(filter.getConjunction()).append(' ');
		}

		this.where.append(column);
		this.where.append(' ').append(filter.getOperator()).append(' ');
		this.where.append('\'').append(value).append('\'');

		return this;
	}

	public QueryBuilder addWhereClause(BaseFilter filter, String column, String[] values) {
		if (this.where.length() == 0) { // first time
			this.where.append(" WHERE ");
		} else {
			this.where.append(' ').append(filter.getConjunction()).append(' ');
		}

		this.where.append(column).append(" IN ").append('(');
		for (int i = 0; i < values.length; i++) {
			this.where.append('\'').append(values[i]).append('\'');
			if (i < values.length - 1) {
				this.where.append(',');
			} else {
				this.where.append(')');
			}
		}

		return this;
	}

	public QueryBuilder addWhereClause(BaseFilter filter, String column, String minValue, String maxValue) {
		if (this.where.length() == 0) { // first time
			this.where.append(" WHERE ");
		} else {
			this.where.append(' ').append(filter.getConjunction()).append(' ');
		}

		this.where.append(column);
		if (minValue != null && maxValue != null) {
			this.where.append(" BETWEEN ").append('\'').append(minValue).append('\'').append(" AND ").append('\'')
					.append(maxValue).append('\'');

		} else if (minValue != null) {
			this.where.append(" >= ").append('\'').append(minValue).append('\'');

		} else if (maxValue != null) {
			this.where.append(" <= ").append('\'').append(maxValue).append('\'');
		}

		return this;
	}

	public QueryBuilder addOrderClause(String column, boolean desc) {
		if (this.order.length() == 0) {
			this.order.append(" ORDER BY ");
		} else {
			this.order.append(", ");
		}

		this.order.append(column).append(desc ? " DESC" : " ASC");
		return this;
	}

	public QueryBuilder addPagination(int offset, int limit) {
		this.offset = offset;
		this.limit = limit;
		return this;
	}

	public static String selectTableColumnsInfo(String table) {
		return "SELECT * FROM information_schema.columns WHERE TABLE_NAME = \'" + table + "\'";
	}
}
