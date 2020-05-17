package eu.cristianl.ross.dal.utils;

import java.util.Date;

import eu.cristianl.ross.utils.DateUtils;

public class SQLDate extends Date {
	private static final long serialVersionUID = 1L;

	public SQLDate() {
		super();
	}

	public SQLDate(long date) {
		super(date);
	}

	@Override
	public String toString() {
		return DateUtils.getDatabaseDateString(this);
	}

}
