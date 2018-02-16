package com.anyang.study.configuration.application;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public class ApplicationInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) {
		AnnotationConfigWebApplicationContext servletApplicationContext = new AnnotationConfigWebApplicationContext();
		servletApplicationContext.register(ApplicationContextConfig.class);

		final ServletRegistration.Dynamic webServlet = servletContext.addServlet("webServlet", new DispatcherServlet(servletApplicationContext));
		webServlet.setLoadOnStartup(1);
		webServlet.addMapping("/");
	}
}