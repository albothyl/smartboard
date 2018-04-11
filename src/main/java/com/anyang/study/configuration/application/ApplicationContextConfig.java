package com.anyang.study.configuration.application;

import com.anyang.study.Base;
import com.github.jknack.handlebars.springmvc.HandlebarsViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/*
	NOTE :
		WebMvcConfigurationSupport는 사용자가 기능을 직접 구현하는 설정이다.
		@EnableWebMvc는 WebMvcConfigurationSupport를 상속하여 기능을 확장한 설정이다.
		때문에 @EnableWebMvc와 WebMvcConfigurationSupport를 같이 쓰면 기능이 중복된다.

		@EnableWebMvc + WebMvcConfigurer 조합으로 사용하면 스프링 기본 설정이 적용된다.
 */

//@Configuration
//@EnableWebMvc
//@EnableSpringDataWebSupport
//public class ApplicationContextConfig implements WebMvcConfigurer {

@Configuration
@ComponentScan(basePackageClasses = { Base.class })
public class ApplicationContextConfig extends WebMvcConfigurationSupport {

	@Bean
	public HandlebarsViewResolver handlebarsViewResolver() {
		HandlebarsViewResolver viewResolver = new HandlebarsViewResolver();
		viewResolver.setCache(false);

		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".hbs");

		return viewResolver;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/swagger-ui.html")
			.addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**")
			.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	@Override
	protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new PageableHandlerMethodArgumentResolver());
	}
}
