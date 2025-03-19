package org.example.foodordersystem.config;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "BookManagement REST API",
                version = "1.0",
                description = "Comprehensive documentation for the BookWave REST API," +
                        " including endpoint descriptions and usage instructions."
        ),
        security = {@io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "bearerToken")},
        externalDocs = @ExternalDocumentation(
                description = "Access the full API documentation",
                url = "http://localhost:8080/swagger-ui/index.html"
        )
)
public class SwaggerConfig {

        @Bean
        public OpenAPI openAPI() {
                return new OpenAPI()

                        .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                        .components(new io.swagger.v3.oas.models.Components()
                                .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                        .name("bearerAuth")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));


        }
}