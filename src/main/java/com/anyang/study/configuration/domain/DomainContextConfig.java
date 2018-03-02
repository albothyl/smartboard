package com.anyang.study.configuration.domain;

import com.anyang.study.Base;
import com.anyang.study.repository.Repository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackageClasses = { Repository.class })
@EnableJpaRepositories(basePackageClasses = { Base.class})
public class DomainContextConfig {
    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

        dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
        dataSource.setUrl("jdbc:mysql://localhost:3306/smartboard?useSSL=false&serverTimezone=UTC");
        dataSource.setUsername("chawnsnd");
        dataSource.setPassword("tw161379");

        return dataSource;
    }
}
