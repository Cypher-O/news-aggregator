// com/synth/news_aggregator/repository/UserRepository.java
package com.synth.news_aggregator.repository;

import com.synth.news_aggregator.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUsername(String username);
    
    Optional<User> findByEmail(String email);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
    
    @Query("SELECT u FROM User u WHERE u.email LIKE %:domain%")
    List<User> findByEmailDomain(@Param("domain") String domain);
    
    @Modifying
    @Query("UPDATE User u SET u.password = :newPassword WHERE u.id = :userId")
    void updatePassword(@Param("userId") Long userId, @Param("newPassword") String newPassword);
    
    @Query("SELECT u FROM User u JOIN FETCH u.preferences WHERE u.id = :userId")
    Optional<User> findByIdWithPreferences(@Param("userId") Long userId);
    
    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.username = :username AND u.id != :userId")
    boolean existsByUsernameAndIdNot(@Param("username") String username, @Param("userId") Long userId);
    
    List<User> findByPreferencesContaining(String preference);
    
    @Query(value = "SELECT * FROM users WHERE last_login_date > NOW() - INTERVAL '30 days'", 
           nativeQuery = true)
    List<User> findActiveUsers();
}
