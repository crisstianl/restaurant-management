package eu.cristianl.ross.entities;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import eu.cristianl.ross.entities.tables.CurrencyTable;
import eu.cristianl.ross.entities.tables.ItemTable;
import eu.cristianl.ross.entities.tables.OrderRowTable;
import eu.cristianl.ross.entities.tables.OrderTable;
import eu.cristianl.ross.entities.tables.UnitTable;

@DatabaseTable(tableName = OrderRowTable.TABLE_NAME)
public class OrderRow {

	@DatabaseField(columnName = OrderRowTable.ID, id = true, width = 50)
	private String mId;

	@DatabaseField(columnName = OrderRowTable.ORDER_ID, foreignColumnName = OrderTable.ID, foreign = true)
	private Order mOrder;

	@DatabaseField(columnName = OrderRowTable.ITEM_ID, foreignColumnName = ItemTable.ID, foreign = true)
	private Item mItem;

	@DatabaseField(columnName = OrderRowTable.UNIT_ID, foreignColumnName = UnitTable.ID, foreign = true)
	private Unit mUnit;

	@DatabaseField(columnName = OrderRowTable.TOTAL, dataType = DataType.FLOAT)
	private float mTotal;

	@DatabaseField(columnName = OrderRowTable.DISCOUNT, dataType = DataType.FLOAT)
	private float mDiscount;

	@DatabaseField(columnName = OrderRowTable.QUANTITY, dataType = DataType.FLOAT)
	private float mQuantity;

	@DatabaseField(columnName = OrderRowTable.CURRENCY_ID, foreignColumnName = CurrencyTable.ID, foreign = true)
	private Currency mCurrency;

	public OrderRow() {
	}

	public OrderRow(String id, Order order, Item item) {
		mId = id;
		mOrder = order;
		mItem = item;
		mUnit = item.getUnit();
		mCurrency = item.getCurrency();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof OrderRow) {
			return mId.equals(((OrderRow) obj).getId());
		}
		return false;
	}

	@Override
	public String toString() {
		return String.format("%1$s", mId);
	}

	public String getId() {
		return mId;
	}

	public Order getOrder() {
		return mOrder;
	}

	public void setOrder(Order order) {
		mOrder = order;
	}

	public Item getItem() {
		return mItem;
	}

	public void setItem(Item item) {
		mItem = item;
	}

	public Unit getUnit() {
		return mUnit;
	}

	public void setUnit(Unit unit) {
		mUnit = unit;
	}

	public float getTotal() {
		return mTotal;
	}

	public void setTotal(float total) {
		mTotal = total;
	}

	public float getDiscount() {
		return mDiscount;
	}

	public void setDiscount(float discount) {
		mDiscount = discount;
	}

	public float getQuantity() {
		return mQuantity;
	}

	public void setQuantity(float quantity) {
		mQuantity = quantity;
	}

	public Currency getCurrency() {
		return mCurrency;
	}

	public void setCurrency(Currency currency) {
		mCurrency = currency;
	}

	public void setId(String id) {
		mId = id;
	}

}
