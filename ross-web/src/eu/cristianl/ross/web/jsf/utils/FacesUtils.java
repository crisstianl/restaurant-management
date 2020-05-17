package eu.cristianl.ross.web.jsf.utils;

import javax.faces.context.FacesContext;

public class FacesUtils {

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName) {
		FacesContext context = FacesContext.getCurrentInstance();
		Object result = context.getApplication().evaluateExpressionGet(context, "#{" + beanName + "}", Object.class);
		return (T) result;
	}
}
