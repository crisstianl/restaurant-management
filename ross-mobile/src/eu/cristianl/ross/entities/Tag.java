package eu.cristianl.ross.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import eu.cristianl.ross.entities.tables.TagTable;

@DatabaseTable(tableName = TagTable.TABLE_NAME)
public class Tag {

	@DatabaseField(columnName = TagTable.ID, id = true, width = 10)
	private String mId;

	@DatabaseField(columnName = TagTable.DESCRIPTION, canBeNull = false, width = 50)
	private String mDescription;

	@DatabaseField(columnName = TagTable.FOREGROUND, canBeNull = false)
	private long mForeground;

	@DatabaseField(columnName = TagTable.BACKGROUND, canBeNull = false)
	private long mBackground;

	public Tag() {
	}

	public Tag(String id, String description, int foreground, int background) {
		super();
		mId = id;
		mDescription = description;
		mForeground = foreground;
		mBackground = background;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Tag) {
			return mId.equals(((Tag) obj).getId());
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

	public long getForeground() {
		return mForeground;
	}

	public void setForeground(int foreground) {
		mForeground = foreground;
	}

	public long getBackground() {
		return mBackground;
	}

	public void setBackground(int background) {
		mBackground = background;
	}

}
