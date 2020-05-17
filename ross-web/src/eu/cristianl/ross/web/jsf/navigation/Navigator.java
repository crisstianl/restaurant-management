package eu.cristianl.ross.web.jsf.navigation;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import eu.cristianl.ross.web.jsf.beans.LoginBean;
import eu.cristianl.ross.web.jsf.utils.FacesUtils;

@ManagedBean(name = Navigator.NAME)
@SessionScoped
public class Navigator {
	public static final String NAME = "NavigatorBean";

	private static final String[] PAGES = new String[] { "login.xhtml", "employees.xhtml", "orders.xhtml",
			"orderRows.xhtml", "contacts.xhtml", "items.xhtml" };

	private int mCurrentPageIdx = 0;

	public String getCurrentPage() {
		return PAGES[mCurrentPageIdx];
	}

	public void setLoginPage() {
		mCurrentPageIdx = 0;
	}

	public void setEmployeesPage() {
		if (isLogged()) {
			mCurrentPageIdx = 1;
		}
	}

	public void setOrdersPage() {
		if (isLogged()) {
			mCurrentPageIdx = 2;
		}
	}

	public void setOrderRowsPage() {
		if (isLogged()) {
			mCurrentPageIdx = 3;
		}
	}

	public void setContactsPage() {
		if (isLogged()) {
			mCurrentPageIdx = 4;
		}
	}

	public void setItemsPage() {
		if (isLogged()) {
			mCurrentPageIdx = 5;
		}
	}

	private boolean isLogged() {
		LoginBean bean = FacesUtils.getBean(LoginBean.NAME);
		return bean != null && bean.isLogged();
	}
}
