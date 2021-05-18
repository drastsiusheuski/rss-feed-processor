package com.gamesys.services.rss.feed.processor.scheduler;

import com.gamesys.services.rss.feed.processor.impl.service.RssFeedService;
import org.awaitility.Duration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Configuration;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Functional test for {@link RssFeedScheduler}.
 *
 * @author Dzmitry Rastsiusheuski
 * @since 18.05.2021
 */
@SpringBootTest(properties = {
    "rss.feed.url=test_url",
    "rss.feed.scheduling.delay.fixed=1500"
})
class RssFeedSchedulerFuncTest {

    @SpyBean
    private RssFeedScheduler rssFeedScheduler;

    @MockBean
    private RssFeedService rssFeedService;

    @Test
    void shouldRunProcessRssFeed() {
        await().atMost(Duration.FIVE_SECONDS)
            .untilAsserted(() -> verify(rssFeedScheduler, times(3)).processRssFeed());
    }

    @Configuration
    @EnableAutoConfiguration
    public static class TestConfiguration {

    }
}