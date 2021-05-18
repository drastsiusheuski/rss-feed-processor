package com.gamesys.services.rss.feed.processor.web;

import com.gamesys.services.rss.feed.processor.impl.service.RssFeedService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

/**
 * Rss feed web functional test.
 * 
 * @author Dzmitry Rastsiusheuski
 * @since 18.05.2021
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RssFeedWebFuncTest {

    @Autowired
    private RssFeedService rssFeedService;

    @LocalServerPort
    private int port;

    @Test
    void shouldGetRssItems() {
        given().port(port)
            .get("/api/v1/feed")
            .then()
            .log()
            .all()
            .statusCode(SC_OK)
            .extract()
            .response()
            .then()
            .assertThat()
            .body("$", hasSize(1))
            .body("[0].title", is("title"))
            .body("[0].description", is("description"))
            .body("[0].uri", is("uri"))
            .body("[0].publishedDate", is("2008-11-09T17:45:21Z"))
            .body("[0].insertedDate", is("2008-11-11T15:23:44Z"));
    }
}
