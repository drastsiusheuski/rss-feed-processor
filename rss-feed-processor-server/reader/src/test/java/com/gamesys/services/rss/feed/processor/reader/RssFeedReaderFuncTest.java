package com.gamesys.services.rss.feed.processor.reader;

import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Instant;
import java.util.List;

import com.gamesys.services.rss.feed.processor.core.RssItem;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Configuration;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * Functional tests for {@link RssFeedReader}.
 *
 * @author Dzmitry Rastsiusheuski
 * @since 18.05.2021
 */
@SpringBootTest(properties = "rss.feed.url=http://localhost:${wiremock.server.port}")
@AutoConfigureWireMock(port = 0)
class RssFeedReaderFuncTest {

    @Autowired
    private RssFeedReader rssFeedReader;

    @MockBean
    private Clock clock;

    @Test
    @SneakyThrows
    void shouldReadRssItems() {
        Instant insertedDate = Instant.now();

        SimpleDateFormat publishedDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");

        List<RssItem> rssItems = List.of(
            RssItem.builder()
                .title("Conflict Enters Second Week With Heavy Israeli Strikes and Rocket Fire from Gaza")
                .description("Battles were waged in the skies above Israel and the ground below Gaza City")
                .uri("https://www.nytimes.com/live/2021/05/17/world/israel-gaza-updates/")
                .publishedDate(publishedDateFormat.parse("Mon, 17 May 2021 20:14:25 +0000").toInstant())
                .insertedDate(insertedDate)
                .build()
        );

        when(clock.instant()).thenReturn(insertedDate);

        stubFor(get("/")
            .willReturn(ok().withBodyFile("rss_feed.xml")));

        assertEquals(rssItems, rssFeedReader.read());
    }

    @Configuration
    @EnableAutoConfiguration
    public static class TestConfiguration {

    }
}
