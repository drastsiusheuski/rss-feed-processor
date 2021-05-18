package com.gamesys.services.rss.feed.processor.reader;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import com.gamesys.services.rss.feed.processor.core.RssItem;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndContentImpl;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndEntryImpl;
import com.rometools.rome.feed.synd.SyndFeed;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link RssFeedReader}.
 *
 * @author Dzmitry Rastsiusheuski
 * @since 17.05.2021
 */
@ExtendWith(MockitoExtension.class)
class RssFeedReaderTest {

    @Mock
    private Clock clock;

    @Mock
    private SyndFeed syndFeed;

    @Mock
    private SyndFeedLoader syndFeedLoader;

    @InjectMocks
    private RssFeedReader rssFeedReader;

    @Test
    void shouldReadRssFeed() {
        String rssFeedUrl = "url";

        Instant insertedDate = Instant.now();

        SyndContent descriptionSyndContent = new SyndContentImpl();
        descriptionSyndContent.setValue("description");

        Date publishedDate = new Date();

        SyndEntry syndEntry = new SyndEntryImpl();
        syndEntry.setTitle("title");
        syndEntry.setDescription(descriptionSyndContent);
        syndEntry.setUri("uri");
        syndEntry.setPublishedDate(publishedDate);

        RssItem rssItem = RssItem.builder()
            .title("title")
            .description("description")
            .uri("uri")
            .publishedDate(publishedDate.toInstant())
            .insertedDate(insertedDate)
            .build();

        ReflectionTestUtils.setField(rssFeedReader, "rssFeedUrl", rssFeedUrl);

        when(syndFeedLoader.loadSyndFeed(rssFeedUrl)).thenReturn(syndFeed);

        when(syndFeed.getEntries()).thenReturn(List.of(syndEntry));

        when(clock.instant()).thenReturn(insertedDate);

        assertEquals(List.of(rssItem), rssFeedReader.read());
    }
}