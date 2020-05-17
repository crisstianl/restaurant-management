package eu.cristianl.ross.web.jsf.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class FacesMessages {

	public static void error(String forKey, String title, String message) {
		show(forKey, title, message, FacesMessage.SEVERITY_ERROR);
	}

	public static void info(String forKey, String title, String message) {
		show(forKey, title, message, FacesMessage.SEVERITY_INFO);
	}

	private static void show(String forKey, String title, String message, FacesMessage.Severity level) {
		FacesContext.getCurrentInstance().addMessage(forKey, new FacesMessage(level, title, message));
	}

}
