package eu.cristianl.ross.android.activities;

import java.util.Date;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import eu.cristianl.ross.R;
import eu.cristianl.ross.android.fragments.navigator.Navigator;
import eu.cristianl.ross.android.utils.Resources;
import eu.cristianl.ross.android.widgets.Console;

public abstract class GenericActivity extends Activity {
	private static final int DEFAULT_BACK_CLICKS_TRIGGER = 2;
	private static final int DEFAULT_BACK_CLICKS_THRESHOLD_MS = 2000;
	private static final int DEFAULT_DELAY_FOR_BACK_TRIAL_NOTIFICATION_MS = 500;

	protected Navigator mNavigator;

	private int mBackClicksTrigger = DEFAULT_BACK_CLICKS_TRIGGER;
	private long mBackClicksThreshold = DEFAULT_BACK_CLICKS_THRESHOLD_MS;
	private int mBackClicksCount;
	private long mBackClicksLastTime;

	private Handler mBackWarnHandler = new Handler();
	private Runnable mBackWarnTask;

	@Override
	protected final void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		internalInitializeData(savedInstanceState);
		internalInitializeViews();
	}

	@Override
	public void onBackPressed() {
		if (!mNavigator.tryBackContent()) {
			// One tap display message, two taps close app
			if (tryBackContent()) {
				finish();
			}
		}
	}

	public Navigator getNavigator() {
		return mNavigator;
	}

	protected Context getContext() {
		return getApplicationContext();
	}

	protected void setBackClickTrigger(int value) {
		mBackClicksTrigger = value;
	}

	private void internalInitializeData(Bundle savedInstance) {
		mNavigator = new Navigator(this, getFragmentContainerId());
		initializeData(savedInstance);

		mBackWarnTask = new Runnable() {
			public void run() {
				Console.get(Resources.getString(R.string.activity_generic_exit_message)).info();
			}
		};
	}

	private void internalInitializeViews() {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		initializeViews();
	}

	protected abstract void initializeData(Bundle savedInstance);

	protected abstract void initializeViews();

	protected int getFragmentContainerId() {
		return -1;
	}

	public final class LogoutReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			Console.get(Resources.getString(R.string.activity_generic_session_end_warning)).warn();
			mNavigator.logOut();
		}
	}

	private final boolean tryBackContent() {
		long nowTime = new Date().getTime();

		if (nowTime > mBackClicksLastTime + mBackClicksThreshold) {
			// restart count
			mBackClicksCount = 0;
			mBackClicksLastTime = nowTime;
		}

		mBackClicksCount++;

		if (mBackClicksCount >= mBackClicksTrigger) {
			mBackWarnHandler.removeCallbacks(mBackWarnTask);
			mBackClicksCount = 0;
			return true;
		} else if (mBackClicksCount == 1) {
			mBackWarnHandler.removeCallbacks(mBackWarnTask);
			mBackWarnHandler.postDelayed(mBackWarnTask,
					DEFAULT_DELAY_FOR_BACK_TRIAL_NOTIFICATION_MS);
		}

		return false;
	}
}
