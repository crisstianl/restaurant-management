package eu.cristianl.ross.web.jsf.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.BehaviorEvent;

import eu.cristianl.ross.api.OrderNotificationService;
import eu.cristianl.ross.dal.DocStatusDal;
import eu.cristianl.ross.dal.OrderDal;
import eu.cristianl.ross.dal.OrderRowDal;
import eu.cristianl.ross.entities.DocStatus;
import eu.cristianl.ross.entities.Order;
import eu.cristianl.ross.entities.OrderRow;
import eu.cristianl.ross.entities.support.DocStatusType;
import eu.cristianl.ross.utils.DateUtils;
import eu.cristianl.ross.utils.Utils;
import eu.cristianl.ross.web.jsf.navigation.Navigator;
import eu.cristianl.ross.web.jsf.utils.FacesUtils;

@ManagedBean(name = OrderRowsBean.NAME)
@ViewScoped
public class OrderRowsBean implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String NAME = "OrderRowsBean";

	// data
	private Order mOrder = null;
	private List<FormData> mOrderRows = new ArrayList<FormData>();
	private List<DocStatus> mDocStates = new ArrayList<DocStatus>();

	public OrderRowsBean() {
	}

	@PostConstruct
	public void init() {
		queryForDocStates();
	}

	@PreDestroy
	public void destroy() {
		mDocStates.clear();
		mDocStates = null;
		mOrder = null;
		mOrderRows.clear();
		mOrderRows = null;
	}

	public void onOrderSelected(BehaviorEvent event) {
		final Navigator navigator = FacesUtils.getBean(Navigator.NAME);
		navigator.setOrderRowsPage();

		queryForOrderRows();
	}

	public void backToOrders(ActionEvent event) {
		final Navigator navigator = FacesUtils.getBean(Navigator.NAME);
		navigator.setOrdersPage();
		mOrder = null;
		mOrderRows.clear();
	}

	private void queryForDocStates() {
		mDocStates = DocStatusDal.I().queryAll();
	}

	private void queryForOrderRows() {
		mOrderRows.clear();
		if (mOrder != null) {
			List<OrderRow> results = OrderRowDal.I().query(mOrder.getId());
			if (!Utils.isEmptyOrNull(results)) {
				for (OrderRow row : results) {
					mOrderRows.add(new FormData(row));
				}
			}
		}
	}

	public OrdersBean.FormData getOrder() {
		if (mOrder != null) {
			return new OrdersBean.FormData(mOrder);
		}
		return null;
	}

	public void setOrder(OrdersBean.FormData form) {
		if (form != null) {
			mOrder = form.getOrder();
		} else {
			mOrder = null;
		}
	}

	public String getOrderId() {
		return (mOrder != null) ? mOrder.getId() : "N/A";
	}

	public String getDocStatus() {
		return (mOrder != null) ? mOrder.getDocStatus().getId() : "N/A";
	}

	public String getCustomer() {
		return (mOrder != null) ? mOrder.getContact().getName() : "N/A";
	}

	public String getCreationDate() {
		return (mOrder != null) ? DateUtils.getDateString(mOrder.getCreationDate()) : "N/A";
	}

	public String getTotal() {
		return (mOrder != null) ? String.format("%1$.2f %2$s", mOrder.getTotal(), mOrder.getCurrency().getName())
				: "N/A";
	}

	public String getDiscount() {
		return (mOrder != null) ? String.format("%1$.2f %2$s", mOrder.getDiscount(), mOrder.getCurrency().getName())
				: "N/A";
	}

	public void setDocStatus(String newStatusId) {
		if (mOrder != null && newStatusId != null && !newStatusId.isEmpty()) {
			if (!newStatusId.equals(mOrder.getDocStatus().getId())) {
				final DocStatus newStatus = getDocStatus(newStatusId);
				if (newStatus != null) {
					// Update database
					mOrder.setDocStatus(newStatus);
					OrderDal.I().updateOrder(mOrder);

					// Notify mobile clients
					OrderNotificationService service = new OrderNotificationService(
							mOrder.getEmployee().getId().toString(), mOrder.getId(), mOrder.getDocStatus().getId());
					service.post();
				}
			}
		}
	}

	public List<FormData> getOrderRows() {
		return mOrderRows;
	}

	public List<DocStatus> getDocStates() {
		List<DocStatus> retList = new ArrayList<DocStatus>();
		if (mOrder != null) {
			// append only statuses that are higher then the current order status
			boolean append = false;
			for (DocStatusType type : DocStatusType.values()) {
				if (append) {
					retList.add(getDocStatus(type.getCode()));
				} else if (mOrder.getDocStatus().getId().equals(type.getCode())) {
					append = true;
					retList.add(mOrder.getDocStatus());
				}
			}
		}
		return retList;
	}

	private DocStatus getDocStatus(String code) {
		if (code != null && !code.isEmpty()) {
			for (DocStatus status : mDocStates) {
				if (code.equals(status.getId())) {
					return status;
				}
			}
		}
		return null;
	}

	public static class FormData implements Serializable {
		private static final long serialVersionUID = 1L;

		private OrderRow mOrderRow;

		private FormData(OrderRow orderRow) {
			mOrderRow = orderRow;
		}

		public String getOrderRowId() {
			return mOrderRow.getId();
		}

		public String getItemId() {
			return mOrderRow.getItem().getId().toString();
		}

		public String getItemTitle() {
			return mOrderRow.getItem().getTitle();
		}

		public String getTotal() {
			return String.format("%1$.2f %2$s", mOrderRow.getTotal(), mOrderRow.getCurrency().getName());
		}

		public String getDiscount() {
			return String.format("%1$.2f %2$s", mOrderRow.getDiscount(), mOrderRow.getCurrency().getName());
		}

		public String getQuantity() {
			String unit = "";
			if (mOrderRow.getUnit() != null) {
				unit = mOrderRow.getUnit().getDescription();
			}
			return String.format("%1$.2f %2$s", mOrderRow.getQuantity(), unit);
		}
	}

}
