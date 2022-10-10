package com.geekbrains;

import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class PersistenceConfig {

    @Bean(name="dataSource")
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setJdbcUrl("jdbc:h2:./test");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        return dataSource;
    }

    private Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "none");
        hibernateProperties.setProperty("hibernate.show_sql", "false");

        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");

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