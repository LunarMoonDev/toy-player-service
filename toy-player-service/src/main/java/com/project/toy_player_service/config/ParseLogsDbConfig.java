package com.project.toy_player_service.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "parseLogsEntityManagerFactoryTransactionManager",
        basePackages = "com.project.toy_project_service.repository",
        transactionManagerRef = "parseLogsPlatformTransactionManager")
public class ParseLogsDbConfig {

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String hibernateDDL;

    @Value("${spring.jpa.properties.hibernate.dialect}")
    private String hibernateDialect;

    @Bean(name = "parseLogsDataSource")
    @Primary
    @ConfigurationProperties(prefix = "db.parse.v1.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "parseLogsEntityManagerFactoryTransactionManager")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(
            EntityManagerFactoryBuilder builder,
            @Qualifier("parseLogsDataSource") DataSource dataSource) {

        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", hibernateDDL);
        properties.put("hibernate.dialect", hibernateDialect);
        properties.put("hibernate.physical_naming_strategy",
                CamelCaseToUnderscoresNamingStrategy.class.getName());
        properties.put("hibernate.implicit_naming_strategy",
                SpringImplicitNamingStrategy.class.getName());

        return builder.dataSource(dataSource).packages("com.project.toy_project_service.entity")
                .persistenceUnit("parseLogsPlatformTransactionManager").properties(properties)
                .build();
    }

    @Bean(name = "parseLogsPlatformTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("parseLogsEntityManagerFactoryTransactionManager") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
