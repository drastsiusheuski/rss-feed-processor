package com.gamesys.services.rss.feed.processor.impl.util;

import com.gamesys.services.rss.feed.processor.core.RssItem;
import lombok.experimental.UtilityClass;

/**
 * A utility class that is used to modify fields of {@link RssItem}.
 *
 * @author Dzmitry Rastsiusheuski
 * @since 17.05.2021
 */
@UtilityClass
public class RssItemProcessor {

    private static final int TITLE_LENGTH = 100;
    private static final int DESCRIPTION_LENGTH = 1000;
    private static final int URI_LENGTH = 500;

    /**
     * Utility method to process fields of {@link RssItem}. Title, description, uri fields are trimmed to required size.
     * Converts all title field characters to uppercase format. Description field is reversed.
     *
     * @param rssItem rss item to process
     * @return processed rss item
     */
    public static RssItem process(RssItem rssItem) {
        return RssItem.builder()
            .title(processTitle(rssItem.getTitle()))
            .description(processDescription(rssItem.getDescription()))
            .uri(trimToSize(rssItem.getUri(), URI_LENGTH))
            .publishedDate(rssItem.getPublishedDate())
            .insertedDate(rssItem.getInsertedDate())
            .build();
    }

    private static String processTitle(String title) {
        return trimToSize(title.toUpperCase(), TITLE_LENGTH);
    }

    private static String processDescription(String description) {
        return trimToSize(new StringBuilder(description).reverse().toString(), DESCRIPTION_LENGTH);
    }

    private static String trimToSize(String input, int size) {
        return input.substring(0, Math.min(input.length(), size));
    }
}
