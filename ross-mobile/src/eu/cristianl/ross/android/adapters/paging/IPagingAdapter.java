package eu.cristianl.ross.android.adapters.paging;

import android.widget.AbsListView.OnScrollListener;

/**
 * Interface used to implements generic mechanisms over a PagingAdapter
 */
public interface IPagingAdapter extends OnScrollListener {

	void loadPage();

	void resetAdapter();
}
