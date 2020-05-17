package eu.cristianl.ross.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import eu.cristianl.ross.entities.tables.JobTable;

@Entity
@Table(name = JobTable.TABLE_NAME)
public class Job implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = JobTable.ID)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer mId;

	@Column(name = JobTable.TITLE, nullable = false, length = 50)
	private String mTitle;

	@Column(name = JobTable.DESCRIPTION)
	@Lob
	private String mDescription;

	@Column(name = JobTable.PAYCHECK, nullable = false)
	private float mPaycheck;

	@Column(name = JobTable.HOURS)
	private int mHours = 8;

	@Column(name = JobTable.RANK)
	private int mRank;

	public Job() {
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Job) {
			Job another = (Job) obj;
			return mId.equals(another.getId());
		}
		return false;
	}

	@Override
	public String toString() {
		return mTitle;
	}

	public Integer getId() {
		return mId;
	}

	public void setId(Integer id) {
		mId = id;
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		mTitle = title;
	}

	public String getDescription() {
		return mDescription;
	}

	public void setDescription(String description) {
		mDescription = description;
	}

	public float getPaycheck() {
		return mPaycheck;
	}

	public void setPaycheck(float paycheck) {
		mPaycheck = paycheck;
	}

	public int getHours() {
		return mHours;
	}

	public void setHours(int hours) {
		mHours = hours;
	}

	public int getRank() {
		return mRank;
	}

	public void setRank(int rank) {
		mRank = rank;
	}

}
