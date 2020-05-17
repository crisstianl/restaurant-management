package eu.cristianl.ross.android.widgets;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import eu.cristianl.ross.R;

public class Question extends Console implements View.OnClickListener {
	private static final int POSITIVE_ID = 20;
	private static final int NEGATIVE_ID = 21;

	private String mTextPositive;
	private String mTextNegative;
	private IFeedbackListener mCallback;

	private Question(String text, String yes, String no, IFeedbackListener callback) {
		super(text, QUESTION);
		mTextPositive = yes;
		mTextNegative = no;
		mCallback = callback;
	}

	public static Question get(String message, String yes, String no, IFeedbackListener callback) {
		return new Question(message, yes, no, callback);
	}

	public final void show() {
		setupLayout(false);
	}

	@Override
	protected View onCreateView() {
		LinearLayout container = new LinearLayout(mContext);
		container.setOrientation(LinearLayout.VERTICAL);
		container.setBackgroundResource(R.color.bright_foreground_disabled_holo_dark);

		container.addView(super.onCreateView());

		addButtons(container);

		return container;
	}

	@Override
	public void onClick(View v) {
		if (mCallback != null) {
			mCallback.doFeedback(v.getId() == POSITIVE_ID);
		}

		dismiss();
	}

	@Override
	protected int getWindowFlag() {
		return 0;
	}

	private void addButtons(ViewGroup parent) {
		LinearLayout container = new LinearLayout(mContext);

		if (mTextPositive != null) {
			addButton(container, mTextPositive, POSITIVE_ID);
		}
		if (mTextNegative != null) {
			addButton(container, mTextNegative, NEGATIVE_ID);
		}

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);

		parent.addView(container, params);
	}

	private void addButton(ViewGroup parent, String text, int id) {
		Button button = new Button(parent.getContext());
		button.setText(text);
		button.setId(id);
		button.setOnClickListener(this);

		parent.addView(button, new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 50));
	}

	public static interface IFeedbackListener {
		public void doFeedback(boolean arg);
	}
}
