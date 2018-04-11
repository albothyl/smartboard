package com.anyang.study.configuration.application;

import com.anyang.study.configuration.domain.DomainContextConfig;

import javax.servlet.ServletContext;

public class WebApplicationInitializer extends AbstractApplicationInitializer {
	@Override
	public void onStartup(ServletContext servletContext) {
		loadLogbackConfigListener(servletContext);
		loadRootApplicationContext(servletContext, DomainContextConfig.class);
		loadDefaultFilters(servletContext);
		addDispatcherServlet(servletContext, "webServlet", ApplicationContextConfig.class,"/*");
	}
}