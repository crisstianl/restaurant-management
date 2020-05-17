package eu.cristianl.ross.android.activities;

import android.os.Bundle;
import eu.cristianl.ross.R;
import eu.cristianl.ross.android.fragments.home.HomeFragment;

public class MainActivity extends GenericActivity {

	@Override
	protected void initializeData(Bundle savedInstance) {
	}

	@Override
	protected void initializeViews() {
		setContentView(R.layout.activity_main);
		mNavigator.goToContent(new HomeFragment(), false);
	}

	@Override
	protected int getFragmentContainerId() {
		return R.id.activity_main_container;
	}
}
