package com.cangzhitao.jbf.core.configuration;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.cangzhitao.jbf.core.register.ApplicationContextRegister;
import com.cangzhitao.jbf.core.util.PropertiesUtil;

@Configuration
@EnableJpaRepositories(basePackages={"com.cangzhitao"})
public class DatabaseConfiguration {

	// 确保先初始化ApplicationContextRegister，将SpringuUtil中的ApplicationContext初始化
	// 替代DependsOn
	@SuppressWarnings("unused")
	@Autowired
	private ApplicationContextRegister applicationContextRegister;
	
	@Bean(name="dataSource")
	public DataSource dataSource() throws IOException {
		DataSource dataSource = null;
		try {
			Properties properties = PropertiesUtil.loadProperties("database");
			dataSource = DruidDataSourceFactory.createDataSource(properties);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataSource;
	}
	
	@Bean(name="jpaVendorAdapter")
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setDatabase(Database.MYSQL);
		hibernateJpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");
		hibernateJpaVendorAdapter.setShowSql(true);
		hibernateJpaVendorAdapter.setGenerateDdl(true);
		return hibernateJpaVendorAdapter;
	}
	
	@Bean(name="entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
		String packagesToScan = "com.cangzhitao";
		LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
		emfb.setDataSource(dataSource);
		emfb.setJpaVendorAdapter(jpaVendorAdapter);
		String[] packagesToScans = packagesToScan.split(",");
		emfb.setPackagesToScan(packagesToScans);
		Properties properties = new Properties();
		properties.put("hibernate.id.new_generator_mappings", "false");
		emfb.setJpaProperties(properties);
		return emfb;
	}
	
	@Bean(name="jdbcTemplate")
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
	
	@Bean(name="namedParameterJdbcTemplate")
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
		return new NamedParameterJdbcTemplate(dataSource);
	}
	
}
