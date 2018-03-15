package com.anyang.study.configuration.application;

import com.anyang.study.Base;
import com.anyang.study.configuration.domain.DomainContextConfig;
import com.github.jknack.handlebars.springmvc.HandlebarsViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
@EnableWebMvc
@Import({DomainContextConfig.class})
@ComponentScan(basePackageClasses = {Base.class})
public class ApplicationContextConfig extends WebMvcConfigurationSupport {

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public HandlebarsViewResolver handlebarsViewResolver() {
        HandlebarsViewResolver viewResolver = new HandlebarsViewResolver();
        viewResolver.setFailOnMissingFile(false);
        viewResolver.setCache(false);

        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".hbs");

        return viewResolver;
    }
}
