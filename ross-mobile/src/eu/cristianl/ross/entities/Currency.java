package eu.cristianl.ross.entities;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import eu.cristianl.ross.entities.tables.CurrencyTable;

@DatabaseTable(tableName = CurrencyTable.TABLE_NAME)
public class Currency {

	@DatabaseField(columnName = CurrencyTable.ID, id = true, width = 3)
	private String mId;

	@DatabaseField(columnName = CurrencyTable.NAME, canBeNull = false, width = 20)
	private String mName;

	@DatabaseField(columnName = CurrencyTable.CURRENCY_TYPE, canBeNull = false, width = 20)
	private String mCurrencyType;

	@DatabaseField(columnName = CurrencyTable.CONVERSION_FACTOR, dataType = DataType.FLOAT)
	private float mConvFactor = 1.0F;

	public Currency() {
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Currency) {
			return mId.equals(((Currency) obj).getId());
		}
		return false;
	}

	@Override
	public String toString() {
		return String.format("%1$d-%2$s", mId, mName);
	}

	public String getId() {
		return mId;
	}

	public void setId(String id) {
		mId = id;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		mName = name;
	}

	public float getConvFactor() {
		return mConvFactor;
	}

	public void setConvFactor(float convFactor) {
		mConvFactor = convFactor;
	}

}
