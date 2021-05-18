package com.gamesys.services.rss.feed.processor.core;

import java.time.Instant;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * An element in the RSS feed.
 *
 * @author Dzmitry Rastsiusheuski
 * @since 17.05.2021
 */
@Value
@Builder
public class RssItem {

    /**
     * The title of the item.
     */
    @NonNull
    String title;

    /**
     * The item synopsis.
     */
    @NonNull
    String description;

    /**
     * Uniform resource identifier.
     */
    @NonNull
    String uri;

    /**
     * Indicates when the item was published.
     */
    @NonNull
    Instant publishedDate;

    /**
     * Indicates when the item was inserted in database.
     */
    @NonNull
    Instant insertedDate;
}
