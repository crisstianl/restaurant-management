package eu.cristianl.ross.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import eu.cristianl.ross.entities.tables.UnitTable;

@DatabaseTable(tableName = UnitTable.TABLE_NAME)
public class Unit {

	@DatabaseField(columnName = UnitTable.ID, id = true, width = 2)
	private String mId;

	@DatabaseField(columnName = UnitTable.DESCRIPTION, canBeNull = false, width = 50)
	private String mDescription;

	public Unit() {
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Unit) {
			return mId.equals(((Unit) obj).getId());
		}
		return false;
	}

	@Override
	public String toString() {
		return String.format("%1$s-%2$s", mId, mDescription);
	}

	public String getId() {
		return mId;
	}

	public void setId(String id) {
		mId = id;
	}

	public String getDescription() {
		return mDescription;
	}

	public void setDescription(String description) {
		mDescription = description;
	}

}
