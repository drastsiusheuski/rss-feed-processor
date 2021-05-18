package com.gamesys.services.rss.feed.processor.impl.util;

import java.time.Instant;

import com.gamesys.services.rss.feed.processor.core.RssItem;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link RssItemProcessor}.
 *
 * @author Dzmitry Rastsiusheuski
 * @since 17.05.2021
 */
class RssItemProcessorTest {

    @Test
    void shouldProcessRssItem() {
        Instant insertedDate = Instant.now();
        Instant publishedDate = Instant.now();

        RssItem rssItem = RssItem.builder()
            .title(StringUtils.rightPad("title", 105, "*"))
            .description(StringUtils.rightPad("description", 1005, "*"))
            .uri(StringUtils.rightPad("uri", 505, "*"))
            .insertedDate(insertedDate)
            .publishedDate(publishedDate)
            .build();

        RssItem expectedRssItem = RssItem.builder()
            .title(StringUtils.rightPad("TITLE", 100, "*"))
            .description(StringUtils.leftPad("noitpi", 1000, "*"))
            .uri(StringUtils.rightPad("uri", 500, "*"))
            .insertedDate(insertedDate)
            .publishedDate(publishedDate)
            .build();

        assertEquals(expectedRssItem, RssItemProcessor.process(rssItem));
    }
}