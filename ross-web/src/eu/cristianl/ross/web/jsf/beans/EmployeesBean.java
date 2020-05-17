package eu.cristianl.ross.web.jsf.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.BehaviorEvent;

import eu.cristianl.ross.dal.AdminDal;
import eu.cristianl.ross.dal.WorkerDal;
import eu.cristianl.ross.entities.Admin;
import eu.cristianl.ross.entities.Employee;
import eu.cristianl.ross.entities.Worker;
import eu.cristianl.ross.utils.DateUtils;
import eu.cristianl.ross.utils.Utils;
import eu.cristianl.ross.web.jsf.utils.FacesMessages;

@ManagedBean(name = EmployeesBean.NAME)
@ViewScoped
public class EmployeesBean implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String NAME = "EmployeesBean";
	private static final String FACEBOOK_PICTURE_URL = "http://graph.facebook.com/%1$s/picture?type=normal";

	// data
	private long mCount = 0L;
	private Employee mSelection = null;
	private List<Employee> mEmployees = new ArrayList<Employee>();

	// Filters
	private Date mHireDate = null;
	private int mJobId = -1;

	public EmployeesBean() {
	}

	@PostConstruct
	public void init() {
		// set hire date
		Calendar cal = Calendar.getInstance();
		cal.set(2000, Calendar.JANUARY, 1, 0, 0, 0);
		mHireDate = cal.getTime();

		mCount = AdminDal.I().totalAdmins();
		mCount += WorkerDal.I().totalWorkers();
		queryEmployees();
	}

	@PreDestroy
	public void destroy() {
		mSelection = null;
		mEmployees.clear();
		mEmployees = null;
	}

	public void hireDateChanged(BehaviorEvent event) {
		queryEmployees();
	}

	public void jobTypeChanged(BehaviorEvent event) {
		queryEmployees();
	}

	private void queryEmployees() {
		mEmployees.clear();

		List<Admin> admins = AdminDal.I().query(mJobId, mHireDate, (int) mCount);
		if (!Utils.isEmptyOrNull(admins)) {
			mEmployees.addAll(admins);
		}

		List<Worker> workers = WorkerDal.I().query(mJobId, mHireDate, (int) mCount);
		if (!Utils.isEmptyOrNull(workers)) {
			mEmployees.addAll(workers);
		}

		if (!mEmployees.isEmpty()) {
			mSelection = mEmployees.get(0);
		}
	}

	private void update() {
		if (mSelection instanceof Worker) {
			WorkerDal.I().update((Worker) mSelection);
		} else if (mSelection instanceof Admin) {
			AdminDal.I().update((Admin) mSelection);
		} else {
			return;
		}
		FacesMessages.info(null, "Success", "Employee saved !");
	}

	// Filters
	public Date getHireDate() {
		return mHireDate;
	}

	public void setHireDate(Date hireDate) {
		mHireDate = hireDate;
	}

	public void setJobId(int value) {
		mJobId = value;
	}

	public int getJobId() {
		return mJobId;
	}

	// Table Data
	public List<Employee> getEmployees() {
		return mEmployees;
	}

	public void setSelection(Employee value) {
		mSelection = value;
	}

	public Employee getSelection() {
		return mSelection;
	}

	// Form data
	public String getJob() {
		return mSelection.getJob().getTitle();
	}

	public String getWorkExperience() {
		return mSelection.getCurriculumVitae().getExperience();
	}

	public void setWorkExperience(String value) {
		if (!mSelection.getCurriculumVitae().getExperience().equals(value)) {
			mSelection.getCurriculumVitae().setExperience(value);
			update();
		}
	}

	public String getEducation() {
		return mSelection.getCurriculumVitae().getEducation();
	}

	public void setEducation(String value) {
		if (!mSelection.getCurriculumVitae().getEducation().equals(value)) {
			mSelection.getCurriculumVitae().setEducation(value);
			update();
		}
	}

	public String getLanguages() {
		return mSelection.getCurriculumVitae().getLanguages();
	}

	public void setLanguages(String value) {
		if (!mSelection.getCurriculumVitae().getLanguages().equals(value)) {
			mSelection.getCurriculumVitae().setLanguages(value);
			update();
		}
	}

	public String getSkills() {
		return mSelection.getCurriculumVitae().getSkills();
	}

	public void setSkills(String value) {
		if (!mSelection.getCurriculumVitae().getSkills().equals(value)) {
			mSelection.getCurriculumVitae().setSkills(value);
			update();
		}
	}

	public String getName() {
		return mSelection.getName();
	}

	public String getPhone() {
		return mSelection.getPhone();
	}

	public void setPhone(String value) {
		if (!mSelection.getPhone().equals(value)) {
			mSelection.setPhone(value);
			update();
		}
	}

	public String getEmail() {
		return mSelection.getEmail();
	}

	public void setEmail(String value) {
		if (!mSelection.getEmail().equals(value)) {
			mSelection.setEmail(value);
			update();
		}
	}

	public String getBirthDate() {
		return DateUtils.getDateString(mSelection.getBirthDate());
	}

	public String getFacebook() {
		return mSelection.getFacebook();
	}

	public void setFacebook(String value) {
		if (!mSelection.getFacebook().equals(value)) {
			mSelection.setFacebook(value);
			update();
		}
	}

	public String getFacebookPicture() {
		String facebook = mSelection.getFacebook();
		if (!Utils.isEmptyOrBlank(facebook)) {
			String[] tokens = facebook.split("/");
			if (!Utils.isEmptyOrNull(tokens)) {
				String profileId = tokens[tokens.length - 1];
				return String.format(FACEBOOK_PICTURE_URL, profileId);
			}
		}
		return "assets/images/photo_unknown.jpg";
	}

}
