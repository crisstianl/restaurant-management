package eu.cristianl.ross.android.widgets;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import eu.cristianl.ross.R;
import eu.cristianl.ross.android.RossApplication;
import eu.cristianl.ross.android.utils.Resources;

public class Console {
	private static final int DEFAULT_HEIGHT = 120;
	private static final int WAIT_TIME = 5000;

	public static final int QUESTION = 3;
	public static final int ERROR = 2;
	public static final int WARN = 1;
	public static final int INFO = 0;

	protected String mText;
	protected int mLevel;

	protected int mWaitTime = WAIT_TIME;

	protected Context mContext = RossApplication.getInstance().getApplicationContext();
	protected View mView;

	protected Console(String text, int level) {
		this(text, level, WAIT_TIME);
	}

	protected Console(String text, int level, int waitTime) {
		mText = text;
		mLevel = level;
		mWaitTime = waitTime;
	}

	public static Console get(String message) {
		return new Console(message, INFO);
	}

	public final void error() {
		mLevel = ERROR;
		setupLayout(true);
	}

	public final void warn() {
		mLevel = WARN;
		setupLayout(true);
	}

	public final void info() {
		mLevel = INFO;
		setupLayout(true);
	}

	public final void dismiss() {
		((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE)).removeView(mView);
		mView = null;
		mContext = null;
	}

	protected final void setupLayout(boolean autoDismiss) {
		WindowManager windowManager = (WindowManager) mContext
				.getSystemService(Context.WINDOW_SERVICE);

		WindowManager.LayoutParams params = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_PHONE, getWindowFlag(), PixelFormat.TRANSLUCENT);
		params.gravity = Gravity.BOTTOM;

		mView = onCreateView();
		windowManager.addView(mView, params);

		if (autoDismiss) {
			getDismissHandler().sendEmptyMessageDelayed(0, mWaitTime);
		}
	}

	protected View onCreateView() {
		LinearLayout container = new LinearLayout(mContext);
		container.setMinimumHeight(DEFAULT_HEIGHT);
		container.setBackgroundResource(R.color.bright_foreground_disabled_holo_dark);

		addIcon(container, mLevel);
		addMessageText(container, mText, mLevel);

		return container;
	}

	protected void addMessageText(ViewGroup parent, String text, int level) {
		TextView message = new TextView(parent.getContext());
		message.setText(text);
		message.setTextAppearance(parent.getContext(), R.style.textLabelDesc);
		message.setTextColor(getColor(level));

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.CENTER_VERTICAL;

		parent.addView(message, params);
	}

	protected void addIcon(ViewGroup parent, int level) {
		ImageView img = new ImageView(parent.getContext());
		img.setBackgroundResource(getIconRes(level));

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 100);
		params.gravity = Gravity.CENTER_VERTICAL;
		parent.addView(img, params);
	}

	protected int getWindowFlag() {
		return WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
	}

	private static int getColor(int level) {
		switch (level) {
		case ERROR:
			return Resources.getColor(R.color.text_error);
		case WARN:
			return Resources.getColor(R.color.text_warning);
		case INFO:
			return Resources.getColor(R.color.text_info);
		case QUESTION:
			return Resources.getColor(R.color.text_warning);
		}
		return Resources.getColor(R.color.text_normal);
	}

	private static int getIconRes(int level) {
		switch (level) {
		case ERROR:
			return R.drawable.ic_action_error;
		case WARN:
			return R.drawable.ic_action_warning;
		case INFO:
			return R.drawable.ic_action_info;
		case QUESTION:
			return R.drawable.ic_action_question;
		}
		return R.drawable.ic_action_info;
	}

	private Handler getDismissHandler() {
		return new Handler() {
			@Override
			public void handleMessage(Message msg) {
				dismiss();
			}
		};
	}
}
