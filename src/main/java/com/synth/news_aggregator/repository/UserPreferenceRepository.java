// com/synth/news_aggregator/repository/UserPreferenceRepository.java
package com.synth.news_aggregator.repository;

import com.synth.news_aggregator.model.UserPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserPreferenceRepository extends JpaRepository<UserPreference, Long> {
    
    List<UserPreference> findByUserId(Long userId);
    
    Optional<UserPreference> findByUserIdAndCategory(Long userId, String category);
    
    @Query("SELECT up.category FROM UserPreference up WHERE up.userId = :userId")
    List<String> findCategoriesByUserId(@Param("userId") Long userId);
    
    @Modifying
    @Query("DELETE FROM UserPreference up WHERE up.userId = :userId AND up.category = :category")
    void deleteByUserIdAndCategory(@Param("userId") Long userId, @Param("category") String category);
    
    @Query("SELECT up FROM UserPreference up WHERE up.userId = :userId AND up.category IN :categories")
    List<UserPreference> findByUserIdAndCategories(
        @Param("userId") Long userId,
        @Param("categories") List<String> categories
    );
    
    @Query(value = "SELECT category, COUNT(*) as count FROM user_preferences " +
           "GROUP BY category ORDER BY count DESC", nativeQuery = true)
    List<Object[]> findMostPopularCategories();
    
    boolean existsByUserIdAndCategory(Long userId, String category);
    
    @Modifying
    @Query("UPDATE UserPreference up SET up.notificationEnabled = :enabled " +
           "WHERE up.userId = :userId AND up.category = :category")
    void updateNotificationSettings(
        @Param("userId") Long userId,
        @Param("category") String category,
        @Param("enabled") boolean enabled
    );
    
    @Query("SELECT DISTINCT up.userId FROM UserPreference up " +
           "WHERE up.category = :category AND up.notificationEnabled = true")
    List<Long> findUserIdsForCategoryNotifications(@Param("category") String category);
}