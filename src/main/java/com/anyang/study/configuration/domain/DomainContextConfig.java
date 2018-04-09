package com.anyang.study.configuration.domain;

import com.anyang.study.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

import static org.springframework.orm.jpa.vendor.Database.MYSQL;

@Configuration
@EnableJpaAuditing
@EnableTransactionManagement
@EnableJpaRepositories(basePackageClasses = {Base.class})
public class DomainContextConfig {

    @Autowired
    private Environment environment;

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public DataSource dataSource() throws SQLException {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriver(new com.mysql.jdbc.Driver());
        dataSource.setUrl(environment.getProperty("base.mysql.jdbc.url"));
        dataSource.setUsername(environment.getProperty("base.mysql.jdbc.username"));
        dataSource.setPassword(environment.getProperty("base.mysql.jdbc.password"));

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws SQLException {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(MYSQL);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setDataSource(dataSource());
        factory.setJpaProperties(hibernateProperties());
        factory.setPackagesToScan("com.anyang.study");
        return factory;
    }

    private Properties hibernateProperties() {
        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.dialect.storage_engine",
            environment.getRequiredProperty("hibernate.dialect.storage_engine"));
        jpaProperties.put("hibernate.show_sql",
            environment.getRequiredProperty("hibernate.show_sql"));
        jpaProperties.put("hibernate.format_sql",
            environment.getRequiredProperty("hibernate.format_sql"));
        jpaProperties.put("hibernate.ejb.naming_strategy",
            environment.getRequiredProperty("hibernate.ejb.naming_strategy"));

        return jpaProperties;
    }

    @Bean
    public PlatformTransactionManager transactionManager() throws SQLException {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return txManager;
    }

}
