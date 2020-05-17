package eu.cristianl.ross.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import eu.cristianl.ross.entities.tables.UserSessionTable;

@Entity
@Table(name = UserSessionTable.TABLE_NAME)
public class UserSession {

	@Id
	@Column(name = UserSessionTable.SESSION_ID, length = 256)
	private String mSessionId;

	@OneToOne
	@JoinColumns({ @JoinColumn(name = UserSessionTable.EMPLOYEE_ID, nullable = false),
			@JoinColumn(name = UserSessionTable.EMPLOYEE_NAME, nullable = false) })
	@NotFound(action = NotFoundAction.IGNORE)
	private Employee mEmployee;

	@Column(name = UserSessionTable.START_TIME, nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date mStartTime;

	@Column(name = UserSessionTable.END_TIME, nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date mEndTime;

	@Column(name = UserSessionTable.CLIENT_IP, length = 19)
	private String mClientIp;

	@Column(name = UserSessionTable.DEVICE_KEY, length = 255)
	private String mDeviceKey;

	public UserSession() {
	}

	public UserSession(String sessionId, Employee employee, Date startTime, Date endTime, String clientIp,
			String deviceKey) {
		mSessionId = sessionId;
		mEmployee = employee;
		mStartTime = startTime;
		mEndTime = endTime;
		mClientIp = clientIp;
		mDeviceKey = deviceKey;
	}

	public Employee getEmployee() {
		return mEmployee;
	}

	public void setEmployee(Employee employee) {
		mEmployee = employee;
	}

	public String getSessionId() {
		return mSessionId;
	}

	public void setSessionId(String sessionId) {
		mSessionId = sessionId;
	}

	public Date getStartTime() {
		return mStartTime;
	}

	public void setStartTime(Date startTime) {
		mStartTime = startTime;
	}

	public Date getEndTime() {
		return mEndTime;
	}

	public void setEndTime(Date endTime) {
		mEndTime = endTime;
	}

	public String getClientIp() {
		return mClientIp;
	}

	public void setClientIp(String clientIp) {
		mClientIp = clientIp;
	}

	public String getDeviceKey() {
		return mDeviceKey;
	}

	public void setDeviceKey(String deviceKey) {
		mDeviceKey = deviceKey;
	}
}
