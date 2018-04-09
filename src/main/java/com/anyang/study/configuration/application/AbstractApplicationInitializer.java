package com.anyang.study.configuration.application;

import com.anyang.study.configuration.domain.DomainContextInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration.Dynamic;

import static java.util.EnumSet.of;
import static javax.servlet.DispatcherType.FORWARD;
import static javax.servlet.DispatcherType.REQUEST;
import static org.apache.commons.lang3.CharEncoding.UTF_8;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.web.context.ContextLoader.CONTEXT_INITIALIZER_CLASSES_PARAM;

@Order(HIGHEST_PRECEDENCE)
public abstract class AbstractApplicationInitializer implements WebApplicationInitializer {

	protected ApplicationContext loadRootApplicationContext(ServletContext servletContext, Class<?> configClass, String servletName, String mapping) {
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(configClass);

		servletContext.addListener(new ContextLoaderListener(rootContext));
		servletContext.setInitParameter(CONTEXT_INITIALIZER_CLASSES_PARAM, DomainContextInitializer.class.getName());

		Dynamic dispatcherServlet = servletContext.addServlet(servletName, new DispatcherServlet(rootContext));
		dispatcherServlet.setLoadOnStartup(1);
		dispatcherServlet.addMapping(mapping);

		return rootContext;
	}

	protected void loadDefaultFilters(ServletContext servletContext) {
		addEncodingFilter(servletContext);
	}

	private void addEncodingFilter(ServletContext servletContext) {
		FilterRegistration encodingFilter = servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);
		encodingFilter.setInitParameter("encoding", UTF_8);
		encodingFilter.setInitParameter("forceEncoding", "true");
		encodingFilter.addMappingForUrlPatterns(of(REQUEST, FORWARD), true, "/*");
	}
}
