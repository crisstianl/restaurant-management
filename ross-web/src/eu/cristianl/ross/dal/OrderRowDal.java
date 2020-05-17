package eu.cristianl.ross.dal;

import eu.cristianl.ross.dal.filters.BaseFilter;
import eu.cristianl.ross.dal.filters.OrderRowFilter;
import eu.cristianl.ross.dal.utils.QueryBuilder;
import eu.cristianl.ross.entities.OrderRow;
import eu.cristianl.ross.entities.tables.OrderRowTable;

public class OrderRowDal extends BaseDal<OrderRow> {
	private static OrderRowDal mInstance;

	private OrderRowDal() {
		super(OrderRow.class);
	}

	public static synchronized OrderRowDal I() {
		if (mInstance == null) {
			mInstance = new OrderRowDal();
		}

		return mInstance;
	}

	@Override
	protected void addWhereClauses(BaseFilter baseFilter, QueryBuilder query) {
		if (baseFilter instanceof OrderRowFilter) {
			final OrderRowFilter filter = (OrderRowFilter) baseFilter;

			if (filter.getOrderId() != null) {
				query.addWhereClause(filter, OrderRowTable.ORDER_ID, filter.getOrderId());
			}
		}
	}

	public java.util.List<OrderRow> query(String orderId) {
		OrderRowFilter filters = new OrderRowFilter(true);
		filters.setOrderId(orderId);

		return super.query(new OrderRowFilter[] { filters }, null, false, 0, 100);
	}

}
