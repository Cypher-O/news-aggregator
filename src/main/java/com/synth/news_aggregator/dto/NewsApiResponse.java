package com.synth.news_aggregator.dto;

import java.util.List;

public class NewsApiResponse {
    private String status;
    private int totalResults;
    private List<NewsApiArticle> articles;

    // Getters and setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<NewsApiArticle> getArticles() {
        return articles;
    }

    public void setArticles(List<NewsApiArticle> articles) {
        this.articles = articles;
    }
}
