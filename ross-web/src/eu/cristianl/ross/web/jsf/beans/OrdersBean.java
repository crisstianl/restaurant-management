package eu.cristianl.ross.web.jsf.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.BehaviorEvent;

import eu.cristianl.ross.dal.DocStatusDal;
import eu.cristianl.ross.dal.OrderDal;
import eu.cristianl.ross.dal.WorkerDal;
import eu.cristianl.ross.entities.DocStatus;
import eu.cristianl.ross.entities.Employee;
import eu.cristianl.ross.entities.Order;
import eu.cristianl.ross.entities.Worker;
import eu.cristianl.ross.utils.DateUtils;
import eu.cristianl.ross.utils.Utils;

@ManagedBean(name = OrdersBean.NAME)
@ViewScoped
public class OrdersBean implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String NAME = "OrdersBean";

	// data
	private List<FormData> mOrders = new ArrayList<FormData>();

	// Filters
	private String mDocStatusId = null;
	private Date mOrderDate = null;
	private Employee mEmployee = null;
	private List<Employee> mEmployees = new ArrayList<Employee>();
	private List<DocStatus> mDocStates = new ArrayList<DocStatus>();

	public OrdersBean() {
	}

	@PostConstruct
	public void init() {
		computeStartingOrderDate();
		queryForOrders();
		queryForDocStates();
		queryForEmployees();
	}

	@PreDestroy
	public void destroy() {
		mOrders.clear();
		mOrders = null;
		mEmployees.clear();
		mEmployees = null;
		mDocStates.clear();
		mDocStates = null;
		mEmployee = null;
	}

	public void onStartDateChanged(BehaviorEvent event) {
		queryForOrders();
	}

	public void onDocStatusChanged(BehaviorEvent event) {
		queryForOrders();
	}

	public void onEmployeeChanged(BehaviorEvent event) {
		queryForOrders();
	}

	public void resetFilters(ActionEvent event) {
		mDocStatusId = null;
		mEmployee = null;
		computeStartingOrderDate();
		queryForOrders();
	}

	private void queryForOrders() {
		mOrders.clear();

		Integer employeeId = (mEmployee != null) ? mEmployee.getId() : null;
		final List<Order> results = OrderDal.I().query(employeeId, mDocStatusId, mOrderDate);
		if (!Utils.isEmptyOrNull(results)) {
			for (Order order : results) {
				mOrders.add(new FormData(order));
			}
		}
	}

	private void queryForEmployees() {
		final List<Worker> workers = WorkerDal.I().queryAll();
		if (workers != null && !workers.isEmpty()) {
			mEmployees.addAll(workers);
		}
	}

	private void queryForDocStates() {
		mDocStates = DocStatusDal.I().queryAll();
	}

	private void computeStartingOrderDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		mOrderDate = cal.getTime();
	}

	public Date getOrderDate() {
		return mOrderDate;
	}

	public void setOrderDate(Date orderDate) {
		mOrderDate = orderDate;
	}

	public Employee getEmployee() {
		return mEmployee;
	}

	public void setEmployee(Employee employee) {
		mEmployee = employee;
	}

	public String getDocStatusId() {
		return mDocStatusId;
	}

	public void setDocStatusId(String docStatusId) {
		mDocStatusId = docStatusId;
	}

	public List<FormData> getOrders() {
		return mOrders;
	}

	public List<Employee> getEmployees() {
		return mEmployees;
	}

	public List<DocStatus> getDocStates() {
		return mDocStates;
	}

	public static class FormData implements Serializable {
		private static final long serialVersionUID = 1L;

		private Order mOrder;

		public FormData(Order order) {
			mOrder = order;
		}

		public String getOrderId() {
			return mOrder.getId();
		}

		public String getEmployeeName() {
			return mOrder.getEmployee().getName();
		}

		public String getContactName() {
			return mOrder.getContact().getName();
		}

		public String getCreationDate() {
			if (isOlderThanADay(mOrder.getCreationDate())) {
				return DateUtils.getDateString(mOrder.getCreationDate());
			} else {
				return DateUtils.getTimeString(mOrder.getCreationDate());
			}
		}

		public String getTotal() {
			return String.format("%1$.2f %2$s", mOrder.getTotal(), mOrder.getCurrency().getName());
		}

		public String getDiscount() {
			return String.format("%1$.2f %2$s", mOrder.getDiscount(), mOrder.getCurrency().getName());
		}

		public String getDocStatus() {
			return mOrder.getDocStatus().getDescription();
		}

		public Order getOrder() {
			return mOrder;
		}

		private boolean isOlderThanADay(Date date) {
			return (DateUtils.now().getTime() - date.getTime()) > (24 * 60 * 60000);
		}
	}

}
