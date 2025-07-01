package net.frey.sdjpa_multi_db.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayConfiguration {
    @Bean("cardFlywayProperties")
    @ConfigurationProperties("spring.datasource.card.flyway")
    public DataSourceProperties cardFlywayProperties() {
        return new DataSourceProperties();
    }

    @Bean(initMethod = "migrate")
    public Flyway flywayCard(@Qualifier("cardFlywayProperties") DataSourceProperties cardFlywayProperties) {
        return Flyway.configure()
                .dataSource(
                        cardFlywayProperties.getUrl(),
                        cardFlywayProperties.getUsername(),
                        cardFlywayProperties.getPassword())
                .locations("classpath:/db/migration/card")
                .load();
    }

    @Bean("cardHolderFlywayProperties")
    @ConfigurationProperties("spring.datasource.cardholder.flyway")
    public DataSourceProperties cardHolderFlywayProperties() {
        return new DataSourceProperties();
    }

    @Bean(initMethod = "migrate")
    public Flyway flywayCardHolder(
            @Qualifier("cardHolderFlywayProperties") DataSourceProperties cardHolderFlywayProperties) {
        return Flyway.configure()
                .dataSource(
                        cardHolderFlywayProperties.getUrl(),
                        cardHolderFlywayProperties.getUsername(),
                        cardHolderFlywayProperties.getPassword())
                .locations("classpath:/db/migration/cardholder")
                .load();
    }

    @Bean("panFlywayProperties")
    @ConfigurationProperties("spring.datasource.pan.flyway")
    public DataSourceProperties panFlywayProperties() {
        return new DataSourceProperties();
    }

    @Bean(initMethod = "migrate")
    public Flyway flywayPan(@Qualifier("panFlywayProperties") DataSourceProperties panFlywayProperties) {
        return Flyway.configure()
                .dataSource(
                        panFlywayProperties.getUrl(),
                        panFlywayProperties.getUsername(),
                        panFlywayProperties.getPassword())
                .locations("classpath:/db/migration/pan")
                .load();
    }
}
