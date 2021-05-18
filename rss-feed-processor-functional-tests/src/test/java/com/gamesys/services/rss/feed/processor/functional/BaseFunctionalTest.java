package com.gamesys.services.rss.feed.processor.functional;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

/**
 * Base functional test.
 *
 * @author Dzmitry Rastsiusheuski
 * @since 18.05.2021
 */
@SpringBootTest(
    properties = {
        "rss.feed.scheduling.enabled=false"
    },
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public abstract class BaseFunctionalTest {

    @LocalServerPort
    protected int serverPort;

    @BeforeEach
    public void setUp() {
        RestAssured.port = serverPort;
    }
}
