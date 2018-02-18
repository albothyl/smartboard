package com.anyang.study.configuration.domain;

import com.anyang.study.Base;
import com.anyang.study.domain.Domain;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import static org.springframework.orm.jpa.vendor.Database.MYSQL;

@Configuration
@EnableJpaAuditing
@EnableTransactionManagement
@ComponentScan(basePackageClasses = { Domain.class })
@EnableJpaRepositories(basePackageClasses = { Base.class })
public class DomainContextConfig {

	private static final String DB_HOST = "jdbc:mysql://127.0.0.1:3306/smartboard?useSSL=false&useUnicode=true&characterEncoding=UTF8";
	private static final String DB_ID = "db_id";
	private static final String DB_PW = "db_pw";

	//JPA
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
		jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
		jpaProperties.put("hibernate.show_sql", true);
		jpaProperties.put("hibernate.format_sql", true);
		jpaProperties.put("hibernate.ejb.naming_strategy", "org.hibernate.cfg.DefaultNamingStrategy");

		return jpaProperties;
	}

	//MyBatis
	@Bean
	public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource, ApplicationContext applicationContext) throws IOException {

		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(dataSource);
		factoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis/config/mybatisConfig.xml"));

		return factoryBean;
	}

	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	//Common
	@Bean
	public DataSource dataSource() throws SQLException {
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		dataSource.setDriver(new com.mysql.jdbc.Driver());
		dataSource.setUrl(DB_HOST);
		dataSource.setUsername(DB_ID);
		dataSource.setPassword(DB_PW);

		return dataSource;
	}

	@Bean
	public PlatformTransactionManager transactionManager() throws SQLException {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return txManager;
	}
}
