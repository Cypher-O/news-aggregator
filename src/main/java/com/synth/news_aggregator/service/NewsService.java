// com/synth/news_aggregator/service/NewsService.java
package com.synth.news_aggregator.service;

import com.synth.news_aggregator.model.NewsArticle;
import com.synth.news_aggregator.repository.NewsArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {
    private final NewsArticleRepository newsArticleRepository;
    private final RestTemplate restTemplate;

    @Cacheable("news")
    public List<NewsArticle> getNewsByCategory(String category) {
        return newsArticleRepository.findByCategory(category);
    }

    @Scheduled(fixedRate = 3600000) // Fetch news every hour
    public void fetchNewsFromExternalApi() {
        // Implementation to fetch news from external API
        // Save to repository
    }
}
