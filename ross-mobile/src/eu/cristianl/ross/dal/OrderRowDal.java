package eu.cristianl.ross.dal;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.stmt.Where;

import eu.cristianl.ross.dal.filters.BaseFilter;
import eu.cristianl.ross.dal.filters.OrderRowFilter;
import eu.cristianl.ross.entities.Item;
import eu.cristianl.ross.entities.Order;
import eu.cristianl.ross.entities.OrderRow;
import eu.cristianl.ross.entities.tables.OrderRowTable;
import eu.cristianl.ross.exceptions.AppException;
import eu.cristianl.ross.logging.AppLogger;
import eu.cristianl.ross.utils.Utils;

public class OrderRowDal extends BaseDal<OrderRow, String> {
	private static OrderRowDal mInstance;

	private OrderRowDal() throws SQLException {
		super(OrderRow.class);
	}

	public static synchronized OrderRowDal getDal() {
		if (mInstance == null) {
			try {
				mInstance = new OrderRowDal();
			} catch (SQLException e) {
				AppLogger.error(e, e.getMessage());
			}
		}
		return mInstance;
	}

	@Override
	protected int addWhereClauses(Where<OrderRow, String> where, BaseFilter baseFilter) {
		int clauseCount = 0;
		if (baseFilter instanceof OrderRowFilter) {
			final OrderRowFilter filter = (OrderRowFilter) baseFilter;

			if (filter.getOrderId() != null) {
				addWhereClause(where, filter, OrderRowTable.ORDER_ID, filter.getOrderId());
				clauseCount++;
			}
		}
		return clauseCount;
	}

	public OrderRow newOrderRow(Order order, Item item) throws AppException {
		OrderRow retValue = null;
		try {
			retValue = new OrderRow(generateId(order, item), order, item);
			retValue.setQuantity(1F);
			retValue.setTotal(computeTotal(item.getPrice(), item.getDiscount(), 1F));
			retValue.setDiscount(computeDiscount(item.getPrice(), item.getDiscount(), 1F));
			// save
			super.save(retValue);
		} catch (SQLException e) {
			throw new AppException(e, e.getMessage());
		}

		return retValue;
	}

	public List<OrderRow> query(String orderId) {
		List<OrderRow> results = null;
		try {
			OrderRowFilter filter = new OrderRowFilter(true);
			filter.setOrderId(orderId);
			results = super.query(new OrderRowFilter[] { filter }, false, OrderRowTable.ITEM_ID, false, 0, 100);
		} catch (SQLException e) {
			AppLogger.error(e, e.getMessage());
		}
		return results;
	}

	public boolean deleteOrderRow(OrderRow orderRow) {
		try {
			return super.delete(orderRow);
		} catch (SQLException e) {
			AppLogger.error(e, e.getMessage());
		}
		return false;
	}

	public void deleteOrderRows(Order order) {
		try {
			OrderRowFilter filter = new OrderRowFilter(true);
			filter.setOrderId(order.getId());
			super.delete(new OrderRowFilter[] { filter }, false);
		} catch (SQLException e) {
			AppLogger.error(e, e.getMessage());
		}
	}

	public void calculateTotal(OrderRow orderRow, float newQuantity) {
		try {
			orderRow.setQuantity(newQuantity);
			orderRow.setTotal(
					computeTotal(orderRow.getItem().getPrice(), orderRow.getItem().getDiscount(), newQuantity));
			orderRow.setDiscount(
					computeDiscount(orderRow.getItem().getPrice(), orderRow.getItem().getDiscount(), newQuantity));

			// save
			super.persist(orderRow);
		} catch (SQLException e) {
			AppLogger.error(e, e.getMessage());
		}
	}

	private static String generateId(Order order, Item item) {
		return order.getId() + "I" + item.getId();
	}

	/**
	 * discounted_price = price * discount / 100 </br>
	 * total = discounted_price * quantity
	 */
	private static float computeTotal(float price, float discount, float quantity) {
		if (discount > 0.0F) { // apply discount
			price = price - (price * discount / 100);
		}
		return Utils.round(price * quantity, 3);
	}

	/**
	 * discount = price * discount / 100 </br>
	 * total = discount * quantity
	 */
	private static float computeDiscount(float price, float discount, float quantity) {
		if (discount > 0.0F) {
			discount = price * discount / 100;
			return Utils.round(discount * quantity, 3);
		} else {
			return 0.0F;
		}
	}
}
