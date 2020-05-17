package eu.cristianl.ross.android.adapters.paging;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import eu.cristianl.ross.dal.iterators.ResultsIterator;

public abstract class PagingAdapter<T> extends BaseAdapter implements IPagingAdapter {
	private static final int MAX_IN_MEMORY_LOADED_ITEMS = 2000;
	private static final int DEFAULT_PAGE_ITEMS_COUNT = 50;

	protected int mPageItemsCount;

	protected ResultsIterator<T> mIterator;
	protected List<T> mItems = new ArrayList<T>();

	protected ItemsLoadingTask mLoadingTask;

	protected PageAdapterListener mPageAdapterListener;

	public PagingAdapter() {
		this(null);
	}

	public PagingAdapter(ResultsIterator<T> iterator) {
		this(iterator, DEFAULT_PAGE_ITEMS_COUNT);
	}

	public PagingAdapter(ResultsIterator<T> iterator, int pageItemsCount) {
		mIterator = iterator;
		mPageItemsCount = pageItemsCount;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		boolean lastPage = firstVisibleItem >= totalItemCount - visibleItemCount;
		// If last page then start loading more
		if (lastPage && !isLoading()) {
			loadPage();
		}
	}

	@Override
	public void resetAdapter() {
		stopLoadingTask();
		setIterator(null);
		mItems.clear();
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
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
	public void loadPage() {
		if (getCount() > MAX_IN_MEMORY_LOADED_ITEMS) {
			if (mPageAdapterListener != null) {
				mPageAdapterListener.onMaxItemsReached(this);
			}
			return;
		}

		startLoadingTask();
	}

	public boolean isMaxItemsReached() {
		return (getCount() > MAX_IN_MEMORY_LOADED_ITEMS);
	}

	public void setPageAdapterListener(PageAdapterListener listener) {
		mPageAdapterListener = listener;
	}

	public ResultsIterator<T> getIterator() {
		return mIterator;
	}

	public void setIterator(ResultsIterator<T> iterator) {
		if (mIterator != null) {
			mIterator.close();
		}
		mIterator = iterator;
	}

	public static void registerAdapter(ListView listview, PagingAdapter<?> adapter) {
		if (listview != null && adapter != null) {
			listview.setAdapter(adapter);
			listview.setOnScrollListener(adapter);
			listview.setFastScrollEnabled(true);
		}
	}

	private void stopLoadingTask() {
		if (mLoadingTask != null) {
			mLoadingTask.cancel();
			mLoadingTask = null;
		}
	}

	private void startLoadingTask() {
		if (isLoading()) {
			stopLoadingTask();
		}
		mLoadingTask = new ItemsLoadingTask(mIterator, mPageItemsCount);
		mLoadingTask.execute();
	}

	private boolean isLoading() {
		return (mLoadingTask != null && !mLoadingTask.cancelled());
	}

	private void notifyPageLoaded() {
		if (mPageAdapterListener != null) {
			mPageAdapterListener.onPageLoaded(PagingAdapter.this);
		}
	}

	protected abstract ResultsIterator<T> internalInitIterator();

	private final class ItemsLoadingTask extends AsyncTask<Void, Void, List<T>> {
		private ResultsIterator<T> mTaskIterator;

		private boolean mCanceled;
		protected int mItemsToLoad;

		private ItemsLoadingTask(ResultsIterator<T> taskIterator, int itemsToLoad) {
			mTaskIterator = taskIterator;
			mItemsToLoad = itemsToLoad;
		}

		@Override
		protected List<T> doInBackground(Void... params) {
			int itemsToLoad = computeItemsToLoad();
			List<T> loadedItems = new ArrayList<T>(itemsToLoad);

			if (mTaskIterator == null) {
				mTaskIterator = internalInitIterator();
				setIterator(mTaskIterator);
			}

			for (int i = 0; i < itemsToLoad && mTaskIterator.hasNext(); i++) {
				loadedItems.add(mTaskIterator.next());

				if (cancelled()) {
					break;
				}
			}

			return loadedItems;
		}

		@Override
		protected void onPostExecute(List<T> loadedItems) {
			if (cancelled() || loadedItems.isEmpty()) {
				return;
			}

			mItems.addAll(loadedItems);
			notifyJobCompletion();

			mLoadingTask = null;
		}

		private void notifyJobCompletion() {
			notifyPageLoaded();
			notifyDataSetChanged();
		}

		private void cancel() {
			mLoadingTask.cancel(true);
			mCanceled = true;
		}

		private boolean cancelled() {
			return mCanceled;
		}

		private int computeItemsToLoad() {
			// Minimum between remaining items and page size
			return Math.min(MAX_IN_MEMORY_LOADED_ITEMS - mItems.size(), mItemsToLoad);
		}
	}

	public static interface PageAdapterListener {
		void onPageLoaded(PagingAdapter<?> sender);

		void onMaxItemsReached(PagingAdapter<?> sender);

	}
}
