package eu.cristianl.ross.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import eu.cristianl.ross.entities.support.DocStatusType;
import eu.cristianl.ross.entities.tables.DocStatusTable;

@Entity
@Table(name = DocStatusTable.TABLE_NAME)
public class DocStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = DocStatusTable.ID, length = 1)
	private String mId;

	@Column(name = DocStatusTable.DESCRIPTION, nullable = false, length = 50)
	private String mDescription;

	@Transient
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
