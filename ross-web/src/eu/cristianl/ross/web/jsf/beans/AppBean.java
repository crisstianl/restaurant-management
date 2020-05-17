package eu.cristianl.ross.web.jsf.beans;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashSet;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = AppBean.NAME)
@ApplicationScoped
public class AppBean implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String NAME = "AppBean";

	private HashSet<String> mUsers = new LinkedHashSet<String>();

	public AppBean() {
	}

	public void addUser(String user) {
		mUsers.add(user);
	}
}
