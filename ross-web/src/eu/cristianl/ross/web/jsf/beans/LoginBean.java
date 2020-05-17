package eu.cristianl.ross.web.jsf.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import eu.cristianl.ross.dal.AdminDal;
import eu.cristianl.ross.entities.Admin;
import eu.cristianl.ross.entities.Employee;
import eu.cristianl.ross.utils.Utils;
import eu.cristianl.ross.web.jsf.navigation.Navigator;
import eu.cristianl.ross.web.jsf.utils.FacesMessages;
import eu.cristianl.ross.web.jsf.utils.FacesUtils;

@ManagedBean(name = LoginBean.NAME)
@SessionScoped
public class LoginBean {
	public static final String NAME = "LoginBean";
	private static final int MAX_TRIES = 5;

	private String mFormName = null;
	private String mFormPassword = null;

	private int mTrysCount = 0;

	private Employee mUser = null;

	public LoginBean() {
	}

	public boolean isLogged() {
		return mUser != null;
	}

	public void toggleLogin(ActionEvent e) {
		if (isLogged()) {
			logout();
		} else {
			login();
		}
	}

	public void login() {
		if (Utils.isEmptyOrBlank(mFormName) || Utils.isEmptyOrBlank(mFormPassword)) {
			FacesMessages.info(null, "Fail", "Empty fields");

		} else if (++mTrysCount >= MAX_TRIES) {
			FacesMessages.info(null, "Fail", "Maximum number of trys has been reached");

		} else {
			mUser = AdminDal.I().getEmployee(mFormName, mFormPassword);
			if (mUser == null) {
				FacesMessages.info(null, "Fail", "Name or password incorrect");
			} else {
				FacesMessages.info(null, "Success", "Welcome " + mFormName);
				AppBean bean = FacesUtils.getBean(AppBean.NAME);
				bean.addUser(mFormName);

				Navigator navigator = FacesUtils.getBean(Navigator.NAME);
				navigator.setOrdersPage();
			}
		}
		mFormPassword = null;
	}

	public void logout() {
		mUser = null;
		mFormName = null;
		mFormPassword = null;
		mTrysCount = 0;
		FacesMessages.info(null, "Success", "Session closed");

		Navigator navigator = FacesUtils.getBean(Navigator.NAME);
		navigator.setLoginPage();
	}

	public boolean isAdmin() {
		return mUser instanceof Admin;
	}

	public String getUsername() {
		if (mUser != null) {
			return mUser.getName();
		}
		return mFormName;
	}

	public void setUsername(String formName) {
		mFormName = formName;
	}

	public String getPassword() {
		return mFormPassword;
	}

	public void setPassword(String formPassword) {
		mFormPassword = formPassword;
	}

	public String getLoginStatus() {
		return isLogged() ? "Logout" : "Login";
	}
}
