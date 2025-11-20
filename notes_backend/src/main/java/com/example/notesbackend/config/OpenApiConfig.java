package com.example.notesbackend.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    // PUBLIC_INTERFACE
    @Bean
    public OpenAPI notesOpenAPI() {
        /** Configure OpenAPI metadata and tags. */
        return new OpenAPI()
                .info(new Info()
                        .title("Notes API")
                        .description("REST API for managing personal notes")
                        .version("0.1.0")
                        .contact(new Contact().name("Notes Backend").email("noreply@example.com")))
                .addTagsItem(new Tag().name("Notes").description("Notes CRUD"))
                .externalDocs(new ExternalDocumentation()
                        .description("Swagger UI").url("/swagger-ui.html"));
    }
}
