package com.example.springliquidbase;

import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.config.DatabaseConfig;
import io.ebean.datasource.DataSourceConfig;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class ServerConfig {

    @Value("${spring.translator.datasource.username}")
    private String username;
    @Value("${spring.translator.datasource.password}")
    private String password;
    @Value("${spring.translator.datasource.url}")
    private String url;
    @Value("${spring.translator.liquibase.change-log}")
    private String changelog;
    @Value("${spring.translator.properties.ebean.dbSchema}")
    private String dbSchema;

    public DataSourceConfig datasource(){
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUsername("postgres");
        dataSourceConfig.setPassword("91324576TTT");
        dataSourceConfig.setUrl("jdbc:postgresql://localhost:5432/translator");
        dataSourceConfig.setSchema("public");
        return dataSourceConfig;
    }

    @Bean
    public Database database(){
        DatabaseConfig cfg = new DatabaseConfig();
        cfg.setDataSourceConfig(datasource());
        return DatabaseFactory.create(cfg);
    }
}
