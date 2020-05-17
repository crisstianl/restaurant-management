package eu.cristianl.ross.entities.tables;

public class OrderTable {
	public static final String TABLE_NAME = "DOC_ORDER";
	public static final String ID = "Id";
	public static final String CONTACT_ID = "ContactId";
	public static final String EMPLOYEE_ID = "EmployeeId";
	public static final String EMPLOYEE_NAME = "EmployeeName";
	public static final String TOTAL = "Total";
	public static final String DISCOUNT = "Discount";
	public static final String CURRENCY_ID = "CurrencyId";
	public static final String CREATION_DATE = "CreationDate";
	public static final String LAST_CHANGE_DATE = "LastChangeDate";
	public static final String DOC_STATUS_ID = "DocStatusId";
	public static final String NUMBER = "Number";

	public static final String[] COLUMNS = { ID, CONTACT_ID, EMPLOYEE_ID, EMPLOYEE_NAME, TOTAL, DISCOUNT, CURRENCY_ID,
			CREATION_DATE, LAST_CHANGE_DATE, DOC_STATUS_ID, NUMBER };
}
