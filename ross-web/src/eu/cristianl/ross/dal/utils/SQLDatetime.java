package eu.cristianl.ross.dal.utils;

import java.util.Date;

import eu.cristianl.ross.utils.DateUtils;

public class SQLDatetime extends Date {
	private static final long serialVersionUID = 1L;

	public SQLDatetime() {
		super();
	}

	public SQLDatetime(long date) {
		super(date);
	}

	@Override
	public String toString() {
		return DateUtils.getDatabaseTimestampString(this);
	}
}
