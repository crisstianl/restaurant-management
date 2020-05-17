package eu.cristianl.ross.dal;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.j256.ormlite.stmt.Where;

import eu.cristianl.ross.dal.database.DatabaseHelper;
import eu.cristianl.ross.dal.filters.BaseFilter;
import eu.cristianl.ross.dal.filters.OrderFilter;
import eu.cristianl.ross.entities.Order;
import eu.cristianl.ross.entities.OrderRow;
import eu.cristianl.ross.entities.support.DocStatusType;
import eu.cristianl.ross.entities.tables.OrderTable;
import eu.cristianl.ross.exceptions.AppException;
import eu.cristianl.ross.logging.AppLogger;
import eu.cristianl.ross.utils.DateUtils;

public class OrderDal extends BaseDal<Order, String> {
	private static OrderDal mInstance;

	private OrderDal() throws SQLException {
		super(Order.class);
	}

	public static synchronized OrderDal getDal() {
		if (mInstance == null) {
			try {
				mInstance = new OrderDal();
			} catch (SQLException e) {
				AppLogger.error(e, e.getMessage());
			}
		}
		return mInstance;
	}

	@Override
	protected int addWhereClauses(Where<Order, String> where, BaseFilter baseFilter) {
		int clauseCount = 0;
		if (baseFilter instanceof OrderFilter) {

			final OrderFilter filter = (OrderFilter) baseFilter;
			if (filter.getContactId() != null) {
				addWhereClause(where, filter, OrderTable.CONTACT_ID, filter.getContactId());
				clauseCount++;
			}

			if (filter.getDocStatusId() != null) {
				addWhereClause(where, filter, OrderTable.DOC_STATUS_ID, filter.getDocStatusId());
				clauseCount++;
			}
		}
		return clauseCount;
	}

	public Order getOrder(String orderId) {
		Order result = null;
		try {
			result = super.get(orderId);
		} catch (SQLException e) {
			AppLogger.error(e, e.getMessage());
		}
		return result;
	}

	public Order getDraftOrder(int contactId) {
		List<Order> results = null;
		try {
			final OrderFilter filter = new OrderFilter(true);
			filter.setDocStatusId(DocStatusType.NEW.getCode());
			filter.setContactId(String.valueOf(contactId));
			results = super.query(new OrderFilter[] { filter }, false, null, false, 0, 1);
		} catch (SQLException e) {
			AppLogger.error(e, e.getMessage());
		}
		return (results != null && !results.isEmpty()) ? results.get(0) : null;
	}

	public Order newOrder(int contactId, String employeeId, String employeeName) throws AppException {
		Order retValue = null;
		try {
			retValue = new Order(generateOrderId(employeeId));
			retValue.setEmployeeId(employeeId);
			retValue.setEmployeeName(employeeName);
			retValue.setContact(ContactDal.getDal().get(contactId));
			retValue.setDocStatus(DocStatusDal.getDal().getStatusNew());
			retValue.setCreationDate(new java.util.Date());

			// save
			super.save(retValue);
		} catch (SQLException e) {
			AppLogger.error(e, e.getMessage());
			throw new AppException("Failed to create order");
		}
		return retValue;
	}

	public void update(Order order) {
		try {
			order.setLastChangeDate(new java.util.Date());
			super.persist(order);
		} catch (SQLException e) {
			AppLogger.error(e, e.getMessage());
		}
	}

	public void markOrderAsPend(Order order) {
		setOrderStatus(order, DocStatusType.PENDING);
	}

	public void markOrderAsSend(Order order) {
		setOrderStatus(order, DocStatusType.SUBMITTED);
	}

	public void markOrderAsReady(Order order) {
		setOrderStatus(order, DocStatusType.READY);
	}

	public void markOrderAsClosed(Order order) {
		setOrderStatus(order, DocStatusType.CLOSED);
	}

	public void addOrderRow(Order order, OrderRow orderRow) {
		try {
			if (order.getOrderRows().add(orderRow)) {
				order.setNumber(order.getNumber() + 1); // increment rows count
				order.setTotal(order.getTotal() + orderRow.getTotal());
				order.setDiscount(order.getDiscount() + orderRow.getDiscount());
				order.setCurrency(orderRow.getCurrency());
				order.setLastChangeDate(new java.util.Date());

				// save
				super.persist(order);
			}
		} catch (SQLException e) {
			AppLogger.error(e, e.getMessage());
		}
	}

