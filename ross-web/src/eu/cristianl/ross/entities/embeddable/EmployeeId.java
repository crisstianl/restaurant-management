package eu.cristianl.ross.entities.embeddable;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import eu.cristianl.ross.entities.tables.EmployeeTable;

@Embeddable
public class EmployeeId implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = EmployeeTable.ID, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer mId;

	@Column(name = EmployeeTable.USERNAME, nullable = false, length = 50)
	private String mUsername;

	public EmployeeId() {
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof EmployeeId) {
			EmployeeId another = (EmployeeId) obj;
			return (mId.equals(another.getId())) && (mUsername.equals(another.getUsername()));
		}
		return false;
	}

	@Override
	public int hashCode() {
		return mId;
	}

	@Override
	public String toString() {
		return String.format("%1$s-%2$s", mId, mUsername);
	}

	public Integer getId() {
		return mId;
	}

	public void setId(Integer id) {
		mId = id;
	}

	public String getUsername() {
		return mUsername;
	}

	public void setUsername(String name) {
		mUsername = name;
	}

}
