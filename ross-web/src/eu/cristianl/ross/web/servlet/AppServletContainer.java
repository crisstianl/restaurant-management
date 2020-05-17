package eu.cristianl.ross.web.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import com.sun.jersey.spi.container.WebApplication;
import com.sun.jersey.spi.container.servlet.ServletContainer;

public class AppServletContainer extends ServletContainer {
	private static final long serialVersionUID = 1L;

	// First method ran
	@Override
	protected WebApplication create() {
		return super.create();
	}

	// After create()
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	// When server stops
	@Override
	public void destroy() {
		super.destroy();
	}
}
