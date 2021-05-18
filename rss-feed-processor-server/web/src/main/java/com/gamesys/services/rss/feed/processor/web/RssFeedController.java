package com.gamesys.services.rss.feed.processor.web;

import java.util.List;

import com.gamesys.services.rss.feed.processor.core.RssItem;
import com.gamesys.services.rss.feed.processor.impl.service.RssFeedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Controller that provides REST API for RSS feed.
 *
 * @author Dzmitry Rastsiusheuski
 * @since 17.05.2021
 */
@RestController
@RequestMapping(path = "/api/v1/feed")
@RequiredArgsConstructor
@Tag(name = "RSS Feed API")
public class RssFeedController {

    private final RssFeedService rssFeedService;

    /**
     * Http endpoint to get a list of latest RSS items.
     *
     * @return a list of {@link RssItem}
     */
    @GetMapping
    @Operation(description = "Get a list of latest RSS items",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "A list of RSS items",
                content = @Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = RssItem.class)))
            )
        }
    )
    public List<RssItem> getRssItems() {
        return rssFeedService.getLastRssItems();
    }
}
