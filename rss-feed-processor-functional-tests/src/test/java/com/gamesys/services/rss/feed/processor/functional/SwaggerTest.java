package com.gamesys.services.rss.feed.processor.functional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.when;
import static org.apache.http.HttpStatus.SC_OK;

/**
 * Swagger test.
 *
 * @author Dzmitry Rastsiusheuski
 * @since 18.05.2021
 */
class SwaggerTest extends BaseFunctionalTest {

    @Test
    void shouldExposeSwaggerUi() {
        when().get("/swagger-ui.html")
            .then()
            .statusCode(SC_OK);
    }

    @Test
    void shouldExposeSwaggerApi() {
        when().get("/v3/api-docs")
            .then()
            .statusCode(SC_OK)
            .body("info.title", Matchers.is("RSS Feed HTTP API"));
    }
}
