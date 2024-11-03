package com.synth.news_aggregator.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPreferenceDto {
    private Long id;
    @NotNull
    private String category;
    private boolean notificationEnabled;
    private Integer refreshFrequencyInHours;
    private boolean emailDigestEnabled;
    private String keywords;
    private LocalDateTime lastNotificationSent;
}
