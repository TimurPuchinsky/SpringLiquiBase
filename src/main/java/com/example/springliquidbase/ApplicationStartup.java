package com.example.springliquidbase;

import io.ebean.DatabaseFactory;
import io.ebean.config.DatabaseConfig;
import io.ebean.datasource.DataSourceConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {

    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.url}")
    private String url;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUsername(username);
        dataSourceConfig.setPassword(password);
        dataSourceConfig.setUrl(url);

        DatabaseConfig config = new DatabaseConfig();
        config.setDataSourceConfig(dataSourceConfig);
        //config.loadFromProperties();
        DatabaseFactory.create(config);
    }

}
