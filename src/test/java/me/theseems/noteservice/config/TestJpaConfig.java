package me.theseems.noteservice.config;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Profile("test")
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("me.theseems.noteservice.repository")
@EntityScan("me.theseems.noteservice.entity")
public class TestJpaConfig extends JpaBaseConfiguration {
    protected TestJpaConfig(DataSource dataSource, JpaProperties properties, ObjectProvider<JtaTransactionManager> jtaTransactionManager) {
        super(dataSource, properties, jtaTransactionManager);
    }

    @Configuration
    static class DataSourceConfig {
        @Bean
        public EmbeddedPostgres embeddedPostgres() throws IOException {
            return EmbeddedPostgres.start();
        }

        @Bean
        public DataSource dataSource(EmbeddedPostgres postgres) {
            HikariDataSource dataSource = new HikariDataSource();
            dataSource.setJdbcUrl(postgres.getJdbcUrl("postgres", "postgres"));
            return dataSource;
        }

        @Bean
        public PlatformTransactionManager transactionManager() throws IOException {
            return new DataSourceTransactionManager(dataSource(embeddedPostgres()));
        }
    }

    @Override
    protected AbstractJpaVendorAdapter createJpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setShowSql(false);
        adapter.setDatabasePlatform("org.eclipse.persistence.platform.database.PostgreSQLPlatform");
        adapter.setDatabase(Database.POSTGRESQL);

        return adapter;
    }

    @Override
    protected Map<String, Object> getVendorProperties() {
       Map<String, Object> jpaProperties = new HashMap<>();
       jpaProperties.put("hibernate.hbm2ddl.auto", "update");
       jpaProperties.put("hibernate.physical_naming_strategy", CamelCaseToUnderscoresNamingStrategy.class);
       jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");

       return jpaProperties;
    }
}
