package eu.cristianl.ross.android.adapters;

import eu.cristianl.ross.android.adapters.paging.PagingAdapter;
import eu.cristianl.ross.dal.iterators.ResultsIterator;

public abstract class LazyListAdapter<T> extends PagingAdapter<T> {

	public LazyListAdapter() {
	}

	public LazyListAdapter(ResultsIterator<T> iterator, int pageItemsCount) {
		super(iterator, pageItemsCount);
	}

	public LazyListAdapter(ResultsIterator<T> iterator) {
		super(iterator);
	}
}
