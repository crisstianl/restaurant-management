package eu.cristianl.ross.android.widgets;

import android.content.Context;
import android.graphics.PixelFormat;
import android.text.InputType;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import eu.cristianl.ross.R;
import eu.cristianl.ross.android.RossApplication;
import eu.cristianl.ross.android.listeners.TextChangeListener;

public class Search {
	private static final int DEFAULT_HEIGHT = 120;

	private Context mContext = RossApplication.getInstance().getApplicationContext();
	private View mView;

	private String mText;
	private IFeedbackListener mCallback;

	protected Search(IFeedbackListener callback) {
		mCallback = callback;
	}

	public static Search get(IFeedbackListener callback) {
		return new Search(callback);
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

		addIcon(container);
		addSearchBar(container);

		return container;
	}

	protected void addSearchBar(ViewGroup parent) {
		RelativeLayout container = new RelativeLayout(mContext);

		addEditText(container);
		addSearchIcon(container);

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.CENTER_VERTICAL;

		parent.addView(container, params);
	}

	protected void addEditText(ViewGroup parent) {
		EditText edittext = new EditText(parent.getContext());
		edittext.addTextChangedListener(new SearchTextListener());
		edittext.setOnKeyListener(new KeyListener());
		edittext.setInputType(InputType.TYPE_CLASS_TEXT);
		edittext.setLines(1);

		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

		parent.addView(edittext, params);
	}

	protected void addSearchIcon(ViewGroup parent) {
		ImageView img = new ImageView(parent.getContext());
		img.setBackgroundResource(R.drawable.ic_action_search);

		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

		parent.addView(img, params);
	}

	protected void addIcon(ViewGroup parent) {
		ImageView img = new ImageView(parent.getContext());
		img.setBackgroundResource(R.drawable.ic_action_warning);

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 100);
		params.gravity = Gravity.CENTER_VERTICAL;

		parent.addView(img, params);
	}

	protected class SearchTextListener extends TextChangeListener {
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			mText = s.toString();
		}
	}

	private class KeyListener implements View.OnKeyListener {

		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			if (event.getAction() == KeyEvent.ACTION_UP) {
				if (keyCode == KeyEvent.KEYCODE_ENTER) {
					if (mCallback != null) {
						mCallback.doFeedback(mText);
					}
					dismiss();
					return true;
				} else if (keyCode == KeyEvent.KEYCODE_BACK) {
					dismiss();
					return true;
				}
			}
			return false;
		}
	}

	public static interface IFeedbackListener {
		public void doFeedback(String text);
	}

}
