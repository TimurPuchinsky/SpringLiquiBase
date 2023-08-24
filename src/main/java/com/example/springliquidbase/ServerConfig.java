package com.example.springliquidbase;

import com.example.springliquidbase.infrastructure.repository.DbModel;
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
        dataSourceConfig.setUsername(username);
        dataSourceConfig.setPassword(password);
        dataSourceConfig.setUrl(url);
        dataSourceConfig.setSchema(dbSchema);
        return dataSourceConfig;
    }

    @Bean
    public DbModel database(){
        DatabaseConfig cfg = new DatabaseConfig();
        cfg.setRegister(false);
        cfg.setDataSourceConfig(datasource());
        return new DbModel(DatabaseFactory.create(cfg));
    }
}
