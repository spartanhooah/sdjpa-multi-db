package net.frey.sdjpa_multi_db.config;

import com.zaxxer.hikari.HikariDataSource;
import java.util.Properties;
import javax.sql.DataSource;
import net.frey.sdjpa_multi_db.domain.pan.CreditCardPAN;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
        basePackages = "net.frey.sdjpa_multi_db.repositories.pan",
        entityManagerFactoryRef = "panEmf",
        transactionManagerRef = "panTransactionManager")
public class PanDatabaseConfiguration {
    @Bean("panDataSourceProperties")
    @Primary
    @ConfigurationProperties("spring.datasource.pan")
    public DataSourceProperties properties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean("panDataSource")
    @ConfigurationProperties("spring.datasource.pan.hikari")
    public DataSource dataSource(
            @Qualifier("panDataSourceProperties") DataSourceProperties cardHolderDataSourceProperties) {
        return cardHolderDataSourceProperties
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Primary
    @Bean("panEmf")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Qualifier("panDataSource") DataSource panDataSource, EntityManagerFactoryBuilder builder) {
        var efb = builder.dataSource(panDataSource)
                .packages(CreditCardPAN.class)
                .persistenceUnit("pan")
                .build();

        var props = new Properties();
        props.put("hibernate.hbm2ddl.auto", "validate");
        props.put(
                "hibernate.physical_naming_strategy",
                "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");
        efb.setJpaProperties(props);

        return efb;
    }

    @Primary
    @Bean("panTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("panEmf") LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
        return new JpaTransactionManager(entityManagerFactoryBean.getObject());
    }
}
