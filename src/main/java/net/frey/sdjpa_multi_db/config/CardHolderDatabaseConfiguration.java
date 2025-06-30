package net.frey.sdjpa_multi_db.config;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import net.frey.sdjpa_multi_db.domain.cardholder.CreditCardHolder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
        basePackages = "net.frey.sdjpa_multi_db.repositories.cardholder",
        entityManagerFactoryRef = "cardHolderEmf",
        transactionManagerRef = "cardHolderTransactionManager")
public class CardHolderDatabaseConfiguration {
    @Bean("cardHolderDataSourceProperties")
    @ConfigurationProperties("spring.datasource.cardholder")
    public DataSourceProperties properties() {
        return new DataSourceProperties();
    }

    @Bean("cardHolderDataSource")
    public DataSource dataSource(
            @Qualifier("cardHolderDataSourceProperties") DataSourceProperties cardHolderDataSourceProperties) {
        return cardHolderDataSourceProperties
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean("cardHolderEmf")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Qualifier("cardHolderDataSource") DataSource cardHolderDataSource, EntityManagerFactoryBuilder builder) {
        return builder.dataSource(cardHolderDataSource)
                .packages(CreditCardHolder.class)
                .persistenceUnit("cardholder")
                .build();
    }

    @Bean("cardHolderTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("cardHolderEmf") LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
        return new JpaTransactionManager(entityManagerFactoryBean.getObject());
    }
}
