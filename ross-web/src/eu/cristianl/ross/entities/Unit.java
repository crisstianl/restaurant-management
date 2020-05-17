package eu.cristianl.ross.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import eu.cristianl.ross.entities.tables.UnitTable;

@Entity
@Table(name = UnitTable.TABLE_NAME)
public class Unit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = UnitTable.ID, length = 2)
	private String mId;

	@Column(name = UnitTable.DESCRIPTION, nullable = false, length = 50)
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
