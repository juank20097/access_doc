package com.project.accessDoc.utilities;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.ExternalDocumentation;

/**
 * Configuration class for OpenAPI documentation.
 * <p>
 * This class defines the configuration for the OpenAPI documentation
 * using the properties defined in the application properties file.
 * </p>
 */
@Configuration
public class OpenApiConfig {
    
    @Value("${name.api}")
    private String nameApi;

    @Value("${description.api}")
    private String descriptionApi;

    @Value("${dev.team.url.api}")
    private String devTeamUrl;

    @Value("${repo.url.api}")
    private String repoUrl;

    /**
     * Creates a custom OpenAPI bean for configuring API documentation.
     * 
     * @return an instance of {@link OpenAPI} with the specified API information
     *         such as title, version, description, and contact details.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title(this.nameApi)
                                 .version("1.0")
                                 .description(descriptionApi)
                                 .contact(new Contact().name("Development Team")
                                                       .url(devTeamUrl)))
                .externalDocs(new ExternalDocumentation().description("GitHub repository")
                                                         .url(repoUrl));
    }
}
