// com/example/news_aggregator/model/UserPreference.java
package com.synth.news_aggregator.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_preferences",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "category"})
    })
public class UserPreference {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private boolean notificationEnabled = true;

    @Column(name = "refresh_frequency")
    private Integer refreshFrequencyInHours = 24;

    @Column(name = "email_digest_enabled")
    private boolean emailDigestEnabled = false;

    @Column(name = "keywords")
    private String keywords;

    @Column(name = "last_notification_sent")
    private LocalDateTime lastNotificationSent;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

