package com.example.springliquidbase.config;

import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.config.DatabaseConfig;
import io.ebean.datasource.DataSourceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataBase {

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
        DatabaseConfig config = new DatabaseConfig();
        config.setDataSourceConfig(datasource());
         //config.loadFromProperties();
        return DatabaseFactory.create(config);
    }
}
