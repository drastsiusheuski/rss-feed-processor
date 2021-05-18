package com.gamesys.services.rss.feed.processor.reader.configuration;

import java.time.Clock;

import com.gamesys.services.rss.feed.processor.reader.RssFeedReader;
import com.gamesys.services.rss.feed.processor.reader.SyndFeedLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * An auto configuration of rss feed reader.
 *
 * @author Dzmitry Rastsiusheuski
 * @since 17.05.2021
 */
@Configuration
public class RssFeedReaderAutoConfig {

    @Bean
    public RssFeedReader rssFeedReader(SyndFeedLoader syndFeedLoader, Clock clock) {
        return new RssFeedReader(syndFeedLoader, clock);
    }

    @Bean
    public SyndFeedLoader syndFeedLoader() {
        return new SyndFeedLoader();
    }

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
