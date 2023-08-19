package com.manager.hotel.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.NonNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Optional;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class PersistenceConfig
        implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Value("${spring.sql.init.data}")
    private String[] initDataScriptPaths;

    @Value("${spring.jpa.show-sql}")
    private String showSql;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String hibernateDdlAuto;

    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;

    @Value("${spring.jpa.properties.hibernate.dialect}")
    private String dialect;

    @Bean
    public EntityManager entityManager(
            final LocalContainerEntityManagerFactoryBean factoryBean) {
        return Optional.ofNullable(factoryBean.getObject())
                .map(EntityManagerFactory::createEntityManager)
                .orElseThrow(() -> new IllegalStateException(
                        "Entity manager factory is not initialized"));
    }

    @Bean
    public DataSourceInitializer dataSourceScriptInitializer(
            final DataSource dataSource) {
        ResourceDatabasePopulator populator =
                new ResourceDatabasePopulator();
        Arrays.stream(initDataScriptPaths)
                .map(scriptPath -> applicationContext
                        .getResource(scriptPath))
                .forEach(populator::addScript);
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(populator);
        return initializer;
    }


    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource =
                new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            final DataSource dataSource) {
        Properties properties = new Properties();
        properties.setProperty("hibernate.show_sql", showSql);
        properties.setProperty("hibernate.hbm2ddl.auto", hibernateDdlAuto);
        properties.setProperty("hibernate.dialect", dialect);
        properties.setProperty("hibernate.default_batch_fetch_size", "16");
        LocalContainerEntityManagerFactoryBean factoryBean =
                new LocalContainerEntityManagerFactoryBean();
        factoryBean.setJpaProperties(properties);
        factoryBean.setDataSource(dataSource);
        factoryBean.setPackagesToScan("com.manager.hotel.model.entity");
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return factoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager(
            final LocalContainerEntityManagerFactoryBean factoryBean) {
        JpaTransactionManager transactionManager =
                new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                factoryBean.getObject());
        return transactionManager;
    }

    @Override
    public void setApplicationContext(
            final @NonNull ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }
}
