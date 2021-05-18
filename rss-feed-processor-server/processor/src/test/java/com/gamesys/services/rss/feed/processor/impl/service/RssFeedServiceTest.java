package com.gamesys.services.rss.feed.processor.impl.service;

import java.time.Instant;
import java.util.List;

import com.gamesys.services.rss.feed.processor.core.RssItem;
import com.gamesys.services.rss.feed.processor.impl.dao.RssItemDao;
import com.gamesys.services.rss.feed.processor.reader.RssFeedReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link RssFeedService}.
 *
 * @author Dzmitry Rastsiusheuski
 * @since 17.05.2021
 */
@ExtendWith(MockitoExtension.class)
class RssFeedServiceTest {

    private static final int RSS_FEED_LIMIT = 10;

    @Mock
    private RssFeedReader rssFeedReader;

    @Mock
    private RssItemDao rssItemDao;

    @InjectMocks
    private RssFeedService rssFeedService;

    @Test
    void shouldGetLastRssItems() {
        List<RssItem> rssItems = List.of(
            RssItem.builder()
                .title("title")
                .description("description")
                .uri("uri")
                .publishedDate(Instant.now())
                .insertedDate(Instant.now())
                .build()
        );

        ReflectionTestUtils.setField(rssFeedService, "rssFeedLimit", RSS_FEED_LIMIT);

        when(rssItemDao.findLast(RSS_FEED_LIMIT)).thenReturn(rssItems);

        assertEquals(rssItems, rssFeedService.getLastRssItems());
    }

    @Test
    void shouldProcessRssFeed() {
        Instant insertedDate = Instant.now();
        Instant publishedDate = Instant.now();

        List<RssItem> rssItems = List.of(
            RssItem.builder()
                .title("title")
                .description("description")
                .uri("uri")
                .publishedDate(publishedDate)
                .insertedDate(insertedDate)
                .build()
        );
        List<RssItem> processedRssItems = List.of(
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

        verify(rssItemDao).saveAll(processedRssItems);
    }
}