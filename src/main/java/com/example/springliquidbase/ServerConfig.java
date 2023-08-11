package com.example.springliquidbase;

import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.config.DatabaseConfig;
import io.ebean.datasource.DataSourceConfig;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
@Getter
public class ServerConfig {

    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.liquibase.change-log}")
    private String changelog;
    @Value("${spring.jpa.properties.ebean.dbSchema}")
    private String dbSchema;
    @Value("${spring.datasource.driver-class-name}")
    private String driver;

    public DataSourceConfig datasource(){
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUsername("postgres");
        dataSourceConfig.setPassword("91324576TTT");
        dataSourceConfig.setUrl("jdbc:postgresql://localhost:5432/translator");
        dataSourceConfig.setSchema("public");
        return dataSourceConfig;
    }

//    public Properties dataproperties(){
//        Properties properties = new Properties();
//        properties.put("spring.datasource.username", username);
//        properties.put("spring.datasource.password", password);
//        properties.put("spring.datasource.url", url);
//        properties.put("spring.liquibase.change-log", changelog);
//        properties.put("spring.jpa.properties.ebean.dbSchema", dbSchema);
//        properties.put("spring.datasource.driver-class-name", driver);
//        properties.put("spring.jpa.properties.ebean.db.ddl.run", true);
//        properties.put("spring.jpa.properties.ebean.db.ddl.generate", true);
//        properties.put("spring.jpa.properties.ebean.migration.run", true);
//        return properties;
//    }

    @Bean
    public Database database(){
        DatabaseConfig cfg = new DatabaseConfig();
        cfg.setDataSourceConfig(datasource());
        //cfg.loadFromProperties(dataproperties());
        return DatabaseFactory.create(cfg);
    }
}
