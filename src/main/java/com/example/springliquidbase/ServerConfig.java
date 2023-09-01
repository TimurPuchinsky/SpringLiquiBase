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

    @Value("${postgres.sql.db-username}")
    private String username;
    @Value("${postgres.sql.db-password}")
    private String password;
    @Value("${postgres.sql.db-url}")
    private String url;
    @Value("${postgres.sql.db-changelog}")
    private String changelog;
    @Value("${postgres.sql.db-Schema}")
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
        cfg.setRegister(true);
        cfg.setDataSourceConfig(datasource());
        return new DbModel(DatabaseFactory.create(cfg));
    }
}
