
// com/synth/news_aggregator/model/NewsArticle.java
package com.synth.news_aggregator.model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "news_articles")
public class NewsArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String url;
    private String imageUrl;
    private String source;
    private LocalDateTime publishedAt;
    private String category;
}
