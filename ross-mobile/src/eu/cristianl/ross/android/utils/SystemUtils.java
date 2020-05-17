package eu.cristianl.ross.android.utils;

import java.util.Locale;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.net.ConnectivityManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import eu.cristianl.ross.android.RossApplication;
import eu.cristianl.ross.logging.AppLogger;

public class SystemUtils {

	public static AlarmManager getAlarmManager() {
		return (AlarmManager) getSystemService(Context.ALARM_SERVICE);
	}

	public static NotificationManager getNotificationManager() {
		return (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	}

	public static ConnectivityManager getConnectivityManager() {
		return (ConnectivityManager) RossApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
	}

	public static Object getSystemService(String serviceName) {
		return RossApplication.getInstance().getSystemService(serviceName);
	}

	public static Locale getDeviceLocale() {
		return RossApplication.getInstance().getResources().getConfiguration().locale;
	}

	public static void showSoftKeyboard(View v) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
	}

	public static void hideSoftKeyboard(View v) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
	}

	public static int getAppVersion() {
		return getAppPackageInfo().versionCode;
	}

	public static String getAppVersionName() {
		return getAppPackageInfo().versionName;
	}

	private static PackageInfo getAppPackageInfo() {
		try {
			final Context context = RossApplication.getInstance().getApplicationContext();
			return context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
		} catch (Exception e) {
			AppLogger.error(e, e.getMessage());
		}
		return null;
	}
}
