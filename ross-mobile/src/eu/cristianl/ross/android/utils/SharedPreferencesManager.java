package eu.cristianl.ross.android.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {

	private static final String SESSION_ID_KEY = "SESSION_ID";
	private static final String SESSION_TIME_KEY = "SESSION_TIME";

	private static final String DEVICE_KEY = "DEVICE_KEY";

	private static final String USER_ID_KEY = "USER_ID";
	private static final String USER_NAME_KEY = "USER_NAME";
	private static final String USER_FULLNAME_KEY = "USER_FULLNAME";

	private SharedPreferences mPreferences = null;

	public SharedPreferencesManager(Context context) {
		this.mPreferences = context.getSharedPreferences("RossSharedPreferences", Context.MODE_PRIVATE);
	}

	public String getUserName() {
		return this.mPreferences.getString(USER_NAME_KEY, null);
	}

	public String getUserFullName() {
		return this.mPreferences.getString(USER_FULLNAME_KEY, null);
	}

	public void setUserName(String name) {
		this.mPreferences.edit().putString(USER_NAME_KEY, name).commit();
	}

	public void setUserFullName(String name) {
		this.mPreferences.edit().putString(USER_FULLNAME_KEY, name).commit();
	}

	public String getUserId() {
		return this.mPreferences.getString(USER_ID_KEY, null);
	}

	public void setUserId(String id) {
		this.mPreferences.edit().putString(USER_ID_KEY, id).commit();
	}

	public String getSessionId() {
		return this.mPreferences.getString(SESSION_ID_KEY, null);
	}

	public void setSessionId(String id) {
		this.mPreferences.edit().putString(SESSION_ID_KEY, id).commit();
	}

	public long getSessionTime() {
		return this.mPreferences.getLong(SESSION_TIME_KEY, -1);
	}

	public void setSessionTime(long time) {
		this.mPreferences.edit().putLong(SESSION_TIME_KEY, time).commit();
	}

	public String getDeviceKey() {
		return this.mPreferences.getString(DEVICE_KEY, null);
	}

	public void setDeviceKey(String id) {
		this.mPreferences.edit().putString(DEVICE_KEY, id).commit();
	}

	public void clear() {
		this.mPreferences.edit().clear().commit();
	}
}
