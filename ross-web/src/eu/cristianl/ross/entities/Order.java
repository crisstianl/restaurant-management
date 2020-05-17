package eu.cristianl.ross.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import eu.cristianl.ross.entities.tables.OrderTable;

@Entity
@Table(name = OrderTable.TABLE_NAME)
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = OrderTable.ID, length = 50)
	private String mId;

	@OneToOne
	@JoinColumn(name = OrderTable.CONTACT_ID, nullable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private Contact mContact;

	@OneToOne
	@JoinColumns({ @JoinColumn(name = OrderTable.EMPLOYEE_ID, nullable = false),
			@JoinColumn(name = OrderTable.EMPLOYEE_NAME, nullable = false) })
	private Employee mEmployee;

	@Column(name = OrderTable.TOTAL)
	private float mTotal;

	@Column(name = OrderTable.DISCOUNT)
	private float mDiscount;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = OrderTable.CURRENCY_ID, nullable = false)
	private BaseCurrency mCurrency;

	@Column(name = OrderTable.CREATION_DATE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date mCreationDate;

	@Column(name = OrderTable.LAST_CHANGE_DATE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date nLastChangeDate;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = OrderTable.DOC_STATUS_ID, nullable = false)
	private DocStatus mDocStatus;

	/** Count of child rows */
	@Column(name = OrderTable.NUMBER)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int mNumber;

	@OneToMany(mappedBy = OrderRow.ORDER_FIELD, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<OrderRow> mOrderRows = new ArrayList<OrderRow>();

	public Order() {
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

	public Contact getContact() {
		return mContact;
	}

	public void setContact(Contact contact) {
		mContact = contact;
	}

	public Employee getEmployee() {
		return mEmployee;
	}

	public void setEmployee(Employee employee) {
		mEmployee = employee;
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

	public Date getnLastChangeDate() {
		return nLastChangeDate;
	}

	public void setnLastChangeDate(Date nLastChangeDate) {
		this.nLastChangeDate = nLastChangeDate;
	}

	public DocStatus getDocStatus() {
		return mDocStatus;
	}

	public void setDocStatus(DocStatus docStatus) {
		mDocStatus = docStatus;
	}

	public int getNumber() {
		return mNumber;
	}

	public void setNumber(int number) {
		mNumber = number;
	}

	public BaseCurrency getCurrency() {
		return mCurrency;
	}

	public void setCurrency(BaseCurrency currency) {
		mCurrency = currency;
	}

}
