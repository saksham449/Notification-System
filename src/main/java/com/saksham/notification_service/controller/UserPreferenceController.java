package com.saksham.notification_service.controller;

import com.saksham.notification_service.dto.UpdateUserPreferenceRequest;
import com.saksham.notification_service.dto.UserPreferenceResponse;
import com.saksham.notification_service.entity.UserPreference;
import com.saksham.notification_service.repository.UserPreferenceRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users/{userId}/preferences")
@RequiredArgsConstructor
public class UserPreferenceController {

    private final UserPreferenceRepository userPreferenceRepository;

    @GetMapping
    public ResponseEntity<UserPreferenceResponse> getPreferences(
            @PathVariable Long userId) {

        UserPreference preference =
                userPreferenceRepository.findByUserId(userId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Preferences not found for user with id: "
                                                + userId
                                )
                        );

        UserPreferenceResponse response =
                UserPreferenceResponse.builder()
                        .userId(userId)
                        .emailEnabled(preference.isEmailEnabled())
                        .smsEnabled(preference.isSmsEnabled())
                        .pushEnabled(preference.isPushEnabled())
                        .inAppEnabled(preference.isInAppEnabled())
                        .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<UserPreferenceResponse> updatePreferences(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateUserPreferenceRequest request) {

        UserPreference preference =
                userPreferenceRepository.findByUserId(userId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Preferences not found for user with id: "
                                                + userId
                                )
                        );

        preference.setEmailEnabled(request.getEmailEnabled());
        preference.setSmsEnabled(request.getSmsEnabled());
        preference.setPushEnabled(request.getPushEnabled());
        preference.setInAppEnabled(request.getInAppEnabled());

        userPreferenceRepository.save(preference);

        UserPreferenceResponse response =
                UserPreferenceResponse.builder()
                        .userId(userId)
                        .emailEnabled(preference.isEmailEnabled())
                        .smsEnabled(preference.isSmsEnabled())
                        .pushEnabled(preference.isPushEnabled())
                        .inAppEnabled(preference.isInAppEnabled())
                        .build();

        return ResponseEntity.ok(response);
    }
}
