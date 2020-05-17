package eu.cristianl.ross.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import eu.cristianl.ross.entities.tables.OrderRowTable;

@Entity
@Table(name = OrderRowTable.TABLE_NAME)
public class OrderRow implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String ORDER_FIELD = "mOrder";

	@Id
	@Column(name = OrderRowTable.ID, length = 50)
	private String mId;

	@ManyToOne
	@JoinColumn(name = OrderRowTable.ORDER_ID, nullable = false)
	private Order mOrder;

	@OneToOne
	@JoinColumn(name = OrderRowTable.ITEM_ID, nullable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private Item mItem;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = OrderRowTable.UNIT_ID, nullable = true)
	private Unit mUnit;

	@Column(name = OrderRowTable.TOTAL)
	private float mTotal;

	@Column(name = OrderRowTable.DISCOUNT)
	private float mDiscount;

	@Column(name = OrderRowTable.QUANTITY)
	private float mQuantity;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = OrderRowTable.CURRENCY_ID, nullable = false)
	private BaseCurrency mCurrency;

	public OrderRow() {
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

	public void setId(String id) {
		mId = id;
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

	public BaseCurrency getCurrency() {
		return mCurrency;
	}

	public void setCurrency(BaseCurrency currency) {
		mCurrency = currency;
	}

}
