package com.uol.pb.challenge3.config;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

public class FlywayConfig {
    @Bean
    @Profile(value = {"default", "memory"})
    public Flyway flyway(DataSourceProperties dataSourceProperties){
        return Flyway.configure()
                .dataSource(dataSourceProperties.getUrl(),
                        dataSourceProperties.getUsername(),
                        dataSourceProperties.getPassword())
                .baselineOnMigrate(true)
                .locations("classpath:db/migration")
                .load();
    }
}
