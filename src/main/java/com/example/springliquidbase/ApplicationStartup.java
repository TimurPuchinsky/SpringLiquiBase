package com.example.springliquidbase;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.DatabaseConnection;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;

@Component
@Slf4j
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {

    ServerConfig serverConfig = new ServerConfig();

    public void applyMigrations(DataSource server, String lbSchema, String lbChangeLog){
        ResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor(getClass().getClassLoader());

        try (var connect = server.getConnection()) {
            try {
                DatabaseConnection databaseConnection = new JdbcConnection(connect);
                var database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(databaseConnection);

                database.setLiquibaseSchemaName(lbSchema);
                database.setDefaultSchemaName(lbSchema);

                Liquibase liquibase = new Liquibase(lbChangeLog, resourceAccessor, database);
                liquibase.update(new Contexts(), new LabelExpression());
            } catch (LiquibaseException ex) {
                log.error(ex.getMessage(), ex);
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        applyMigrations(serverConfig.database().dataSource(), "public", "db/changelog/2023/20230810-language-table.xml");
    }
}
