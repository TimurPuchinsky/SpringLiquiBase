package com.example.springliquidbase.infrastructure.database;

import io.ebean.config.DatabaseConfig;
import io.ebean.datasource.DataSourceConfig;
import org.springframework.stereotype.Component;

@Component
public class Database {

    public DataSourceConfig datasource(){
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUsername("postgres");
        dataSourceConfig.setPassword("91324576TTT");
        dataSourceConfig.setUrl("jdbc:postgresql://localhost:5432/postgres");
        return dataSourceConfig;
    }

    public void database(){
        DatabaseConfig config = new DatabaseConfig();
        config.setDataSourceConfig(datasource());
    }

}