	public void deleteOrderRow(Order order, OrderRow orderRow) {
		try {
			if (order.getOrderRows().remove(orderRow)) {
				order.setNumber(order.getNumber() - 1); // decrement rows count
				order.setTotal(order.getTotal() - orderRow.getTotal());
				order.setDiscount(order.getDiscount() - orderRow.getDiscount());
				order.setLastChangeDate(new java.util.Date());

				// save
				super.persist(order);
			}
		} catch (SQLException e) {
			AppLogger.error(e, e.getMessage());
		}
	}

	public void deleteAllOrderRows(Order order) {
		try {
			// Delete database rows
			OrderRowDal.getDal().deleteOrderRows(order);

			// Update order prices
			order.setNumber(0);
			order.setTotal(0F);
			order.setDiscount(0F);
			order.setLastChangeDate(new java.util.Date());

			super.persist(order);
		} catch (SQLException e) {
			AppLogger.error(e, e.getMessage());
		}
	}

	public void deleteOrder(Order order) {
		try {
			// Delete rows
			OrderRowDal.getDal().deleteOrderRows(order);
			// Delete order
			super.delete(order);
		} catch (SQLException e) {
			AppLogger.error(e, e.getMessage());
		}
	}

	public void calculateTotal(Order order) {
		try {
			float total = 0.0F, discount = 0.0F;
			for (OrderRow row : order.getOrderRows()) {
				total += row.getTotal();
				discount += row.getDiscount();
			}
			order.setTotal(total);
			order.setDiscount(discount);

			// save
			super.persist(order);
		} catch (SQLException e) {
			AppLogger.error(e, e.getMessage());
		}
	}

	public List<Order> getOrdersSortedById(String docStatus, String contact, int maxResults) {
		return getOrders(docStatus, contact, OrderTable.ID, false, maxResults);
	}

	public List<Order> getOrdersSortedByDate(String docStatus, String contact, int maxResults) {
		return getOrders(docStatus, contact, OrderTable.CREATION_DATE, true, maxResults);
	}

	public List<Order> getOrdersSortedByStatus(String docStatus, String contact, int maxResults) {
		return getOrders(docStatus, contact, OrderTable.DOC_STATUS_ID, false, maxResults);
	}

	public List<Order> getOrdersSortedByTotal(String docStatus, String contact, int maxResults) {
		return getOrders(docStatus, contact, OrderTable.TOTAL, false, maxResults);
	}

	private List<Order> getOrders(String docStatus, String contact, String orderColumn, boolean desc, int maxResults) {
		List<Order> results = null;
		try {
			final List<OrderFilter> filters = new ArrayList<OrderFilter>(2);
			if (docStatus != null) {
				OrderFilter filter = new OrderFilter(true);
				filter.setDocStatusId(docStatus);
				filters.add(filter);
			}
			if (contact != null) {
				OrderFilter filter = new OrderFilter(false, true);
				filter.setContactId(contact);
				filters.add(filter);
			}
			results = super.query(filters.toArray(new OrderFilter[filters.size()]), true, orderColumn, desc, 0,
					maxResults);
		} catch (SQLException e) {
			AppLogger.error(e, e.getMessage());
		}
		return results;
	}

	public void updateOrderStatus(String id, String status) {
		final StringBuilder stmt = new StringBuilder("UPDATE ").append(OrderTable.TABLE_NAME);
		stmt.append(" SET ").append(OrderTable.DOC_STATUS_ID).append('=').append('\'').append(status).append('\'');
		stmt.append(',').append(OrderTable.LAST_CHANGE_DATE).append('=').append('\'')
				.append(DateUtils.getDatabaseTimestampString(new java.util.Date())).append('\'');
		stmt.append(" WHERE ").append(OrderTable.ID).append('=').append('\'').append(id).append('\'');

		DatabaseHelper.getInstance().insert(stmt.toString());
	}

	private void setOrderStatus(Order order, DocStatusType status) {
		try {
			order.setDocStatus(DocStatusDal.getDal().getStatus(status.getCode()));
			order.setLastChangeDate(new java.util.Date());
			super.persist(order);
		} catch (SQLException e) {
			AppLogger.error(e, e.getMessage());
		}
	}

	private static String generateOrderId(String employeeId) {
		StringBuilder retValue = new StringBuilder(employeeId);
		retValue.append("ORD");
		retValue.append(DateUtils.getString(new Date(), "yyyyMMddHHmmss"));
		return retValue.toString();
	}
}
