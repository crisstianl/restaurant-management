package eu.cristianl.ross.android.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import eu.cristianl.ross.R;

public class ListHeader extends LinearLayout implements View.OnClickListener {
	private CharSequence[] mHeaderTitles = null;

	private int mBackgroundColor;
	private int mSelectionColor;
	private int mTitleStyleId;
	private int[] mTitleWeight;

	private Drawable mIconSortAsc;
	private Drawable mIconSortDesc;

	private boolean mAscendingOrder = true;
	private int mSortingColumnId = -1;
	private SortTypeChangeListener mSortChangeListener;

	public ListHeader(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}

	public ListHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	@Override
	public void onClick(View v) {
		// If same column then change the order, else start new asc order
		mAscendingOrder = (mSortingColumnId == v.getId()) ? !mAscendingOrder : true;
		mSortingColumnId = v.getId();

		// refresh all icons
		refreshSortingIcons((ViewGroup) v.getParent());

		if (mSortChangeListener != null) {
			mSortChangeListener.onSortTypeChanged(mSortingColumnId, mAscendingOrder);
		}
	}

	protected void setupLayout(Context context, AttributeSet attrs) {
		for (int i = 0; i < mHeaderTitles.length; i++) {
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,
					LayoutParams.MATCH_PARENT, mTitleWeight[i]);

			View cellView = createCellView(context, attrs, i);
			addView(cellView, params);
		}
	}

	public void setHeaderTitles(String[] headerTitles) {
		mHeaderTitles = headerTitles;
		invalidate();
	}

	public void setSortTypeChangeListener(SortTypeChangeListener sortChangeListener) {
		mSortChangeListener = sortChangeListener;
	}

	private void init(Context context, AttributeSet attrs) {
		initDataFromTypedArray(context.obtainStyledAttributes(attrs, R.styleable.ListHeader));
		setupLayout(context, attrs);
	}

	private void initDataFromTypedArray(TypedArray typedArray) {
		mHeaderTitles = typedArray.getTextArray(R.styleable.ListHeader_titles);

		mBackgroundColor = typedArray.getColor(R.styleable.ListHeader_backgroundColor, Color.WHITE);

		mSelectionColor = typedArray.getColor(R.styleable.ListHeader_selectionColor, Color.WHITE);

		mTitleStyleId = typedArray.getResourceId(R.styleable.ListHeader_titleStyle,
				android.R.style.TextAppearance_Medium);

		try {
			mTitleWeight = typedArray.getResources().getIntArray(
					typedArray.getResourceId(R.styleable.ListHeader_titleWeight, -1));
		} catch (Exception e) {
			mTitleWeight = new int[mHeaderTitles.length];
			for (int i = 0; i < mTitleWeight.length; i++) {
				mTitleWeight[i] = 100 / mHeaderTitles.length;
			}
		}

		mIconSortAsc = typedArray.getDrawable(R.styleable.ListHeader_drawableAsc);
		mIconSortDesc = typedArray.getDrawable(R.styleable.ListHeader_drawableDesc);
	}

	private View createCellView(Context context, AttributeSet attrs, int id) {
		Button button = new Button(context, attrs);
		button.setTextAppearance(getContext(), mTitleStyleId);
		button.setPadding(0, 0, 0, 0);
		button.setId(id);
		button.setOnClickListener(this);
		button.setText(mHeaderTitles[id]);
		button.setCompoundDrawablesWithIntrinsicBounds(mIconSortAsc, null, null, null);
		button.setBackgroundDrawable(getDrawableStates());

		return button;
	}

	private void refreshSortingIcons(ViewGroup parent) {
		for (int i = 0; i < parent.getChildCount(); i++) {
			Button button = (Button) parent.getChildAt(i);
			if (button.getId() == mSortingColumnId) {
				button.setCompoundDrawablesWithIntrinsicBounds(mAscendingOrder ? mIconSortAsc
						: mIconSortDesc, null, null, null);
			} else {
				button.setCompoundDrawablesWithIntrinsicBounds(mIconSortAsc, null, null, null);
			}
		}
	}

	private StateListDrawable getDrawableStates() {
		StateListDrawable retValue = new StateListDrawable();

		ShapeDrawable normal = new ShapeDrawable(new RectShape());
		normal.getPaint().setColor(mBackgroundColor);

		ShapeDrawable pressed = new ShapeDrawable(new RectShape());
		pressed.getPaint().setColor(mSelectionColor);

		retValue.addState(new int[] { android.R.attr.state_pressed }, pressed);
		retValue.addState(new int[] { android.R.attr.state_enabled }, normal);

		return retValue;
	}

	public static interface SortTypeChangeListener {
		public void onSortTypeChanged(int columnId, boolean asc);
	}
}
