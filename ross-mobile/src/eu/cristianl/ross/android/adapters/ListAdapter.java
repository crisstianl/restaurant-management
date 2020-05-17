package eu.cristianl.ross.android.adapters;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.os.AsyncTask;
import android.widget.BaseAdapter;

public abstract class ListAdapter<T> extends BaseAdapter implements Comparator<T> {
	private static final int MAX_IN_MEMORY_LOADED_ITEMS = 2000;

	private int m_MaxInMemoryItemsCount = MAX_IN_MEMORY_LOADED_ITEMS;

	private List<T> mItems;
	private int mSortingType;
	private boolean mSortingAsc = true;

	public ListAdapter(List<T> items, int maxInMemoryItemsCount) {
		mItems = items;
		m_MaxInMemoryItemsCount = maxInMemoryItemsCount;
	}

	public ListAdapter(List<T> items) {
		this(items, MAX_IN_MEMORY_LOADED_ITEMS);
	}

	public ListAdapter() {
		this(null);
	}

	@Override
	public int getCount() {
		if (mItems == null) {
			return 0;
		}
		return mItems.size();
	}

	@Override
	public T getItem(int position) {
		if (position >= 0 && position < getCount()) {
			return mItems.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public final int compare(T arg0, T arg1) {
		int retValue = internalCompare(arg0, arg1);
		if (!mSortingAsc) {
			retValue *= -1;
		}
		return retValue;
	}

	/** Force an AsyncTask to query again for data, {@link #internalGetItems()} */
	public void reset() {
		new ItemsLoadingTask().execute();
	}

	protected abstract int internalCompare(T arg0, T arg1);

	protected abstract List<T> internalGetItems();

	public int getMaxInMemoryItemsCount() {
		return m_MaxInMemoryItemsCount;
	}

	public void setMaxInMemoryItemsCount(int maxInMemoryItemsCount) {
		m_MaxInMemoryItemsCount = maxInMemoryItemsCount;
	}

	public List<T> getItems() {
		return mItems;
	}

	public void setItems(List<T> items) {
		mItems = items;
	}

	public int getSortingType() {
		return mSortingType;
	}

	public boolean isSortingAsc() {
		return mSortingAsc;
	}

	public final void sort(int sortingType, boolean asc) {
		mSortingType = sortingType;
		mSortingAsc = asc;
		Collections.sort(mItems, this);
		notifyDataSetChanged();
	}

	private final class ItemsLoadingTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			setItems(internalGetItems());
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			notifyDataSetChanged();
		}
	}
}
