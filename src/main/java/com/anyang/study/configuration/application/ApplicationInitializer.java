package com.anyang.study.configuration.application;

import javax.servlet.ServletContext;

public class ApplicationInitializer extends AbstractApplicationInitializer {
	@Override
	public void onStartup(ServletContext servletContext) {
		loadRootApplicationContext(servletContext, ApplicationContextConfig.class, "webServlet", "/");
		loadDefaultFilters(servletContext);
	}
}