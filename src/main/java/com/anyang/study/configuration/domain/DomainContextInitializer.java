package com.anyang.study.configuration.domain;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.springframework.core.env.AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME;

@Slf4j
public class DomainContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	public static final String CONFIGURATION_PROPERTIES_SOURCE_NAME = "_configuration_properties_property_source_";

	public static final String PROJECT_PROPERTIES_LOCATION = "classpath:properties/project-${" + ACTIVE_PROFILES_PROPERTY_NAME + "}.xml";

	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		String profile = getActiveProfile(applicationContext);
		log.info("profile : {}",profile);

		final String path = getProjectPropertiesPath(profile);

		Properties configurationProperties = loadPropertiesFromPaths(applicationContext, path);

		PropertiesPropertySource propertySource = new PropertiesPropertySource(CONFIGURATION_PROPERTIES_SOURCE_NAME, configurationProperties);
		applicationContext.getEnvironment().getPropertySources().addLast(propertySource);
		log.info("base.mysql.jdbc.url : {}",configurationProperties.getProperty("base.mysql.jdbc.url"));
	}

	private String getActiveProfile(ApplicationContext applicationContext) {
		String[] activeProfiles = applicationContext.getEnvironment().getActiveProfiles();

		if (ArrayUtils.isEmpty(activeProfiles)) {
			throw new IllegalStateException("profile is empty");
		}

		if (activeProfiles.length > 1) {
			throw new IllegalStateException("profile is too many");
		}

		return activeProfiles[0];
	}

	private String getProjectPropertiesPath(String profile) {
		return PROJECT_PROPERTIES_LOCATION.replaceAll("\\$\\{" + ACTIVE_PROFILES_PROPERTY_NAME + "}", profile);
	}

	private Properties loadPropertiesFromPaths(ResourceLoader resourceLoader, String path) {
		Properties configurationProperties = new Properties();

		Resource resource = resourceLoader.getResource(path);

		try(InputStream is = resource.getInputStream()) {
			Properties properties = new Properties();
			properties.loadFromXML(is);
			configurationProperties.putAll(properties);
		} catch (IOException e) {
			throw new IllegalStateException("is not exist properties : " + path, e);
		}

		return configurationProperties;
	}
}
