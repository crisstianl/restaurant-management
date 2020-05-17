package eu.cristianl.ross.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import eu.cristianl.ross.entities.tables.CategoryTable;

@Entity
@Table(name = CategoryTable.TABLE_NAME)
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = CategoryTable.ID, length = 10)
	private String mId;

	@Column(name = CategoryTable.DESCRIPTION, nullable = false, length = 50)
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
