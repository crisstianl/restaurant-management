package eu.cristianl.ross.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import eu.cristianl.ross.entities.tables.ContactTable;
import eu.cristianl.ross.entities.tables.CurrencyTable;
import eu.cristianl.ross.entities.tables.DocStatusTable;
import eu.cristianl.ross.entities.tables.OrderTable;

@DatabaseTable(tableName = OrderTable.TABLE_NAME)
public class Order {

	@DatabaseField(columnName = OrderTable.ID, id = true, width = 50)
	private String mId;

	@DatabaseField(columnName = OrderTable.CONTACT_ID, foreignColumnName = ContactTable.ID, foreign = true)
	private Contact mContact;

	@DatabaseField(columnName = OrderTable.EMPLOYEE_ID, canBeNull = false)
	private String mEmployeeId;

	@DatabaseField(columnName = OrderTable.EMPLOYEE_NAME, canBeNull = false, width = 50)
	private String mEmployeeName;

	@DatabaseField(columnName = OrderTable.TOTAL, dataType = DataType.FLOAT)
	private float mTotal;

	@DatabaseField(columnName = OrderTable.DISCOUNT, dataType = DataType.FLOAT)
	private float mDiscount;

	@DatabaseField(columnName = OrderTable.CURRENCY, foreignColumnName = CurrencyTable.ID, foreign = true)
	private Currency mCurrency;

	@DatabaseField(columnName = OrderTable.CREATION_DATE, dataType = DataType.DATE_STRING, format = "yyyy-MM-dd HH:mm:ss")
	private Date mCreationDate = null;

	@DatabaseField(columnName = OrderTable.LAST_CHANGE_DATE, dataType = DataType.DATE_STRING, format = "yyyy-MM-dd HH:mm:ss")
	private Date mLastChangeDate = null;

	@DatabaseField(columnName = OrderTable.DOC_STATUS_ID, foreignColumnName = DocStatusTable.ID, foreign = true)
	private DocStatus mDocStatus;

	/** Count of child rows */
	@DatabaseField(columnName = OrderTable.NUMBER)
	private int mNumber;

	private final List<OrderRow> mOrderRows = new ArrayList<OrderRow>();

	public Order() {
	}

	public Order(String id) {
		mId = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Order) {
			return mId.equals(((Order) obj).getId());
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

	public String getEmployeeId() {
		return mEmployeeId;
	}

	public void setEmployeeId(String employeeId) {
		mEmployeeId = employeeId;
	}

	public String getEmployeeName() {
		return mEmployeeName;
	}

	public void setEmployeeName(String employeeName) {
		mEmployeeName = employeeName;
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

	public Date getCreationDate() {
		return mCreationDate;
	}

	public void setCreationDate(Date creationDate) {
		mCreationDate = creationDate;
	}

	public Date getLastChangeDate() {
		return mLastChangeDate;
	}

	public void setLastChangeDate(Date lastChangeDate) {
		mLastChangeDate = lastChangeDate;
	}

	public int getNumber() {
		return mNumber;
	}

	public void setNumber(int number) {
		mNumber = number;
	}

	public Contact getContact() {
		return mContact;
	}

	public void setContact(Contact contact) {
		mContact = contact;
	}

	public DocStatus getDocStatus() {
		return mDocStatus;
	}

	public void setDocStatus(DocStatus docStatus) {
		mDocStatus = docStatus;
	}

	public List<OrderRow> getOrderRows() {
		return mOrderRows;
	}

	public Currency getCurrency() {
		return mCurrency;
	}

	public void setCurrency(Currency currency) {
		this.mCurrency = currency;
	}

}
