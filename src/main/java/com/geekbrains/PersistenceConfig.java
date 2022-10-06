package com.geekbrains;

import javax.persistence.EntityManager;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class PersistenceConfig {

    @Bean(name="dataSource")
    public DataSource dataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/geekshop");
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");

        return dataSource;
    }

    private Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "none");
        hibernateProperties.setProperty("hibernate.show_sql", "true");

        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL94Dialect");
        hibernateProperties.put("hibernate.max_fetch_depth", 3);
        hibernateProperties.put("hibernate.jdbc.fetch_size", 20);
        hibernateProperties.put("hibernate.jdbc.batch_size", 10);

        return hibernateProperties;
    }

    @Bean
    public LocalSessionFactoryBean entityManagerFactory() {
        LocalSessionFactoryBean factory = new LocalSessionFactoryBean(); // фабрика
        factory.setDataSource(dataSource()); // источник (БД)
        factory.setPackagesToScan("com.geekbrains.persistence.entities"); // ссылка на классы-сущности
        factory.setHibernateProperties(hibernateProperties()); // настройки Hibernate
        return factory;
    }

    @Bean
    public EntityManager entityManager(SessionFactory sessionFactory) {
        return sessionFactory.createEntityManager();
    }

}