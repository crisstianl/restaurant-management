package eu.cristianl.ross.android.fragments.docs.renderers;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import eu.cristianl.ross.R;
import eu.cristianl.ross.android.fragments.docs.DocsListAdapter.AdapterItem;
import eu.cristianl.ross.android.fragments.docs.DocsListFragment;

public class ReadyOrdersRenderer extends OrderRenderer {

	ReadyOrdersRenderer(Context context) {
		super(context);
	}

	@Override
	public View getView(AdapterItem item, View convertView, ViewGroup parent, int layoutId) {
		return super.getView(item, convertView, parent, R.layout.fragment_docs_list_adapter);
	}

	@Override
	public void onCreateOrderActions(final View parent, final DocsListFragment.OrdersManager orderManager) {
		Button refreshBtn = (Button) parent.findViewById(R.id.fragment_docs_list_btn1);
		refreshBtn.setVisibility(View.VISIBLE);
		refreshBtn.setText(R.string.dictionary_refresh);
		refreshBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				orderManager.synchronizeOrders();
			}
		});

		Button closeBtn = (Button) parent.findViewById(R.id.fragment_docs_list_btn2);
		closeBtn.setVisibility(View.VISIBLE);
		closeBtn.setText(R.string.fragment_docs_list_close_order);
		closeBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				orderManager.closeOrders();
			}
		});
	}
}
