package eu.cristianl.ross.entities;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import eu.cristianl.ross.entities.support.PriorityType;
import eu.cristianl.ross.entities.tables.ContactTable;

@DatabaseTable(tableName = ContactTable.TABLE_NAME)
public class Contact {

	@DatabaseField(columnName = ContactTable.ID, id = true, dataType = DataType.INTEGER)
	private int mId;

	@DatabaseField(columnName = ContactTable.NAME, canBeNull = false, width = 50)
	private String mName;

	@DatabaseField(columnName = ContactTable.PRIORITY, dataType = DataType.ENUM_STRING)
	private PriorityType mPriority = PriorityType.MEDIUM;

	@DatabaseField(columnName = ContactTable.CONV_FACTOR, dataType = DataType.INTEGER)
	private int mConvFactor = 1;

	public Contact() {
	}

	public Contact(String name, PriorityType priority, int convFactor) {
		super();
		mName = name;
		mPriority = priority;
		mConvFactor = convFactor;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Contact) {
			return mId == ((Contact) obj).getId();
		}
		return false;
	}

	public int getId() {
		return mId;
	}

	public void setId(int id) {
		mId = id;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		mName = name;
	}

	public PriorityType getPriority() {
		return mPriority;
	}

	public void setPriority(PriorityType priority) {
		mPriority = priority;
	}

	public int getConvFactor() {
		return mConvFactor;
	}

	public void setConvFactor(int convFactor) {
		mConvFactor = convFactor;
	}
}
