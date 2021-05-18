package com.gamesys.services.rss.feed.processor.impl.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.gamesys.services.rss.feed.processor.core.RssItem;
import lombok.RequiredArgsConstructor;

/**
 * The class is used to hide implementation details of the underlying storage for RSS item. Defines basic operations to perform on {@link RssItem} object.
 *
 * @author Dzmitry Rastsiusheuski
 * @since 17.05.2021
 */
@RequiredArgsConstructor
public class RssItemDao {

    private static final String SELECT_LAST_RSS_ITEMS_SQL_STATEMENT = "SELECT * FROM rss_item ORDER BY published_date DESC LIMIT ?";
    private static final String INSERT_IGNORE_INTO_RSS_ITEM_SQL_STATEMENT =
        "INSERT IGNORE INTO rss_item(title, description, uri, published_date, inserted_date) VALUES (?, ?, ?, ?, ?)";

    private final DataSource dataSource;

    /**
     * Save all unique RSS items.
     *
     * @param rssItems items to save
     */
    public void saveAll(List<RssItem> rssItems) {
        try (var connection = dataSource.getConnection();
             var prepareStatement = connection.prepareStatement(INSERT_IGNORE_INTO_RSS_ITEM_SQL_STATEMENT)) {
            connection.setAutoCommit(false);

            for (RssItem rssItem : rssItems) {
                prepareStatement.setString(1, rssItem.getTitle());
                prepareStatement.setString(2, rssItem.getDescription());
                prepareStatement.setString(3, rssItem.getUri());
                prepareStatement.setTimestamp(4, Timestamp.from(rssItem.getPublishedDate()));
                prepareStatement.setTimestamp(5, Timestamp.from(rssItem.getInsertedDate()));
                prepareStatement.addBatch();
            }
            prepareStatement.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save a list of rss items", e);
        }
    }

    /**
     * Find last N RSS items limited by parameter {@literal limit}.
     *
     * @param limit number of last rss items to fetch
     * @return a list of {@link RssItem} that meets search criteria.
     */
    public List<RssItem> findLast(int limit) {
        try (var connection = dataSource.getConnection();
             var prepareStatement = connection.prepareStatement(SELECT_LAST_RSS_ITEMS_SQL_STATEMENT)) {

            prepareStatement.setInt(1, limit);

            List<RssItem> rssItems = new ArrayList<>();
            try (var resultSet = prepareStatement.executeQuery()) {
                while (resultSet.next()) {
                    rssItems.add(createRssItem(resultSet));
                }
            }
            return rssItems;
        } catch (SQLException e) {
            throw new RuntimeException(MessageFormat.format("Unable to find last {0} rss items", limit), e);
        }
    }

    private RssItem createRssItem(ResultSet resultSet) throws SQLException {
        return RssItem.builder()
            .title(resultSet.getString("title"))
            .description(resultSet.getString("description"))
            .uri(resultSet.getString("uri"))
            .publishedDate(resultSet.getTimestamp("published_date").toInstant())
            .insertedDate(resultSet.getTimestamp("inserted_date").toInstant())
            .build();
    }
}
