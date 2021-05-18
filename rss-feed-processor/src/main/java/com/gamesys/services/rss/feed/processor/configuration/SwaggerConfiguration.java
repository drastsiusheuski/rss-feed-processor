package com.gamesys.services.rss.feed.processor.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger configuration.
 *
 * @author Dzmitry Rastsiusheuski
 * @since 17.05.2021
 */
@Configuration
@RequiredArgsConstructor
public class SwaggerConfiguration {

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
            .group("Default")
            .packagesToScan("com.gamesys.services.rss.feed.processor.web")
            .pathsToMatch("/**")
            .build();
    }

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
            .info(new Info().title("RSS Feed HTTP API"));
    }
}
