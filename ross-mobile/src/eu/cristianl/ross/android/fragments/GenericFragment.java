package eu.cristianl.ross.android.fragments;

import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import eu.cristianl.ross.R;
import eu.cristianl.ross.android.RossApplication;
import eu.cristianl.ross.android.activities.GenericActivity;
import eu.cristianl.ross.android.fragments.contacts.ContactsFragment;
import eu.cristianl.ross.android.fragments.docs.DocsListFragment;
import eu.cristianl.ross.android.fragments.home.HomeFragment;
import eu.cristianl.ross.android.fragments.navigator.Navigator;
import eu.cristianl.ross.android.widgets.Console;
import eu.cristianl.ross.android.widgets.Search;
import eu.cristianl.ross.entities.support.DocStatusType;
import eu.cristianl.ross.utils.DateUtils;

public abstract class GenericFragment extends Fragment {
	protected Navigator mNavigator;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mNavigator = ((GenericActivity) getActivity()).getNavigator();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Build action bar menu
		setHasOptionsMenu(true);
		// Build action bar view
		setupActionBarView(inflater, savedInstanceState);

		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.main_options_menu, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.main_options_menu_home) {
			goToHome();
		} else if (item.getItemId() == R.id.main_options_menu_new) {
			goToNewOrder();
		} else if (item.getItemId() == R.id.main_options_menu_ready) {
			goToOrderList(DocStatusType.READY);
		} else if (item.getItemId() == R.id.main_options_menu_pending) {
			goToOrderList(DocStatusType.PENDING);
		} else if (item.getItemId() == R.id.main_options_menu_inprogress) {
			goToOrderList(DocStatusType.SUBMITTED);
		} else if (item.getItemId() == R.id.main_options_menu_search) {
			search();
		} else {
			return false;
		}
		return true;
	}

	/** True to handle back action, false pop back stack */
	public boolean backContent() {
		return false;
	}

	protected View onCreateActionBar(ActionBar actionBar, LayoutInflater inflater, Bundle savedState) {
		final String username = RossApplication.getInstance().getUserFullName();
		if (username == null) {
			setupActionBarTitle("Unknown", "", R.drawable.ic_action_person);

		} else {
			final String[] names = username.split(" ", 2);
			if (names != null && names.length == 2) {
				setupActionBarTitle(names[0], names[1], R.drawable.ic_action_person);
			} else {
				setupActionBarTitle(username, "", R.drawable.ic_action_person);
			}
		}
		return null;
	}

	protected void setupActionBarTitle(String title, String subtitle, int iconId) {
		ActionBar actionBar = getActivity().getActionBar();
		actionBar.setTitle(title);
		actionBar.setSubtitle(subtitle);

		ImageView logo = ((ImageView) getActivity().findViewById(android.R.id.home));
		logo.setImageResource(iconId);
		logo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				final StringBuilder message = new StringBuilder();
				message.append(RossApplication.getInstance().getUserFullName()).append("\n");
				message.append(DateUtils
						.getTimeDateString(new java.util.Date(RossApplication.getInstance().getSessionTime())));
				Console.get(message.toString()).info();
			}
		});
	}

	protected final void goToHome() {
		mNavigator.resetNavigationHistory();
		mNavigator.goToContent(new HomeFragment());
	}

	protected final void goToNewOrder() {
		mNavigator.resetNavigationHistory();
		mNavigator.goToContent(new ContactsFragment());
	}

	protected final void goToOrderList(DocStatusType docStatus) {
		mNavigator.resetNavigationHistory();
		Bundle args = new Bundle();
		args.putString(DocsListFragment.DOC_STATUS_KEY, docStatus.getCode());
		mNavigator.goToContent(new DocsListFragment(), args);
	}

	protected void search() {
		Search.get(null).show();
	}

	private final void setupActionBarView(LayoutInflater inflater, Bundle savedState) {
		ActionBar actionBar = getActivity().getActionBar();
		View actionBarView = onCreateActionBar(actionBar, inflater, savedState);
		if (actionBarView != null) {
			actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
			actionBar.setCustomView(actionBarView);
		}
	}

	public String getFragmentNameTag() {
		return null;
	}
}
