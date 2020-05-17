package eu.cristianl.ross.android.fragments.order.components.sync;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import eu.cristianl.ross.R;
import eu.cristianl.ross.android.adapters.ListAdapter;
import eu.cristianl.ross.entities.OrderRow;

public class SyncListAdapter extends ListAdapter<OrderRow> {

	private final Context mContext;

	public SyncListAdapter(Context context, List<OrderRow> items) {
		super(items);
		this.mContext = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		if (convertView != null) {
			holder = (ViewHolder) convertView.getTag();
		} else {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_order_sync_component_adapter, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		fillViewFolderWithData(holder, getItem(position));
		return convertView;
	}

	@Override
	protected int internalCompare(OrderRow arg0, OrderRow arg1) {
		return 0;
	}

	@Override
	protected List<OrderRow> internalGetItems() {
		return null;
	}

	private void fillViewFolderWithData(ViewHolder holder, OrderRow orderRow) {
		holder.mItemIdView.setText(String.valueOf(orderRow.getItem().getId()));

		holder.mItemTitleView.setText(orderRow.getItem().getTitle());

		holder.mOrderRowQuantityView.setText(Html.fromHtml(String.format("<b>%1$.2f</b> %2$s", orderRow.getQuantity(),
				(orderRow.getUnit() != null) ? orderRow.getUnit().getDescription() : "um")));

		holder.mOrderRowTotalView.setText(Html
				.fromHtml(String.format("<b>%1$.2f</b> %2$s", orderRow.getTotal(), orderRow.getCurrency().getName())));

		holder.mOrderRowDiscountView.setText(Html.fromHtml(String.format("%1$.2f &#37", orderRow.getDiscount())));
	}

	private static class ViewHolder {
		private TextView mItemTitleView;
		private TextView mItemIdView;
		private TextView mOrderRowQuantityView;
		private TextView mOrderRowTotalView;
		private TextView mOrderRowDiscountView;

		private ViewHolder(View parent) {
			mItemTitleView = (TextView) parent.findViewById(R.id.fragment_order_sync_component_adapter_item_title);
			mItemIdView = (TextView) parent.findViewById(R.id.fragment_order_sync_component_adapter_item_id);
			mOrderRowTotalView = (TextView) parent
					.findViewById(R.id.fragment_order_sync_component_adapter_order_row_total);
			mOrderRowQuantityView = (TextView) parent
					.findViewById(R.id.fragment_order_sync_component_adapter_order_row_quantity);
			mOrderRowDiscountView = (TextView) parent
					.findViewById(R.id.fragment_order_sync_component_adapter_order_row_discount);
		}
	}

}
