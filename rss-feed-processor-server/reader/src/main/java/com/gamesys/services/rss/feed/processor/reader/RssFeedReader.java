package com.gamesys.services.rss.feed.processor.reader;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.gamesys.services.rss.feed.processor.core.RssItem;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

/**
 * Rss feed HTTP reader.
 *
 * @author Dzmitry Rastsiusheuski
 * @since 17.05.2021
 */
@Slf4j
@RequiredArgsConstructor
public class RssFeedReader {

    private final SyndFeedLoader syndFeedLoader;

    private final Clock clock;

    @Value("${rss.feed.url}")
    private String rssFeedUrl;

    /**
     * Load RSS feed entries from external source and convert them to a list of {@link RssItem}.
     *
     * @return a list of {@link RssItem}
     */
    public List<RssItem> read() {
        SyndFeed syndFeed = syndFeedLoader.loadSyndFeed(rssFeedUrl);

        return syndFeed.getEntries()
            .stream()
            .map(this::createRssItem)
            .collect(Collectors.toList());
    }

    private RssItem createRssItem(SyndEntry syndEntry) {
        return RssItem.builder()
            .title(syndEntry.getTitle())
            .description(syndEntry.getDescription().getValue())
            .uri(syndEntry.getUri())
            .publishedDate(syndEntry.getPublishedDate().toInstant())
            .insertedDate(clock.instant())
            .build();
    }
}
