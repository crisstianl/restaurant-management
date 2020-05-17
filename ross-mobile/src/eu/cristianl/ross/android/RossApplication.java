package eu.cristianl.ross.android;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Intent;
import eu.cristianl.ross.android.activities.GenericActivity;
import eu.cristianl.ross.android.services.cloudMessaging.CloudMessagingService;
import eu.cristianl.ross.android.utils.SharedPreferencesManager;
import eu.cristianl.ross.android.utils.SystemUtils;
import eu.cristianl.ross.dal.database.DatabaseHelper;

public class RossApplication extends Application {

	// session
	private String mSessionId;
	private long mSessionTime = -1;
	private String mDeviceKey;

	// user data
	private String mUserId;
	private String mUserName;
	private String mUserFullName;

	// application serialization
	private SharedPreferencesManager mPreferences;

	private static RossApplication mInstance = null;

	@Override
	public void onCreate() {
		super.onCreate();

		mInstance = this;
		mPreferences = new SharedPreferencesManager(getApplicationContext());
		DatabaseHelper.createDatabase(getApplicationContext());
		
		// if (getDeviceKey() == null) {
		// CloudMessagingService.register(getApplicationContext());
		// }
	}

	@Override
	public void onTerminate() {
		super.onTerminate();

		mInstance = null;
		mPreferences = null;
		DatabaseHelper.destroyDatabase();
	}

	public static RossApplication getInstance() {
		return mInstance;
	}

	public boolean isUserLogged() {
		return (getUserId() != null) && (getSessionTime() > System.currentTimeMillis());
	}

	public void setUserSession(String sessionId, long expireTime) {
		this.mSessionId = sessionId;
		this.mSessionTime = expireTime;
		this.mPreferences.setSessionId(sessionId);
		this.mPreferences.setSessionTime(expireTime);

		// Post logout receiver
		if (isUserLogged()) {
			setAlarmAt(expireTime);
		}
	}

	public void endSession() {
		setUserSession(null, 0);
		// CloudMessagingService.unregister(getApplicationContext());
	}

	public void setUser(String userId, String userName, String userFullName) {
		this.mUserId = userId;
		this.mUserName = userName;
		this.mUserFullName = userFullName;
		this.mPreferences.setUserId(userId);
		this.mPreferences.setUserName(userName);
		this.mPreferences.setUserFullName(userFullName);
	}

	public void setDeviceKey(String key) {
		this.mDeviceKey = key;
		this.mPreferences.setDeviceKey(key);
	}

	public String getUserId() {
		if (this.mUserId == null) {
			this.mUserId = this.mPreferences.getUserId();
		}
		return this.mUserId;
	}

	public String getUserName() {
		if (this.mUserName == null) {
			this.mUserName = this.mPreferences.getUserName();
		}
		return this.mUserName;
	}

	public String getUserFullName() {
		if (this.mUserFullName == null) {
			this.mUserFullName = this.mPreferences.getUserFullName();
		}
		return this.mUserFullName;
	}

	public String getDeviceKey() {
		if (this.mDeviceKey == null) {
			this.mDeviceKey = this.mPreferences.getDeviceKey();
		}
		return this.mDeviceKey;
	}

	public String getSessionId() {
		if (this.mSessionId == null) {
			this.mSessionId = this.mPreferences.getSessionId();
		}
		return this.mSessionId;
	}

	public long getSessionTime() {
		if (this.mSessionTime < 0) {
			this.mSessionTime = this.mPreferences.getSessionTime();
		}
		return this.mSessionTime;
	}

	public void setAlarmAt(long schedule) {
		final Intent intent = new Intent(getApplicationContext(), GenericActivity.LogoutReceiver.class);
		final PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
		SystemUtils.getAlarmManager().set(AlarmManager.RTC_WAKEUP, schedule, pendingIntent);
	}

	public void postAlarm(long period) {
		final Intent intent = new Intent(getApplicationContext(), GenericActivity.LogoutReceiver.class);
		final PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
		SystemUtils.getAlarmManager().set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + period, pendingIntent);
	}
}
