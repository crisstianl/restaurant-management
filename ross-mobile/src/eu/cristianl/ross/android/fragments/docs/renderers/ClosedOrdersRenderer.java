package eu.cristianl.ross.android.fragments.docs.renderers;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import eu.cristianl.ross.R;
import eu.cristianl.ross.android.fragments.docs.DocsListAdapter.AdapterItem;
import eu.cristianl.ross.android.fragments.docs.DocsListFragment;

public class ClosedOrdersRenderer extends OrderRenderer {

	ClosedOrdersRenderer(Context context) {
		super(context);
	}

	@Override
	public View getView(AdapterItem item, View convertView, ViewGroup parent, int layoutId) {
		return super.getView(item, convertView, parent, R.layout.fragment_docs_list_adapter);
	}

	@Override
	public void onCreateOrderActions(final View parent, final DocsListFragment.OrdersManager orderManager) {
	}
}
