package net.frey.sdjpa_multi_db.config;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import net.frey.sdjpa_multi_db.domain.creditcard.CreditCard;
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
        basePackages = "net.frey.sdjpa_multi_db.repositories.creditcard",
        entityManagerFactoryRef = "cardEmf",
        transactionManagerRef = "cardTransactionManager")
public class CardDatabaseConfiguration {
    @Bean("cardDataSourceProperties")
    @ConfigurationProperties("spring.datasource.card")
    public DataSourceProperties properties() {
        return new DataSourceProperties();
    }

    @Bean("cardDataSource")
    public DataSource cardDataSource(
            @Qualifier("cardDataSourceProperties") DataSourceProperties cardHolderDataSourceProperties) {
        return cardHolderDataSourceProperties
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean("cardEmf")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Qualifier("cardDataSource") DataSource cardDataSource, EntityManagerFactoryBuilder builder) {
        return builder.dataSource(cardDataSource)
                .packages(CreditCard.class)
                .persistenceUnit("card")
                .build();
    }

    @Bean("cardTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("cardEmf") LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
        return new JpaTransactionManager(entityManagerFactoryBean.getObject());
    }
}
