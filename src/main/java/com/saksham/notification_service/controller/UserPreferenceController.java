package com.saksham.notification_service.controller;

import com.saksham.notification_service.dto.UpdateUserPreferenceRequest;
import com.saksham.notification_service.dto.UserPreferenceResponse;
import com.saksham.notification_service.service.UserPreferenceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users/{userId}/preferences")
@RequiredArgsConstructor
public class UserPreferenceController {

    private final UserPreferenceService userPreferenceService;

    @GetMapping
    public ResponseEntity<UserPreferenceResponse> getPreferences(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                userPreferenceService.getPreferences(userId)
        );
    }

    @PutMapping
    public ResponseEntity<UserPreferenceResponse> updatePreferences(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateUserPreferenceRequest request) {

        return ResponseEntity.ok(
                userPreferenceService.updatePreferences(
                        userId,
                        request
                )
        );
    }
}