package com.gamesys.services.rss.feed.processor.impl.configuration;

import javax.sql.DataSource;

import com.gamesys.services.rss.feed.processor.impl.dao.RssItemDao;
import com.gamesys.services.rss.feed.processor.impl.service.RssFeedService;
import com.gamesys.services.rss.feed.processor.reader.RssFeedReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * An auto configuration of rss feed processor.
 *
 * @author Dzmitry Rastsiusheuski
 * @since 17.05.2021
 */
@Configuration
public class RssFeedProcessorAutoConfig {

    @Bean
    public RssItemDao rssItemDao(DataSource dataSource) {
        return new RssItemDao(dataSource);
    }

    @Bean
    public RssFeedService rssFeedService(RssFeedReader rssFeedReader, RssItemDao rssItemDao) {
        return new RssFeedService(rssFeedReader, rssItemDao);
    }
}
