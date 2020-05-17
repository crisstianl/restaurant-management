package eu.cristianl.ross.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import eu.cristianl.ross.entities.tables.CategoryTable;

@DatabaseTable(tableName = CategoryTable.TABLE_NAME)
public class Category {

	@DatabaseField(columnName = CategoryTable.ID, id = true, width = 10)
	private String mId;

	@DatabaseField(columnName = CategoryTable.DESCRIPTION, canBeNull = false, width = 50)
	private String mDescription;

	public Category() {
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Category) {
			return mId.equals(((Category) obj).getId());
		}
		return false;
	}

	@Override
	public String toString() {
		return mDescription;
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
