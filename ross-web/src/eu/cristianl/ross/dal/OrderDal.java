package eu.cristianl.ross.dal;

import java.util.List;

import eu.cristianl.ross.dal.filters.BaseFilter;
import eu.cristianl.ross.dal.filters.OrderFilter;
import eu.cristianl.ross.dal.utils.QueryBuilder;
import eu.cristianl.ross.dal.utils.SQLDate;
import eu.cristianl.ross.dal.utils.SQLDatetime;
import eu.cristianl.ross.entities.Order;
import eu.cristianl.ross.entities.tables.OrderTable;

public class OrderDal extends BaseDal<Order> {
	private static OrderDal mInstance;

	private OrderDal() {
		super(Order.class);
	}

	public static synchronized OrderDal I() {
		if (mInstance == null) {
			mInstance = new OrderDal();
		}

		return mInstance;
	}

	@Override
	protected void addWhereClauses(BaseFilter baseFilter, QueryBuilder query) {
		if (baseFilter instanceof OrderFilter) {
			OrderFilter filter = (OrderFilter) baseFilter;

			if (filter.getOrderId() != null) {
				query.addWhereClause(filter, OrderTable.ID, filter.getOrderId());
			}

			if (filter.getDocStatusId() != null) {
				query.addWhereClause(filter, OrderTable.DOC_STATUS_ID, filter.getDocStatusId());
			}

			if (filter.getEmployeeId() != null) {
				query.addWhereClause(filter, OrderTable.EMPLOYEE_ID, filter.getEmployeeId());
			}

			if (filter.getOrderDate() != null) {
				query.addWhereClause(filter, OrderTable.CREATION_DATE, filter.getOrderDate().toString(), null);
			}
		}
	}

	public List<Order> query(Integer employeeId, String docStatus, java.util.Date startDate) {
		OrderFilter filters = new OrderFilter(true);
		if (employeeId != null) {
			filters.setEmployeeId(employeeId.toString());
		}
		if (docStatus != null && !docStatus.isEmpty()) {
			filters.setDocStatusId(docStatus);
		}
		if (startDate != null) {
			filters.setOrderDate(new SQLDate(startDate.getTime()));
		}

		return super.query(new OrderFilter[] { filters }, null, false, 0, 100);
	}

	public List<Order> query(String[] orderIds) {
		OrderFilter filters = new OrderFilter(true);
		filters.setOrderId(orderIds);
		return super.query(new OrderFilter[] { filters }, null, false, 0, orderIds.length);
	}

	public void updateOrder(Order order) {
		super.update(order);
	}

	public int createOrders(String[] sqlInserts) {
		return super.insert(sqlInserts);
	}

	public int updateOrderStatus(String id, String status) {
		final StringBuilder stmt = new StringBuilder("UPDATE ").append(OrderTable.TABLE_NAME);
		stmt.append(" SET ").append(OrderTable.DOC_STATUS_ID).append('=').append('\'').append(status).append('\'');
		stmt.append(',').append(OrderTable.LAST_CHANGE_DATE).append('=').append('\'').append(new SQLDatetime())
				.append('\'');
		stmt.append(" WHERE ").append(OrderTable.ID).append('=').append('\'').append(id).append('\'');

		return super.insert(stmt.toString());
	}
}
