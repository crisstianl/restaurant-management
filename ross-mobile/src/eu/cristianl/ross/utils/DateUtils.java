package eu.cristianl.ross.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import eu.cristianl.ross.android.utils.Resources;
import eu.cristianl.ross.logging.AppLogger;

public class DateUtils {

	public static final String DATE_FORMAT;
	public static final String TIME_FORMAT;
	public static final String TIME_LONG_FORMAT;
	public static final String DATE_TIME_FORMAT;
	public static final String TIME_DATE_FORMAT;
	public static final String DATE_DATABASE_FORMAT;
	public static final String TIMESTAMP_DATABASE_FORMAT;

	private static final SimpleDateFormat mFormatter = new SimpleDateFormat();

	static {
		DATE_FORMAT = Resources.getDateFormat();
		TIME_FORMAT = Resources.getTimeFormat();
		TIME_LONG_FORMAT = Resources.getTimeLongFormat();
		TIME_DATE_FORMAT = Resources.getTimeDateFormat();
		DATE_TIME_FORMAT = Resources.getDateTimeFormat();
		DATE_DATABASE_FORMAT = Resources.getDateDatabaseFormat();
		TIMESTAMP_DATABASE_FORMAT = Resources.getTimestampDatabaseFormat();
	}

	/** Parse string date using the format yyyy-MM-dd */
	public static Date tryParseDatabaseDate(String formattedDate, Date defaultValue) {
		return tryParse(formattedDate, DATE_DATABASE_FORMAT, defaultValue);
	}

	/** Parse string date using the format yyyy-MM-dd HH:mm:ss */
	public static Date tryParseDatabaseTimestamp(String formattedDate, Date defaultValue) {
		return tryParse(formattedDate, TIMESTAMP_DATABASE_FORMAT, defaultValue);
	}

	/** Return string date in format yyyy-MM-dd */
	public static String getDatabaseDateString(Date date) {
		return format(date, DATE_DATABASE_FORMAT);
	}

	/** Return string date in format yyyy-MM-dd HH:mm:ss */
	public static String getDatabaseTimestampString(Date date) {
		return format(date, TIMESTAMP_DATABASE_FORMAT);
	}

	/** Return string date in format HH:mm:ss */
	public static String getTimeLongString(Date date) {
		return format(date, TIME_LONG_FORMAT);
	}

	/** Return string date in format dd/MM/yyyy */
	public static String getDateString(Date date) {
		return format(date, DATE_FORMAT);
	}

	/** Return a string date in format HH:mm dd/MM/yyyy */
	public static String getTimeDateString(Date date) {
		return format(date, TIME_DATE_FORMAT);
	}

	public static String getString(Date date, String format) {
		return format(date, format);
	}

	public static Date now() {
		return new Date();
	}

	/**
	 * Returns a new Date object with the time of the day reset to 0. The
	 * resulting object can also be used for comparisons between days.
	 * 
	 * @param date
	 * @return a new Date object with hours, minutes, seconds, milliseconds set
	 *         to 0, or null if the input parameter was null
	 */
	public static Date getStartOfDay(Date date) {
		if (date == null)
			return date;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	/**
	 * Returns a new date created by adding the units to the year, month, day,
	 * hour, minute, second or millisecond from the initialDate. It uses the
	 * default Calendar to compute the new date.
	 * 
	 * @param initialDate
	 *            the date from where you want to start the addition
	 * @param calendarTimeUnit
	 *            it is one of the followings: Calendar.YEAR, Calendar.MONTH,
	 *            Calendar.DATE (for the day), Calendar.HOUR, Calendar.MINUTE,
	 *            Calendar.SECOND, Calendar.MILLISECOND. The behavior is
	 *            undefined for other values.
	 * @param units
	 *            the number want to add to the month,year,or day
	 * @return the new Date
	 * @see Calendar
	 */
	public static Date add(Date initialDate, int calendarTimeUnit, int units) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(initialDate);

		calendar.add(calendarTimeUnit, units);

		return calendar.getTime();
	}

	private static String format(Date date, String format) {
		mFormatter.applyPattern(format);
		return mFormatter.format(date);
	}

	private static Date tryParse(String formattedDate, String format, Date defaultValue) {
		mFormatter.applyPattern(format);
		try {
			return mFormatter.parse(formattedDate);
		} catch (Exception e) {
			AppLogger.error("Failed to parse string {} into date", formattedDate);
			return defaultValue;
		}
	}

}
