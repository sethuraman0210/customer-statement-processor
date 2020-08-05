package com.rabobank.customer.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger configuration for the controllers
 *
 * @author Sethuraman Sathiyamoorthy
 * @version 1.0
 * @since 1.0
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    /**
     * Method initializes the configurations for Swagger UI
     */
    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false)
                .select().apis(RequestHandlerSelectors.basePackage("com.rabobank.customer.controller"))
                .build();
    }
}