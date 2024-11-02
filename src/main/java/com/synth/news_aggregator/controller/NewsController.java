// com/synth/newsaggregator/controller/NewsController.java
package com.synth.news_aggregator.controller;

import com.synth.news_aggregator.dto.ApiResponse;
import com.synth.news_aggregator.model.NewsArticle;
import com.synth.news_aggregator.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;

    @GetMapping("/category/{category}")
    public ApiResponse<List<NewsArticle>> getNewsByCategory(@PathVariable String category) {
        List<NewsArticle> news = newsService.getNewsByCategory(category);
        return ApiResponse.success("News retrieved successfully", news);
    }
}