
package com.lean.cdc.config;


import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.lean.cdc.sink.repository",
        entityManagerFactoryRef = "targetEntityManagerFactory",
        transactionManagerRef = "targetTransactionManager"
)
public class SinkDataSourceConfig {
    @Bean
    @ConfigurationProperties("spring.datasource-target")
    public DataSourceProperties targetDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean("targetDataSource")
    @ConfigurationProperties("spring.datasource.target.configuration")
    public DataSource targetDataSource() {
        return targetDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Bean(name = "targetEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean targetEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(targetDataSource())
                .packages("com.lean.cdc.sink.entity")
                .persistenceUnit("targetDatabase")
                .build();
    }

    @Bean(name = "targetTransactionManager")
    public PlatformTransactionManager sourceTransactionManager(
            final @Qualifier("targetEntityManagerFactory") EntityManagerFactory targetEntityManagerFactory) {
        return new JpaTransactionManager(targetEntityManagerFactory);
    }

    @Bean(name = "targetJdbcTemplate")
    public JdbcTemplate targetJdbcTemplate(@Qualifier("targetDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "targetNamedParameterJdbcTemplate")
    public NamedParameterJdbcTemplate targetNamedParameterJdbcTemplate(@Qualifier("targetDataSource") DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }
}
