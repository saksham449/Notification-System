package com.saksham.notification_service.service;

import com.saksham.notification_service.dto.UserRequest;
import com.saksham.notification_service.dto.UserResponse;
import com.saksham.notification_service.entity.User;
import com.saksham.notification_service.entity.UserPreference;
import com.saksham.notification_service.repository.UserPreferenceRepository;
import com.saksham.notification_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserPreferenceRepository userPreferenceRepository;

    public UserResponse createUser(UserRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException(
                    "User already exists with email: "
                            + request.getEmail()
            );
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .deviceToken(request.getDeviceToken())
                .build();

        User savedUser = userRepository.save(user);

        // Every new user gets all channels enabled by default.
        UserPreference preference = UserPreference.builder()
                .user(savedUser)
                .emailEnabled(true)
                .smsEnabled(true)
                .pushEnabled(true)
                .inAppEnabled(true)
                .build();

        userPreferenceRepository.save(preference);

        return mapToResponse(savedUser);
    }

    public UserResponse getUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new com.saksham.notification_service.exception
                                .UserNotFoundException(userId));

        return mapToResponse(user);
    }

    private UserResponse mapToResponse(User user) {

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .deviceToken(user.getDeviceToken())
                .createdAt(user.getCreatedAt())
                .build();
    }
}