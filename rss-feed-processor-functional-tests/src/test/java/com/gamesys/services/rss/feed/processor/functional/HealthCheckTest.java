package com.gamesys.services.rss.feed.processor.functional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.autoconfigure.web.server.LocalManagementPort;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;

/**
 * Application health check test.
 *
 * @author Dzmitry Rastsiusheuski
 * @since 18.05.2021
 */
class HealthCheckTest extends BaseFunctionalTest {

    @LocalManagementPort
    protected int managementPort;

    @Test
    void shouldCheckHealth() {
        given().port(managementPort)
            .when().get("/actuator/health")
            .then()
            .log()
            .all()
            .statusCode(SC_OK)
            .body("status", Matchers.is("UP"));
    }
}
