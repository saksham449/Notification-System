package com.saksham.notification_service.exception;

public class UserPreferenceNotFoundException extends RuntimeException {

    public UserPreferenceNotFoundException(Long userId) {
        super("Preferences not found for user with id: " + userId);
    }
}