
package com.lean.cdc.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
@EnableJpaRepositories(
        basePackages = "com.lean.cdc.source.repository",
        entityManagerFactoryRef = "sourceEntityManagerFactory",
        transactionManagerRef = "sourceTransactionManager"
)
@EnableTransactionManagement
public class SourceDataSourceConfig {
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource-source")
    public DataSourceProperties sourceDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "sourceDataSource")
    @Primary
    @ConfigurationProperties("spring.datasource.source.configuration")
    public DataSource sourceDataSource() {
        return sourceDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Primary
    @Bean(name = "sourceEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean sourceEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(sourceDataSource())
                .packages("com.lean.cdc.source.entity")
                .persistenceUnit("sourceDatabase")
                .build();
    }

    @Primary
    @Bean(name = "sourceTransactionManager")
    public PlatformTransactionManager sourceTransactionManager(
            final @Qualifier("sourceEntityManagerFactory") EntityManagerFactory sourceEntityManagerFactory) {
        return new JpaTransactionManager(sourceEntityManagerFactory);
    }

    @Primary
    @Bean(name = "sourceJdbcTemplate")
    public JdbcTemplate sourceJdbcTemplate(@Qualifier("sourceDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Primary
    @Bean(name = "sourceNamedParameterJdbcTemplate")
    public NamedParameterJdbcTemplate sourceNamedParameterJdbcTemplate(@Qualifier("sourceDataSource") DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

}
