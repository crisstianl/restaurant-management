package eu.cristianl.ross.android.widgets;

import java.util.List;

import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

public class MultipleChoice<T> extends SingleChoice<T> {

	protected MultipleChoice(T[] items, IFeedbackListener<T> listener) {
		super(items, listener);
	}

	public MultipleChoice(List<T> items, IFeedbackListener<T> listener) {
		super(items, listener);
	}

	public static <T> MultipleChoice<T> get(T[] items, IFeedbackListener<T> listener) {
		return new MultipleChoice<T>(items, listener);
	}

	public static <T> MultipleChoice<T> get(List<T> items, IFeedbackListener<T> listener) {
		return new MultipleChoice<T>(items, listener);
	}

	@Override
	protected ListAdapter getAdapter() {
		return new ArrayAdapter<T>(mContext, android.R.layout.simple_list_item_multiple_choice,
				mItems);
	}

}
