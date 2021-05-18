package com.gamesys.services.rss.feed.processor.reader;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link SyndFeedLoader}.
 *
 * @author Dzmitry Rastsiusheuski
 * @since 17.05.2021
 */
class SyndFeedLoaderTest {

    @Test
    @SneakyThrows
    void shouldLoadSyndFeed() {
        Date publishedDate = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").parse("Mon, 17 May 2021 20:14:25 +0000");

        SyndFeedLoader syndFeedLoader = new SyndFeedLoader();
        SyndFeed syndFeed = syndFeedLoader.loadSyndFeed(getClass().getResource("/__files/rss_feed.xml").toExternalForm());
        List<SyndEntry> entries = syndFeed.getEntries();
        SyndEntry syndEntry = entries.get(0);

        assertEquals("Conflict Enters Second Week With Heavy Israeli Strikes and Rocket Fire from Gaza", syndEntry.getTitle());
        assertEquals("Battles were waged in the skies above Israel and the ground below Gaza City", syndEntry.getDescription().getValue());
        assertEquals("https://www.nytimes.com/live/2021/05/17/world/israel-gaza-updates/", syndEntry.getUri());
        assertEquals(publishedDate, syndEntry.getPublishedDate());
    }

    @Test
    @SneakyThrows
    void shouldThrowRuntimeExceptionWhenLoadSyndFeedIfFeedExceptionThrown() {
        SyndFeedLoader syndFeedLoader = new SyndFeedLoader();

        String url = getClass().getResource("/__files/malformed_rss_feed.xml").toExternalForm();

        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> syndFeedLoader.loadSyndFeed(url));

        assertTrue(runtimeException.getCause() instanceof FeedException);
    }

    @Test
    void shouldThrowRuntimeExceptionWhenLoadSyndFeedIfIOExceptionThrown() {
        SyndFeedLoader syndFeedLoader = new SyndFeedLoader();

        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> syndFeedLoader.loadSyndFeed("http"));

        assertTrue(runtimeException.getCause() instanceof IOException);
    }
}