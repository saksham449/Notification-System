package com.saksham.notification_service.service;

import com.saksham.notification_service.dto.UpdateUserPreferenceRequest;
import com.saksham.notification_service.dto.UserPreferenceResponse;
import com.saksham.notification_service.entity.UserPreference;
import com.saksham.notification_service.exception.UserPreferenceNotFoundException;
import com.saksham.notification_service.repository.UserPreferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserPreferenceService {

    private final UserPreferenceRepository userPreferenceRepository;

    public UserPreferenceResponse getPreferences(Long userId) {

        UserPreference preference =
                userPreferenceRepository.findByUserId(userId)
                        .orElseThrow(() ->
                                new UserPreferenceNotFoundException(userId));

        return mapToResponse(preference);
    }

    public UserPreferenceResponse updatePreferences(
            Long userId,
            UpdateUserPreferenceRequest request) {

        UserPreference preference =
                userPreferenceRepository.findByUserId(userId)
                        .orElseThrow(() ->
                                new UserPreferenceNotFoundException(userId));

        preference.setEmailEnabled(request.getEmailEnabled());
        preference.setSmsEnabled(request.getSmsEnabled());
        preference.setPushEnabled(request.getPushEnabled());
        preference.setInAppEnabled(request.getInAppEnabled());

        UserPreference saved =
                userPreferenceRepository.save(preference);

        return mapToResponse(saved);
    }

    private UserPreferenceResponse mapToResponse(
            UserPreference preference) {

        return UserPreferenceResponse.builder()
                .userId(preference.getUser().getId())
                .emailEnabled(preference.isEmailEnabled())
                .smsEnabled(preference.isSmsEnabled())
                .pushEnabled(preference.isPushEnabled())
                .inAppEnabled(preference.isInAppEnabled())
                .build();
    }
}