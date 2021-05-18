package com.gamesys.services.rss.feed.processor.scheduler;

import com.gamesys.services.rss.feed.processor.impl.service.RssFeedService;
import com.gamesys.services.rss.feed.processor.reader.RssFeedReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link RssFeedScheduler}.
 *
 * @author Dzmitry Rastsiusheuski
 * @since 18.05.2021
 */
@ExtendWith(MockitoExtension.class)
class RssFeedSchedulerTest {

    @Mock
    private RssFeedService rssFeedService;

    @InjectMocks
    private RssFeedScheduler rssFeedScheduler;

    @Test
    void shouldProcessRssFeed() {
        rssFeedScheduler.processRssFeed();

        verify(rssFeedService).processRssFeed();
    }

    @Test
    void shouldCatchRuntimeExceptionWhenProcessRssFeed() {
        doThrow(RuntimeException.class).when(rssFeedService).processRssFeed();

        assertDoesNotThrow(() -> rssFeedScheduler.processRssFeed());
    }
}
