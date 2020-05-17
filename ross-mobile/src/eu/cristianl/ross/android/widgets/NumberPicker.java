package eu.cristianl.ross.android.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import eu.cristianl.ross.R;
import eu.cristianl.ross.utils.Utils;

public class NumberPicker extends LinearLayout implements View.OnClickListener {
	private static final int PLUS_BTN_ID = 100;
	private static final int MINUS_BTN_ID = 200;
	private static final int EDIT_TEXT_ID = 300;

	private float mCurrentValue = 0F;
	private float mPreviousValue = 0F;
	private float mIncrementValue = 1F;
	private float mMaxValue = 10000F;
	private float mMinValue = 0F;

	private NumberType mNumberType = NumberType.INTEGER;
	private ValueChangeListener mValueChangeListener;

	private PickerEditText mNumberEdx;

	public NumberPicker(Context context, AttributeSet attrs) {
		this(context, attrs, -1);
	}

	public NumberPicker(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NumberPicker);
		buildLayoutView(context, attrs, typedArray);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case PLUS_BTN_ID:
			increment();
			break;
		case MINUS_BTN_ID:
			decrement();
			break;
		}
	}

	private void increment() {
		float newValue = Math.min(mMaxValue, mCurrentValue + mIncrementValue);
		applyValue(newValue);
	}

	private void decrement() {
		float newValue = Math.max(mMinValue, mCurrentValue - mIncrementValue);
		applyValue(newValue);
	}

	protected boolean applyValue(float newValue) {
		boolean retValue = false;
		if (mValueChangeListener != null) {
			if (isValid(newValue) && mValueChangeListener.isValidValue(newValue)) {
				retValue = true;
			}
		} else if (isValid(newValue)) {
			retValue = true;
		}

		// update value and notify listeners
		if (retValue) {
			setValue(newValue);
			onValueChanged();
		}
		return retValue;
	}

	protected boolean isValid(float newValue) {
		return (newValue != mCurrentValue);
	}

	private void buildLayoutView(Context context, AttributeSet attrs, TypedArray typedArray) {
		PickerButton plusBtn = buildButton(context, attrs, typedArray, "+", PLUS_BTN_ID);
		PickerButton minusBtn = buildButton(context, attrs, typedArray, "-", MINUS_BTN_ID);
		mNumberEdx = buildNumberEditText(context, attrs, typedArray, EDIT_TEXT_ID);

		LinearLayout.LayoutParams params = new LayoutParams(minusBtn.getButtonWidth(),
				minusBtn.getButtonHeight());
		addView(minusBtn, params);

		params = new LayoutParams(mNumberEdx.getEditTextWidth(), mNumberEdx.getEditTextHeight());
		addView(mNumberEdx, params);

		params = new LayoutParams(plusBtn.getButtonWidth(), plusBtn.getButtonHeight());
		addView(plusBtn, params);
	}

	protected PickerButton buildButton(Context context, AttributeSet attrs, TypedArray typedArray,
			final String text, final int id) {
		PickerButton retButton = new PickerButton(context, attrs);

		int btnW = typedArray.getDimensionPixelSize(R.styleable.NumberPicker_plusminus_width,
				PickerButton.DEFAULT_WIDTH);
		int btnH = typedArray.getDimensionPixelSize(R.styleable.NumberPicker_plusminus_height,
				PickerButton.DEFAULT_HEIGHT);
		float textSize = typedArray.getDimension(R.styleable.NumberPicker_textSize,
				PickerEditText.DEFAULT_TEXT_SIZE);

		retButton.setId(id);
		retButton.setButtonHeight(btnH);
		retButton.setButtonWidth(btnW);
		retButton.setText(text);
		retButton.setTextSize(textSize);
		retButton.setOnClickListener(this);

		return retButton;
	}

	protected PickerEditText buildNumberEditText(Context context, AttributeSet attrs,
			TypedArray typedArray, final int id) {
		PickerEditText retEditText = new PickerEditText(context, attrs);

		int textWidth = typedArray.getDimensionPixelSize(
				R.styleable.NumberPicker_textarea_minWidth, PickerEditText.DEFAULT_MIN_WIDTH);
		int textHeight = typedArray.getDimensionPixelSize(R.styleable.NumberPicker_textarea_height,
				PickerEditText.DEFAULT_HEIGHT);
		float textSize = typedArray.getDimension(R.styleable.NumberPicker_textSize,
				PickerEditText.DEFAULT_TEXT_SIZE);

		retEditText.setId(id);
		retEditText.setTextSize(textSize);
		retEditText.setEditTextHeight(textHeight);
		retEditText.setEditTextWidth(textWidth);
		retEditText.setInputType(mNumberType.getInputType());
		retEditText.refreshText();

		return retEditText;
	}

	public float getValue() {
		return mCurrentValue;
	}

	public void setValue(float value) {
		mPreviousValue = mCurrentValue;
		mCurrentValue = value;
		mNumberEdx.refreshText();
	}

	protected void onValueChanged() {
		if (mValueChangeListener != null) {
			mValueChangeListener.onValueChanged(mCurrentValue);
		}
	}

	public void setIncrementValue(float incrementValue) {
		mIncrementValue = incrementValue;
	}

	public void setMaxValue(float maxValue) {
		mMaxValue = maxValue;
	}

	public void setMinValue(float minValue) {
		mMinValue = minValue;
	}

	public final void setOnValueChangeListener(ValueChangeListener listener) {
		mValueChangeListener = listener;
	}

	public static enum NumberType {
		INTEGER(InputType.TYPE_CLASS_NUMBER), FLOAT(InputType.TYPE_CLASS_NUMBER
				| InputType.TYPE_NUMBER_FLAG_DECIMAL);

		private int m_InputType;

		private NumberType(int inputType) {
			m_InputType = inputType;
		}

		public int getInputType() {
			return m_InputType;
		}
	}

	public interface ValueChangeListener {
		public void onValueChanged(float value);

		/**
		 * Check if the value is valid and can be applied <br>
		 * Return true if you want the value to be applied ,false otherwise
		 */
		public boolean isValidValue(float value);
	}

	protected class PickerEditText extends EditText implements TextWatcher {
		private static final int DEFAULT_TEXT_SIZE = 25;
		private static final int DEFAULT_MIN_WIDTH = 50;
		private static final int DEFAULT_HEIGHT = 50;
		private int mHeight = DEFAULT_HEIGHT;

		private boolean mForceText = false;

		private PickerEditText(Context context) {
			this(context, null);
		}

		private PickerEditText(Context context, AttributeSet attrs) {
			super(context, attrs);
			setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
			setSelectAllOnFocus(true);
			setSingleLine();
			addTextChangedListener(this);
			setTextColor(Color.BLACK);
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			if (mForceText) {
				return;
			}
		}

		@Override
		public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
			if (mForceText) {
				return;
			}
			mCurrentValue = Utils.tryReadFloat(text.toString(), mPreviousValue);
		}

		@Override
		public void afterTextChanged(Editable s) {
			if (mForceText) {
				return;
			}
		}

		private void refreshText() {
			mForceText = true;
			setText(String.valueOf((int) mCurrentValue));
			mForceText = false;
		}

		private void setEditTextHeight(int height) {
			mHeight = height;
		}

		private void setEditTextWidth(int width) {
			super.setMinWidth(width);
		}

		private int getEditTextWidth() {
			return LayoutParams.WRAP_CONTENT;
		}

		private int getEditTextHeight() {
			return mHeight;
		}
	}

	protected class PickerButton extends Button {
		private static final int DEFAULT_WIDTH = 50;
		private static final int DEFAULT_HEIGHT = 50;
		private int mHeight = DEFAULT_HEIGHT;
		private int mWidth = DEFAULT_WIDTH;

		private PickerButton(Context context) {
			this(context, null);
		}

		private PickerButton(Context context, AttributeSet attrs) {
			super(context, attrs);
			setGravity(Gravity.CENTER);
		}

		private int getButtonHeight() {
			return mHeight;
		}

		private void setButtonHeight(int height) {
			mHeight = height;
		}

		private int getButtonWidth() {
			return mWidth;
		}

		private void setButtonWidth(int width) {
			mWidth = width;
		}
	}
}
