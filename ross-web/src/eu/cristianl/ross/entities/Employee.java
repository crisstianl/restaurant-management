package eu.cristianl.ross.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import eu.cristianl.ross.entities.embeddable.CurriculumVitae;
import eu.cristianl.ross.entities.embeddable.EmployeeId;
import eu.cristianl.ross.entities.support.GenderType;
import eu.cristianl.ross.entities.tables.EmployeeTable;

@Entity
@Table(name = EmployeeTable.TABLE_NAME)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Employee implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EmployeeId mId;

	@OneToOne
	@JoinColumn(name = EmployeeTable.JOB_ID, nullable = false)
	private Job mJob;

	@Column(name = EmployeeTable.PASSWORD, length = 20, nullable = false)
	private String mPassword;

	@Column(name = EmployeeTable.HIRE_DATE)
	@Temporal(TemporalType.DATE)
	private Date mHireDate;

	@Column(name = EmployeeTable.NAME, nullable = false, length = 50)
	private String mName;

	@Column(name = EmployeeTable.BIRTHDATE, nullable = false)
	@Temporal(TemporalType.DATE)
	private Date mBirthDate;

	@Column(name = EmployeeTable.PHONE, nullable = false)
	private String mPhone;

	@Column(name = EmployeeTable.EMAIL, nullable = false)
	private String mEmail;

	@Column(name = EmployeeTable.FACEBOOK)
	private String mFacebook;

	@Column(name = EmployeeTable.GENDER, nullable = false)
	@Enumerated(EnumType.STRING)
	private GenderType mGender;

	@Embedded
	private CurriculumVitae mCurriculumVitae;

	public Employee() {
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Employee) {
			Employee another = (Employee) obj;
			return mId.equals(another.mId);
		}
		return false;
	}

	@Override
	public String toString() {
		return mId.toString();
	}

	public Integer getId() {
		return mId.getId();
	}

	public void setId(Integer id) {
		if (mId == null) {
			mId = new EmployeeId();
		}
		mId.setId(id);
	}

	public String getUsername() {
		return mId.getUsername();
	}

	public void setUsername(String username) {
		if (mId == null) {
			mId = new EmployeeId();
		}
		mId.setUsername(username);
	}

	public Job getJob() {
		return mJob;
	}

	public void setJob(Job job) {
		mJob = job;
	}

	public String getPassword() {
		return mPassword;
	}

	public void setPassword(String password) {
		mPassword = password;
	}

	public Date getHireDate() {
		return mHireDate;
	}

	public void setHireDate(Date hireDate) {
		mHireDate = hireDate;
	}

	public CurriculumVitae getCurriculumVitae() {
		return mCurriculumVitae;
	}

	public void setCurriculumVitae(CurriculumVitae curriculumVitae) {
		mCurriculumVitae = curriculumVitae;
	}

	public Date getBirthDate() {
		return mBirthDate;
	}

	public void setBirthDate(Date birthDate) {
		mBirthDate = birthDate;
	}

	public String getPhone() {
		return mPhone;
	}

	public void setPhone(String phone) {
		mPhone = phone;
	}

	public String getEmail() {
		return mEmail;
	}

	public void setEmail(String email) {
		mEmail = email;
	}

	public String getFacebook() {
		return mFacebook;
	}

	public void setFacebook(String facebook) {
		mFacebook = facebook;
	}

	public GenderType getGender() {
		return mGender;
	}

	public void setGender(GenderType gender) {
		mGender = gender;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		mName = name;
	}
}
