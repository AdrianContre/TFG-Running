package com.example.API_Running.Config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        SecurityScheme securityScheme = new SecurityScheme()
                .name("Bearer Authentication")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        // Aplica el esquema de seguridad a los endpoints protegidos
        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("Bearer Authentication");
        return new OpenAPI()
                .info(new Info()
                        .title("API RUNNING4ALL")
                        .version("1.0")
                        .description("API documentation for RUNNING4ALL"))
                .addSecurityItem(securityRequirement) // Aplica el esquema a todos los endpoints
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("Bearer Authentication", securityScheme));
    }
}

