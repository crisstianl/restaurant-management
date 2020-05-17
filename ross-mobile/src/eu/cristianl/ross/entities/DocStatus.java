package eu.cristianl.ross.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import eu.cristianl.ross.entities.support.DocStatusType;
import eu.cristianl.ross.entities.tables.DocStatusTable;

@DatabaseTable(tableName = DocStatusTable.TABLE_NAME)
public class DocStatus {

	@DatabaseField(columnName = DocStatusTable.ID, id = true, width = 1)
	private String mId;

	@DatabaseField(columnName = DocStatusTable.DESCRIPTION, canBeNull = false, width = 50)
	private String mDescription;

	private DocStatusType mStatus;

	public DocStatus() {
	}

	public DocStatus(String id) {
		this.mId = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DocStatus) {
			return mId.equals(((DocStatus) obj).getId());
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
		mStatus = DocStatusType.getByCode(id);
	}

	public String getDescription() {
		return mDescription;
	}

	public void setDescription(String description) {
		mDescription = description;
	}

	public DocStatusType getStatus() {
		return mStatus;
	}
}
