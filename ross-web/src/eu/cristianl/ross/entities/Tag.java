package eu.cristianl.ross.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import eu.cristianl.ross.entities.tables.TagTable;

@Entity
@Table(name = TagTable.TABLE_NAME)
public class Tag implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = TagTable.ID, length = 10)
	private String mId;

	@Column(name = TagTable.DESCRIPTION, nullable = false, length = 50)
	private String mDescription;

	@Column(name = TagTable.FOREGROUND, nullable = false)
	private long mForeground;

	@Column(name = TagTable.BACKGROUND, nullable = false)
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
