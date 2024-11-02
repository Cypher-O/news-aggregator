// Dockerfile
FROM openjdk:17-slim
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]

// Additional Features and Improvements:

// com/example/newsaggregator/service/NewsService.java - Enhanced caching
@Service
@Slf4j
@RequiredArgsConstructor
public class NewsService {
    private final NewsArticleRepository newsArticleRepository;
    private final ExternalNewsApiService externalNewsApiService;
    
    @Cacheable(value = "news", key = "#category")
    public List<NewsArticle> getNewsByCategory(String category) {
        return newsArticleRepository.findByCategory(category);
    }

    @Scheduled(cron = "0 0 */3 * * *") // Every 3 hours
    @CacheEvict(value = "news", allEntries = true)
    public void refreshNews() {
        log.info("Starting scheduled news refresh");
        List<String> categories = List.of("business", "technology", "sports", "science");
        categories.forEach(category -> {
            try {
                externalNewsApiService.fetchNewsFromExternalApi(category);
            } catch (Exception e) {
                log.error("Error refreshing news for category: {}", category, e);
            }
        });
    }
}