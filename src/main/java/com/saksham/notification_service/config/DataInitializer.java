package com.saksham.notification_service.config;

import com.saksham.notification_service.entity.User;
import com.saksham.notification_service.entity.UserPreference;
import com.saksham.notification_service.repository.UserPreferenceRepository;
import com.saksham.notification_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final UserPreferenceRepository userPreferenceRepository;

    @Override
    public void run(String... args) {

        if (userRepository.existsByEmail("test@example.com")) {
            return;
        }

        User user = User.builder()
                .name("Test User")
                .email("test@example.com")
                .phone("9876543210")
                .deviceToken("test-device-token")
                .build();

        user = userRepository.save(user);

        UserPreference preference = UserPreference.builder()
                .user(user)
                .emailEnabled(true)
                .smsEnabled(false)
                .pushEnabled(true)
                .inAppEnabled(true)
                .build();

        userPreferenceRepository.save(preference);

        System.out.println("Development test user created.");
    }
}