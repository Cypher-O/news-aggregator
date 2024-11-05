package com.synth.news_aggregator.service;

import com.synth.news_aggregator.dto.NewsApiArticle;
import com.synth.news_aggregator.dto.NewsApiResponse;
// import com.synth.news_aggregator.dto.NewsArticleDto;
import com.synth.news_aggregator.model.NewsArticle;
import com.synth.news_aggregator.repository.NewsArticleRepository;
import com.synth.news_aggregator.exception.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExternalNewsApiService {
    private final WebClient webClient;
    private final NewsArticleRepository newsArticleRepository;

    @Value("${news.api.key}")
    private String apiKey;

    @Value("${news.api.base-url}")
    private String baseUrl;

    public void fetchNewsFromExternalApi(String category) {
        log.info("Fetching news for category: {}", category);
        
        try {
            webClient.get()
                .uri(baseUrl + "/top-headlines?category={category}&apiKey={apiKey}",
                    Map.of(
                        "category", category,
                        "apiKey", apiKey
                    ))
                .retrieve()
                .bodyToMono(NewsApiResponse.class)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1))
                    .filter(throwable -> throwable instanceof TimeoutException))
                .timeout(Duration.ofSeconds(10))
                .subscribe(
                    response -> processNewsResponse(response, category),
                    error -> handleNewsApiFetchError(error, category)
                );
        } catch (Exception e) {
            log.error("Error fetching news for category: {}", category, e);
            throw new ApiException("Failed to fetch news from external API");
        }
    }

    private void processNewsResponse(NewsApiResponse response, String category) {
        if (response != null && response.getArticles() != null) {
            List<NewsArticle> articles = response.getArticles().stream()
                .map(article -> mapToNewsArticle(article, category))
                .toList();
            
            newsArticleRepository.saveAll(articles);
            log.info("Successfully saved {} articles for category: {}", articles.size(), category);
        }
    }

    private void handleNewsApiFetchError(Throwable error, String category) {
        log.error("Error fetching news for category: {}", category, error);
        // Implement notification service or alerting mechanism
    }

    private NewsArticle mapToNewsArticle(NewsApiArticle article, String category) {
        NewsArticle newsArticle = new NewsArticle();
        newsArticle.setTitle(article.getTitle());
        newsArticle.setDescription(article.getDescription());
        newsArticle.setUrl(article.getUrl());
        newsArticle.setImageUrl(article.getUrlToImage());
        newsArticle.setSource(article.getSource().getName());
        newsArticle.setPublishedAt(article.getPublishedAt());
        newsArticle.setCategory(category);
        return newsArticle;
    }
}