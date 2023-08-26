package com.example.springliquidbase;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Dictionary APIS", version = "3.0", description = "Apis"))
public class SpringLiquibaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringLiquibaseApplication.class, args);
    }

}
