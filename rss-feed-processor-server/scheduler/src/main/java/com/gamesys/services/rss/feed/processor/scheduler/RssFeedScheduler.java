package com.gamesys.services.rss.feed.processor.scheduler;

import com.gamesys.services.rss.feed.processor.impl.service.RssFeedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * A scheduler that runs rss feed processing task on a given schedule.
 *
 * @author Dzmitry Rastsiusheuski
 * @since 17.05.2021
 */
@Slf4j
@RequiredArgsConstructor
public class RssFeedScheduler {

    private final RssFeedService rssFeedService;

    /**
     * Scheduled task that executed at preconfigured interval time.
     */
    @Scheduled(fixedDelayString = "${rss.feed.scheduling.delay.fixed:10000}", initialDelayString = "${rss.feed.scheduling.delay.initial:1000}")
    public void processRssFeed() {
        try {
            rssFeedService.processRssFeed();
        } catch (RuntimeException e) {
            log.error("Failed to process rss feed", e);
        }
    }
}
