package com.gamesys.services.rss.feed.processor.scheduler.configuration;

import com.gamesys.services.rss.feed.processor.impl.service.RssFeedService;
import com.gamesys.services.rss.feed.processor.scheduler.RssFeedScheduler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * An auto configuration of rss feed scheduler.
 *
 * @author Dzmitry Rastsiusheuski
 * @since 17.05.2021
 */
@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "rss.feed.scheduling.enabled", havingValue = "true", matchIfMissing = true)
public class RssFeedSchedulerAutoConfig {

    @Bean
    public RssFeedScheduler rssFeedScheduler(RssFeedService rssFeedService) {
        return new RssFeedScheduler(rssFeedService);
    }
}
