package eu.cristianl.ross.android.fragments.home;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import eu.cristianl.ross.R;
import eu.cristianl.ross.android.fragments.GenericFragment;
import eu.cristianl.ross.android.utils.Resources;
import eu.cristianl.ross.android.widgets.Question;
import eu.cristianl.ross.dal.database.DatabaseHelper;
import eu.cristianl.ross.entities.Order;
import eu.cristianl.ross.entities.OrderRow;
import eu.cristianl.ross.entities.support.DocStatusType;

public class HomeFragment extends GenericFragment implements OnItemClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View retView = inflater.inflate(R.layout.fragment_home, null);
		setupMenuList(retView);
		return retView;
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View row, int position, long id) {
		MenuListAdapter adapter = (MenuListAdapter) adapterView.getAdapter();
		adapter.getItem(position).perform();
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		menu.findItem(R.id.main_options_menu_home).setVisible(false);
		menu.findItem(R.id.main_options_menu_search).setVisible(false);
	}

	private void setupMenuList(View parent) {
		GridView gridView = (GridView) parent.findViewById(R.id.fragment_home_menu_grid);
		gridView.setAdapter(new MenuListAdapter(getActivity(), buildMenu()));
		gridView.setOnItemClickListener(this);
	}

	private List<MenuListAdapter.MenuItem> buildMenu() {
		List<MenuListAdapter.MenuItem> retList = new ArrayList<MenuListAdapter.MenuItem>();
		retList.add(new MenuListAdapter.MenuItem(R.string.fragment_home_item_newOrder, R.drawable.ic_action_new_event) {
			@Override
			public void perform() {
				goToNewOrder();
			}
		});

		retList.add(
				new MenuListAdapter.MenuItem(R.string.fragment_home_item_pending, R.drawable.ic_action_add_to_queue) {
					@Override
					public void perform() {
						goToOrderList(DocStatusType.PENDING);
					}
				});

		retList.add(new MenuListAdapter.MenuItem(R.string.fragment_home_item_submitted, R.drawable.ic_action_upload) {
			@Override
			public void perform() {
				goToOrderList(DocStatusType.SUBMITTED);
			}
		});

		retList.add(
				new MenuListAdapter.MenuItem(R.string.fragment_home_item_ready, R.drawable.ic_action_import_export) {
					@Override
					public void perform() {
						goToOrderList(DocStatusType.READY);
					}
				});

		retList.add(new MenuListAdapter.MenuItem(R.string.fragment_home_item_clear, R.drawable.ic_action_refresh) {
			@Override
			public void perform() {
				Question.get(Resources.getString(R.string.fragment_home_item_clear_warning),
						Resources.getString(R.string.dictionary_yes), Resources.getString(R.string.dictionary_no),
						getResetQuestionFeedback()).show();
			}
		});

		retList.add(new MenuListAdapter.MenuItem(R.string.fragment_home_item_exit, R.drawable.ic_action_person) {
			@Override
			public void perform() {
				Question.get(Resources.getString(R.string.fragment_home_item_exit_warning),
						Resources.getString(R.string.dictionary_yes), Resources.getString(R.string.dictionary_no),
						getQuestionFeedback()).show();
			}
		});

		return retList;
	}

	private Question.IFeedbackListener getQuestionFeedback() {
		return new Question.IFeedbackListener() {
			@Override
			public void doFeedback(boolean arg) {
				if (arg) {
					mNavigator.logOut();
				}
			}
		};
	}

	private Question.IFeedbackListener getResetQuestionFeedback() {
		return new Question.IFeedbackListener() {
			@Override
			public void doFeedback(boolean arg) {
				if (arg) {
					DatabaseHelper.getInstance().clearTable(OrderRow.class);
					DatabaseHelper.getInstance().clearTable(Order.class);
				}
			}
		};
	}
}
