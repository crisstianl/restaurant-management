package eu.cristianl.ross.web.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import eu.cristianl.ross.dal.database.DatabaseHelper;
import eu.cristianl.ross.utils.SQLUtils;

public class AppServletContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		DatabaseHelper.destroy();
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		DatabaseHelper.create();
		SQLUtils.insertTestData();
	}
}
