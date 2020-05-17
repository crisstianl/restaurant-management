package eu.cristianl.ross.android.utils;

import android.graphics.drawable.Drawable;
import eu.cristianl.ross.R;
import eu.cristianl.ross.android.RossApplication;

public class Resources {
	
	public static String getString(int stringRes) {
		return RossApplication.getInstance().getApplicationContext().getResources().getString(stringRes);
	}

	public static String getFormattedString(int stringRes, Object... args) {
		return String.format(getString(stringRes), args);
	}

	public static String[] getStringArray(int stringRes) {
		return RossApplication.getInstance().getApplicationContext().getResources().getStringArray(stringRes);
	}

	public static float getSize(int dimenRes) {
		return RossApplication.getInstance().getApplicationContext().getResources().getDimension(dimenRes);
	}

	public static int getColor(int colorRes) {
		return RossApplication.getInstance().getApplicationContext().getResources().getColor(colorRes);
	}

	public static Drawable getDrawable(int drawableId) {
		return RossApplication.getInstance().getApplicationContext().getResources().getDrawable(drawableId);
	}

	public static String getDateFormat() {
		return getString(R.string.date_format);
	}

	public static String getTimeFormat() {
		return getString(R.string.time_format);
	}

	public static String getTimeLongFormat() {
		return getString(R.string.time_long_format);
	}

	public static String getDateTimeFormat() {
		return getString(R.string.date_time_format);
	}

	public static String getTimeDateFormat() {
		return getString(R.string.time_date_format);
	}

	public static String getDateDatabaseFormat() {
		return getString(R.string.date_database_format);
	}

	public static String getTimestampDatabaseFormat() {
		return getString(R.string.timestamp_database_format);
	}

	public static String getServerAddr() {
		return getString(R.string.SERVER_ADDRESS);
	}
}
