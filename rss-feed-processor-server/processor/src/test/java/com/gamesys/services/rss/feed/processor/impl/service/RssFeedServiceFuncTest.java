package com.gamesys.services.rss.feed.processor.impl.service;

import java.time.Instant;
import java.util.List;

import com.gamesys.services.rss.feed.processor.core.RssItem;
import com.gamesys.services.rss.feed.processor.reader.RssFeedReader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Functional test for {@link RssFeedService}.
 *
 * @author Dzmitry Rastsiusheuski
 * @since 18.05.2021
 */
@SpringBootTest
class RssFeedServiceFuncTest {

    @MockBean
    private RssFeedReader rssFeedReader;

    @Autowired
    private RssFeedService rssFeedService;

    @Test
    void shouldProcessRssFeed() {
        Instant publishedDate = Instant.now();
        Instant insertedDate = Instant.now();

        List<RssItem> rssItems = List.of(
            RssItem.builder()
                .title("title")
                .description("description")
                .uri("uri")
                .publishedDate(publishedDate)
                .insertedDate(insertedDate)
                .build()
        );

        List<RssItem> expectedRssItems = List.of(
            RssItem.builder()
                .title("TITLE")
                .description("noitpircsed")
                .uri("uri")
                .publishedDate(publishedDate)
                .insertedDate(insertedDate)
                .build()
        );

        when(rssFeedReader.read()).thenReturn(rssItems);

        rssFeedService.processRssFeed();

        assertEquals(expectedRssItems, rssFeedService.getLastRssItems());
    }

    @Configuration
    @EnableAutoConfiguration
    public static class TestConfiguration {

    }
}
