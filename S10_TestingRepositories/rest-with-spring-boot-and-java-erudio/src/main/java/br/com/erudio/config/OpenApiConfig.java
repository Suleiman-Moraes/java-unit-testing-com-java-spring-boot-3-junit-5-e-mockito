package br.com.erudio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Hello Swagger OpenAPI")
                        .version("v1")
                        .description("Susu's API")
                        .termsOfService("https://github.com/Suleiman-Moraes")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://github.com/Suleiman-Moraes")));
    }
}
