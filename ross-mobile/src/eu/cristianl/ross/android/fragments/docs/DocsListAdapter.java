package eu.cristianl.ross.android.fragments.docs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import eu.cristianl.ross.android.adapters.ListAdapter;
import eu.cristianl.ross.android.fragments.docs.renderers.OrderRenderer;
import eu.cristianl.ross.dal.OrderDal;
import eu.cristianl.ross.entities.Order;
import eu.cristianl.ross.entities.support.DocStatusType;

public class DocsListAdapter extends ListAdapter<DocsListAdapter.AdapterItem> {
	private static final int SORT_BY_CONTACT = 0;
	private static final int SORT_BY_CREATION_DATE = 1;
	private static final int SORT_BY_STATUS = 2;
	private static final int SORT_BY_TOTAL = 3;

	private String mFilterSearch = null;
	private DocStatusType mDocStatus = null;

	private OrderRenderer mViewRenderer = null;

	DocsListAdapter(DocStatusType docStatus, OrderRenderer viewRenderer) {
		mDocStatus = docStatus;
		mViewRenderer = viewRenderer;
		reset();
	}

	@Override
	public View getView(int position, View row, ViewGroup parent) {
		return mViewRenderer.getView(getItem(position), row, parent, -1);
	}

	@Override
	protected int internalCompare(AdapterItem arg0, AdapterItem arg1) {
		switch (getSortingType()) {
		case SORT_BY_CONTACT:
			return arg0.getOrder().getContact().getName().compareTo(arg1.getOrder().getContact().getName());

		case SORT_BY_CREATION_DATE:
			return arg0.getOrder().getCreationDate().compareTo(arg1.getOrder().getCreationDate());

		case SORT_BY_STATUS:
			return arg0.getOrder().getDocStatus().getDescription()
					.compareTo(arg1.getOrder().getDocStatus().getDescription());

		case SORT_BY_TOTAL:
			return (int) (arg0.getOrder().getTotal() - arg1.getOrder().getTotal());

		default:
			return arg0.getOrder().getId().compareTo(arg1.getOrder().getId());
		}
	}

	@Override
	protected List<AdapterItem> internalGetItems() {
		List<Order> results = null;

		final String docStatusCode = (mDocStatus != null) ? mDocStatus.getCode() : null;
		switch (getSortingType()) {
		case SORT_BY_CREATION_DATE:
			results = OrderDal.getDal().getOrdersSortedByDate(docStatusCode, mFilterSearch, 100);
			break;
		case SORT_BY_STATUS:
			results = OrderDal.getDal().getOrdersSortedByStatus(docStatusCode, mFilterSearch, 100);
			break;
		case SORT_BY_TOTAL:
			results = OrderDal.getDal().getOrdersSortedByTotal(docStatusCode, mFilterSearch, 100);
			break;
		case SORT_BY_CONTACT:
		default:
			results = OrderDal.getDal().getOrdersSortedById(docStatusCode, mFilterSearch, 100);
		}

		List<AdapterItem> retList = new ArrayList<AdapterItem>();
		if (results != null && !results.isEmpty()) {
			for (Order order : results) {
				retList.add(new AdapterItem(order));
			}
		}
		return retList;
	}

	public void setSearchFilter(String filter) {
		mFilterSearch = filter;
	}

	public OrderRenderer getViewRenderer() {
		return mViewRenderer;
	}

	public List<Order> getSelectedOrders() {
		List<Order> retList = new ArrayList<Order>();

		for (AdapterItem item : getItems()) {
			if (item.isSelected()) {
				retList.add(item.getOrder());
			}
		}
		return retList;
	}

	public void removeSelectedRows() {
		Iterator<AdapterItem> iterator = getItems().iterator();
		while (iterator.hasNext()) {
			AdapterItem item = iterator.next();
			if (item.isSelected()) {
				iterator.remove();
			}
		}
	}

	public DocStatusType getDocStatus() {
		return mDocStatus;
	}

	public void removeRow(String orderId) {
		Iterator<AdapterItem> iterator = getItems().iterator();
		while (iterator.hasNext()) {
			AdapterItem item = iterator.next();
			if (item.getOrder().getId().equals(orderId)) {
				iterator.remove();
				break;
			}
		}
	}

	public static class AdapterItem {
		private Order mOrder = null;
		private boolean mSelected = true;

		private AdapterItem(Order order) {
			mOrder = order;
		}

		public Order getOrder() {
			return mOrder;
		}

		public boolean isSelected() {
			return mSelected;
		}

		public void setSelected(boolean selected) {
			mSelected = selected;
		}

		@Override
		public boolean equals(Object o) {
			if (o instanceof AdapterItem) {
				return mOrder.equals(((AdapterItem) o).getOrder());
			}
			return false;
		}
	}
}
