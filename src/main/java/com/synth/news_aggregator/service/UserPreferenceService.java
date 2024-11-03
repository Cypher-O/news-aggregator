// com/synth/news_aggregator/service/UserPreferenceService.java

package com.synth.news_aggregator.service;

import com.synth.news_aggregator.dto.UserPreferenceDto;
import com.synth.news_aggregator.model.User;
import com.synth.news_aggregator.model.UserPreference;
import com.synth.news_aggregator.repository.UserPreferenceRepository;
import com.synth.news_aggregator.repository.UserRepository;
import com.synth.news_aggregator.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserPreferenceService {
    private final UserPreferenceRepository preferenceRepository;
    private final UserRepository userRepository;

    @Transactional
    public UserPreferenceDto addUserPreference(Long userId, UserPreferenceDto preferenceDto) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (preferenceRepository.existsByUserIdAndCategory(userId, preferenceDto.getCategory())) {
            throw new IllegalArgumentException("Preference already exists for this category");
        }

        UserPreference preference = UserPreference.builder()
            .user(user)
            .category(preferenceDto.getCategory())
            .notificationEnabled(preferenceDto.isNotificationEnabled())
            .refreshFrequencyInHours(preferenceDto.getRefreshFrequencyInHours())
            .emailDigestEnabled(preferenceDto.isEmailDigestEnabled())
            .keywords(preferenceDto.getKeywords())
            .build();

        UserPreference savedPreference = preferenceRepository.save(preference);
        return mapToDto(savedPreference);
    }

    @Transactional(readOnly = true)
    public List<UserPreferenceDto> getUserPreferences(Long userId) {
        return preferenceRepository.findByUserId(userId).stream()
            .map(this::mapToDto)
            .collect(Collectors.toList());
    }

    private UserPreferenceDto mapToDto(UserPreference preference) {
        return UserPreferenceDto.builder()
            .id(preference.getId())
            .category(preference.getCategory())
            .notificationEnabled(preference.isNotificationEnabled())
            .refreshFrequencyInHours(preference.getRefreshFrequencyInHours())
            .emailDigestEnabled(preference.isEmailDigestEnabled())
            .keywords(preference.getKeywords())
            .lastNotificationSent(preference.getLastNotificationSent())
            .build();
    }
}