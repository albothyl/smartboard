package com.anyang.study.configuration;

import com.anyang.study.Base;
import com.anyang.study.configuration.domain.DomainContextConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
@EnableWebMvc
@Import({DomainContextConfig.class})
@ComponentScan(basePackageClasses = {Base.class})
public class TestConfiguration extends WebMvcConfigurationSupport {
}
