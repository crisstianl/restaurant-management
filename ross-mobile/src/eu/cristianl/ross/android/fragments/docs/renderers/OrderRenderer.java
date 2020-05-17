package eu.cristianl.ross.android.fragments.docs.renderers;

import java.util.Date;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import eu.cristianl.ross.R;
import eu.cristianl.ross.android.fragments.docs.DocsListAdapter.AdapterItem;
import eu.cristianl.ross.android.fragments.docs.DocsListFragment;
import eu.cristianl.ross.entities.Order;
import eu.cristianl.ross.entities.support.DocStatusType;
import eu.cristianl.ross.utils.DateUtils;
import eu.cristianl.ross.utils.Utils;

public abstract class OrderRenderer {

	protected final Context mContext;

	protected OrderRenderer(Context context) {
		this.mContext = context;
	}

	public View getView(AdapterItem item, View convertView, ViewGroup parent, int layoutId) {
		ViewHolder holder = null;

		if (convertView != null) {
			holder = (ViewHolder) convertView.getTag();
		} else {
			convertView = LayoutInflater.from(mContext).inflate(layoutId, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}

		fillViewHolderData(holder, item);

		return convertView;
	}

	public void onCreateOrderActions(final View parent, final DocsListFragment.OrdersManager orderManager) {
	}

	protected void fillViewHolderData(ViewHolder holder, AdapterItem item) {
		Order order = item.getOrder();

		holder.mDocStateView.setChecked(item.isSelected());
		holder.mContactNameView.setText(order.getContact().getName());
		holder.mDocIdView.setText(order.getId());
		holder.mDocStatusView.setText(order.getDocStatus().getDescription());
		holder.mDocTotalView.setText(Utils.format(order.getTotal()));

		String dateText = isOlderThanADay(order.getCreationDate()) ? DateUtils.getDateString(order.getCreationDate())
				: DateUtils.getTimeLongString(order.getCreationDate());
		holder.mDocCreationDateView.setText(dateText);

		dateText = isOlderThanADay(order.getLastChangeDate()) ? DateUtils.getDateString(order.getLastChangeDate())
				: DateUtils.getTimeLongString(order.getLastChangeDate());
		holder.mDocLastChangeDateView.setText(dateText);
	}

	private boolean isOlderThanADay(Date date) {
		return (DateUtils.now().getTime() - date.getTime()) > (24 * 60 * 60000);
	}

	protected static class ViewHolder {
		private CheckBox mDocStateView;
		private TextView mContactNameView;
		private TextView mDocIdView;
		private TextView mDocStatusView;
		private TextView mDocCreationDateView;
		private TextView mDocLastChangeDateView;
		private TextView mDocTotalView;

		protected ViewHolder(View parent) {
			mDocStateView = (CheckBox) parent.findViewById(R.id.fragment_docs_list_adapter_doc_state);
			mContactNameView = (TextView) parent.findViewById(R.id.fragment_docs_list_adapter_contact_name);
			mDocIdView = (TextView) parent.findViewById(R.id.fragment_docs_list_adapter_doc_id);
			mDocCreationDateView = (TextView) parent.findViewById(R.id.fragment_docs_list_adapter_doc_creation_date);
			mDocLastChangeDateView = (TextView) parent
					.findViewById(R.id.fragment_docs_list_adapter_doc_last_change_date);
			mDocStatusView = (TextView) parent.findViewById(R.id.fragment_docs_list_adapter_doc_status);
			mDocTotalView = (TextView) parent.findViewById(R.id.fragment_docs_list_adapter_doc_total);
		}
	}

	public static class Factory {
		public OrderRenderer getOrderRenderer(Context context, DocStatusType docStatus) {
			switch (docStatus) {
			case PENDING:
				return new DraftOrdersRenderer(context);
			case READY:
				return new ReadyOrdersRenderer(context);
			case SUBMITTED:
				return new SubmittedOrdersRenderer(context);
			case CLOSED:
				return new ClosedOrdersRenderer(context);
			default:
				return null;
			}
		}
	}
}
