// com/synth/news_aggregator/repository/NewsArticleRepository.java
package com.synth.news_aggregator.repository;

import com.synth.news_aggregator.model.NewsArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface NewsArticleRepository extends JpaRepository<NewsArticle, Long> {
    
    List<NewsArticle> findByCategory(String category);
    
    List<NewsArticle> findByCategoryAndPublishedAtAfter(String category, LocalDateTime date);
    
    @Query("SELECT n FROM NewsArticle n WHERE n.category = :category ORDER BY n.publishedAt DESC")
    List<NewsArticle> findLatestNewsByCategory(@Param("category") String category);
    
    @Query(value = "SELECT DISTINCT category FROM news_articles", nativeQuery = true)
    List<String> findAllCategories();
    
    List<NewsArticle> findByTitleContainingIgnoreCase(String keyword);
    
    @Query("SELECT n FROM NewsArticle n WHERE n.category IN :categories AND n.publishedAt > :date")
    List<NewsArticle> findNewsByUserPreferences(
        @Param("categories") List<String> categories,
        @Param("date") LocalDateTime date
    );
    
    void deleteByPublishedAtBefore(LocalDateTime date);
    
    Optional<NewsArticle> findByUrlAndCategory(String url, String category);
    
    @Query(value = "SELECT * FROM news_articles WHERE category = :category " +
           "ORDER BY published_at DESC LIMIT :limit", nativeQuery = true)
    List<NewsArticle> findTopNewsByCategory(
        @Param("category") String category,
        @Param("limit") int limit
    );
}