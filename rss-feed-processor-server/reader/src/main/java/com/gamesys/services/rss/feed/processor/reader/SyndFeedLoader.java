package com.gamesys.services.rss.feed.processor.reader;

import java.io.IOException;
import java.io.Reader;
import java.net.URL;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

/**
 * RSS feed loader implementation backed by ROME framework.
 *
 * @author Dzmitry Rastsiusheuski
 * @since 17.05.2021
 */
public class SyndFeedLoader {

    /**
     * Load RSS feed from external source by URL.
     *
     * @param url rss feed url
     * @return {@link SyndFeed} populated with rss data
     */
    public SyndFeed loadSyndFeed(String url) {
        try (Reader reader = new XmlReader(new URL(url))) {
            return new SyndFeedInput().build(reader);
        } catch (IOException | FeedException e) {
            throw new RuntimeException("An error occurred while reading rss feed", e);
        }
    }
}
