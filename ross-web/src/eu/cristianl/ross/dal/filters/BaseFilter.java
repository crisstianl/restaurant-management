package eu.cristianl.ross.dal.filters;

public abstract class BaseFilter {
	private final static String[] OPERATORS = { "=", "!=", "<", ">", "<=", ">=", "IN", "LIKE", "BETWEEN" };

	private String conjunction;
	private String operator;

	/**
	 * @param and
	 *            clauses are linked with AND, otherwise with OR
	 */
	public BaseFilter(boolean and) {
		this(and, 0);
	}

	/**
	 * @param and
	 *            clauses are linked with AND, otherwise with OR
	 * @param operator
	 *            =, !=, <, >, <=, >=, IN, LIKE, BETWEEN
	 */
	public BaseFilter(boolean and, int operator) {
		this.conjunction = and ? "AND" : "OR";
		this.operator = OPERATORS[operator];
	}

	public String getConjunction() {
		return this.conjunction;
	}

	public String getOperator() {
		return this.operator;
	}
}
