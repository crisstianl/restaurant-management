package eu.cristianl.ross.android.widgets;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import eu.cristianl.ross.R;
import eu.cristianl.ross.android.RossApplication;

public class SingleChoice<T> {
	private static final int DEFAULT_HEIGHT = 120;

	protected Context mContext = RossApplication.getInstance().getApplicationContext();
	protected View mView;

	protected List<T> mItems = new ArrayList<T>();
	private IFeedbackListener<T> mListener;

	protected SingleChoice(T[] items, IFeedbackListener<T> listener) {
		for (T item : items) {
			mItems.add(item);
		}
		mListener = listener;
	}

	protected SingleChoice(List<T> items, IFeedbackListener<T> listener) {
		mItems.addAll(items);
		mListener = listener;
	}

	public static <T> SingleChoice<T> get(T[] items, IFeedbackListener<T> listener) {
		return new SingleChoice<T>(items, listener);
	}

	public static <T> SingleChoice<T> get(List<T> items, IFeedbackListener<T> listener) {
		return new SingleChoice<T>(items, listener);
	}

	public final void show() {
		setupLayout();
	}

	public final void dismiss() {
		((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE)).removeView(mView);
		mView = null;
		mContext = null;
	}

	private final void setupLayout() {
		WindowManager windowManager = (WindowManager) mContext
				.getSystemService(Context.WINDOW_SERVICE);

		WindowManager.LayoutParams params = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_PHONE, 0, PixelFormat.TRANSLUCENT);
		params.gravity = Gravity.BOTTOM;

		mView = onCreateView();
		windowManager.addView(mView, params);
	}

	protected View onCreateView() {
		LinearLayout container = new LinearLayout(mContext);
		container.setMinimumHeight(DEFAULT_HEIGHT);
		container.setBackgroundResource(R.color.bright_foreground_disabled_holo_dark);

		addListView(container);

		return container;
	}

	private void addListView(LinearLayout container) {
		ListView listview = new ListView(mContext);
		listview.setAdapter(getAdapter());

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View row, int position, long id) {
				@SuppressWarnings("unchecked")
				T item = (T) adapterView.getItemAtPosition(position);

				if (mListener != null) {
					mListener.doFeedback(item);
				}

				dismiss();
			}
		});

		listview.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
					dismiss();
					return true;
				}
				return false;
			}
		});

		container.addView(listview, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
	}

	protected ListAdapter getAdapter() {
		return new ArrayAdapter<T>(mContext, android.R.layout.simple_list_item_single_choice,
				mItems);
	}

	public interface IFeedbackListener<T> {
		public void doFeedback(T arg);
	}
}
