package eu.cristianl.ross.android.fragments.navigator;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import eu.cristianl.ross.R;
import eu.cristianl.ross.android.RossApplication;
import eu.cristianl.ross.android.activities.GenericActivity;
import eu.cristianl.ross.android.activities.LoginActivity;
import eu.cristianl.ross.android.fragments.GenericFragment;
import eu.cristianl.ross.logging.AppLogger;

public class Navigator {
	private GenericActivity mParentActivity;
	private FragmentManager mFragmentManager;
	private int mActivityFragmentContainerRes;

	public Navigator(GenericActivity parentActivity, int activityFragmentContainerRes) {
		mParentActivity = parentActivity;
		mFragmentManager = parentActivity.getFragmentManager();
		mActivityFragmentContainerRes = activityFragmentContainerRes;
	}

	/** Return true to manage the back action, return false to exit application */
	public boolean tryBackContent() {
		GenericFragment currentFragment = getCurrentFragment();
		if (currentFragment != null && currentFragment.backContent()) {
			return true;
		}

		return backContent();
	}

	public boolean backContent() {
		if (mFragmentManager.getBackStackEntryCount() > 0) {
			mFragmentManager.popBackStack();
			return true;
		}
		return false;
	}

	public void resetNavigationHistory() {
		if (canNavigate()) {
			mFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
		}
	}

	public boolean startActivity(Class<? extends Activity> activity) {
		return startActivity(activity, null);
	}

	public boolean startActivity(Class<? extends Activity> activity, Bundle bundle) {
		if (!canNavigate())
			return false;

		Intent intent = new Intent(mParentActivity, activity);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		try {
			mParentActivity.startActivity(intent);
			return true;
		}

		catch (ActivityNotFoundException e) {
			AppLogger.error(e, "Navigator-failed to start activity");
		}
		return false;
	}

	public ComponentName startService(Intent service) {
		return mParentActivity.startService(service);
	}

	public boolean stopService(Intent service) {
		return mParentActivity.stopService(service);
	}

	public void exit() {
		mParentActivity.finish();
	}

	public void goToContent(GenericFragment contentFragment) {
		goToContent(contentFragment, null, true);
	}

	public void goToContent(GenericFragment contentFragment, Bundle args) {
		goToContent(contentFragment, args, true);
	}

	public void goToContent(GenericFragment contentFragment, boolean addToBackStack) {
		goToContent(contentFragment, null, addToBackStack);
	}

	public void goToContent(GenericFragment contentFragment, Bundle args, boolean addToBackStack) {
		if (!canNavigate() || contentFragment == null) {
			return;
		}
		if (args != null) {
			contentFragment.setArguments(args);
		}

		FragmentTransaction ft = mFragmentManager.beginTransaction();
		ft.replace(mActivityFragmentContainerRes, contentFragment, contentFragment.getFragmentNameTag());
		ft.setTransition(FragmentTransaction.TRANSIT_NONE);

		if (addToBackStack) {
			ft.addToBackStack(contentFragment.getFragmentNameTag());
		}

		ft.commit();
	}

	public void logOut() {
		RossApplication.getInstance().endSession();
		if (!(mParentActivity instanceof LoginActivity)) {
			startActivity(LoginActivity.class);
		}
	}

	public GenericActivity getParentActivity() {
		return mParentActivity;
	}

	public GenericFragment getCurrentFragment() {
		Fragment fragment = mFragmentManager.findFragmentById(R.id.activity_main_container);
		if (fragment != null) {
			return (GenericFragment) fragment;
		}
		return null;
	}

	protected boolean canNavigate() {
		return true;
	}
}
