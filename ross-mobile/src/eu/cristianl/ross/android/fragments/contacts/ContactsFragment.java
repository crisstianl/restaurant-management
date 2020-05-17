package eu.cristianl.ross.android.fragments.contacts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import eu.cristianl.ross.R;
import eu.cristianl.ross.android.fragments.GenericFragment;
import eu.cristianl.ross.android.fragments.order.OrderFragment;
import eu.cristianl.ross.android.widgets.ListHeader;
import eu.cristianl.ross.android.widgets.Search;
import eu.cristianl.ross.entities.Contact;

public class ContactsFragment extends GenericFragment {
	private ContactsListAdapter mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAdapter = new ContactsListAdapter(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View retView = inflater.inflate(R.layout.fragment_contacts, null);
		setupContactsListView(retView);
		setupContactsListHeader(retView);

		return retView;
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		menu.findItem(R.id.main_options_menu_new).setVisible(false);
	}

	@Override
	protected void search() {
		Search.get(getSearchFeedback()).show();
	}

	private Search.IFeedbackListener getSearchFeedback() {
		return new Search.IFeedbackListener() {
			@Override
			public void doFeedback(String text) {
				mAdapter.setSearchFilter(text);
				mAdapter.reset();
			}
		};
	}

	private void setupContactsListHeader(View parent) {
		ListHeader listheader = (ListHeader) parent.findViewById(R.id.fragment_contacts_list_header);
		listheader.setSortTypeChangeListener(new ListHeader.SortTypeChangeListener() {
			@Override
			public void onSortTypeChanged(int columnId, boolean asc) {
				mAdapter.sort(columnId, asc);
			}
		});
	}

	private void setupContactsListView(View parent) {
		ListView listView = (ListView) parent.findViewById(R.id.fragment_contacts_list);
		listView.setAdapter(mAdapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View row, int position, long id) {
				gotToItemsFragment((Contact) adapterView.getItemAtPosition(position));
			}
		});
	}

	private void gotToItemsFragment(Contact contact) {
		Bundle args = new Bundle();
		args.putInt(OrderFragment.CONTACT_ID_KEY, contact.getId());
		mNavigator.goToContent(new OrderFragment(), args);
	}
}
