package eu.cristianl.ross.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import eu.cristianl.ross.logging.AppLogger;

public class DateUtils {
	public static final String DATE_FORMAT = "dd/MM/yyyy";
	public static final String TIME_FORMAT = "HH:mm:ss";
	public static final String DATABASE_DATE_FORMAT = "yyyy-MM-dd";
	public static final String DATABASE_TIME_FORMAT = "HH:mm:ss";
	public static final String DATABASE_TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";

	private static final SimpleDateFormat mFormatter = new SimpleDateFormat();

	public static String getDateString(Date date) {
		return format(date, DATE_FORMAT);
	}

	public static String getTimeString(Date date) {
		return format(date, TIME_FORMAT);
	}

	public static String getDatabaseDateString(Date date) {
		return format(date, DATABASE_DATE_FORMAT);
	}

	public static String getDatabaseTimeString(Date date) {
		return format(date, DATABASE_TIME_FORMAT);
	}

	public static String getDatabaseTimestampString(Date date) {
		return format(date, DATABASE_TIMESTAMP_FORMAT);
	}

	public static String format(Date date, String format) {
		mFormatter.applyPattern(format);
		return mFormatter.format(date);
	}

	public static Date parse(String date, String format) {
		try {
			mFormatter.applyPattern(format);
			return mFormatter.parse(date);
		} catch (Exception e) {
			AppLogger.error(e, "Failed to parse date");
		}
		return null;
	}

	public static Date now() {
		return new Date();
	}

}
