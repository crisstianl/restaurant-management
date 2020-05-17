package eu.cristianl.ross.dal.filters;

public abstract class BaseFilter {
	private boolean mNegated;
	private boolean mRestricted;
	private boolean mAlternative;

	public BaseFilter() {
		this(false, false, false);
	}

	public BaseFilter(boolean restrict) {
		this(restrict, false, false);
	}

	public BaseFilter(boolean restrict, boolean alternative) {
		this(restrict, alternative, false);
	}

	/**
	 * @param restrict
	 *            true the "=" operator is used, false the "like" operator is
	 *            used
	 * @param alternative
	 *            true means where clauses are group with "AND", false with "OR"
	 * @param negated
	 *            true means the clauses are negated
	 */
	public BaseFilter(boolean restrict, boolean alternative, boolean negated) {
		mNegated = negated;
		mRestricted = restrict;
		mAlternative = alternative;
	}

	public static class RangeFilter<T> {
		private T mMin;
		private T mMax;

		public RangeFilter(T min, T max) {
			mMin = min;
			mMax = max;
		}

		public T getMin() {
			return mMin;
		}

		public T getMax() {
			return mMax;
		}
	}

	public boolean isNegated() {
		return mNegated;
	}

	public void setNegated(boolean negated) {
		mNegated = negated;
	}

	public boolean isRestricted() {
		return mRestricted;
	}

	public void setRestricted(boolean restricted) {
		mRestricted = restricted;
	}

	public boolean isAlternative() {
		return mAlternative;
	}

	public void setAlternative(boolean alternative) {
		mAlternative = alternative;
	}
}
