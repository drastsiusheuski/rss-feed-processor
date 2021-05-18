package com.gamesys.services.rss.feed.processor.impl.service;

import java.util.List;
import java.util.stream.Collectors;

import com.gamesys.services.rss.feed.processor.core.RssItem;
import com.gamesys.services.rss.feed.processor.impl.dao.RssItemDao;
import com.gamesys.services.rss.feed.processor.impl.util.RssItemProcessor;
import com.gamesys.services.rss.feed.processor.reader.RssFeedReader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@RequiredArgsConstructor
public class RssFeedService {

    /**
     * Limit for the number of last rss items to fetch. By default rss feed is limited by 10 entries.
     */
    @Value("${rss.feed.limit: #{10}}")
    private int rssFeedLimit;

    private final RssFeedReader rssFeedReader;

    private final RssItemDao rssItemDao;

    /**
     * Process and save all read RSS items to the storage.
     */
    public void processRssFeed() {
        List<RssItem> rssItems = rssFeedReader.read().stream()
            .map(RssItemProcessor::process)
            .collect(Collectors.toList());

        rssItemDao.saveAll(rssItems);
    }

    /**
     * Get last N preconfigured rss items.
     *
     * @return a list of N last {@link RssItem}.
     */
    public List<RssItem> getLastRssItems() {
        return rssItemDao.findLast(rssFeedLimit);
    }
}
