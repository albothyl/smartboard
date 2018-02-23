package com.anyang.study.configuration;

import com.anyang.study.Base;
import com.github.jknack.handlebars.springmvc.HandlebarsViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.sql.DataSource;
import java.sql.Driver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses = { Base.class })
public class ApplicationContextConfig extends WebMvcConfigurationSupport {

	@Bean
	public HandlebarsViewResolver handlebarsViewResolver() {
		HandlebarsViewResolver viewResolver = new HandlebarsViewResolver();
		viewResolver.setFailOnMissingFile(false);
		viewResolver.setCache(true);

		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".hbs");

		return viewResolver;
	}

	@Bean
	public DataSource dataSource(){
		SimpleDriverDataSource dataSource= new SimpleDriverDataSource();

		dataSource.setDriverClass(Driver.class);
		dataSource.setUrl("jdbc:mysql://localhost/EASYCOMPANY?characterEncoding=UTF-8");
		dataSource.setUsername("root");
		dataSource.setPassword("oracle");

		return dataSource;
	}
}
