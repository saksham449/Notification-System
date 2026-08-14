package com.saksham.notification_service.service;

import com.saksham.notification_service.dto.NotificationRequest;
import com.saksham.notification_service.dto.NotificationResponse;
import com.saksham.notification_service.dto.NotificationResult;
import com.saksham.notification_service.entity.User;
import com.saksham.notification_service.entity.UserPreference;
import com.saksham.notification_service.enums.DeliveryStatus;
import com.saksham.notification_service.enums.NotificationChannel;
import com.saksham.notification_service.exception.UserNotFoundException;
import com.saksham.notification_service.exception.UserPreferenceNotFoundException;
import com.saksham.notification_service.provider.NotificationProvider;
import com.saksham.notification_service.repository.NotificationHistoryRepository;
import com.saksham.notification_service.repository.UserPreferenceRepository;
import com.saksham.notification_service.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserPreferenceRepository userPreferenceRepository;

    @Mock
    private NotificationHistoryRepository notificationHistoryRepository;

    @Mock
    private NotificationProvider emailProvider;

    @Mock
    private NotificationProvider smsProvider;

    @Mock
    private NotificationProvider pushProvider;

    @Mock
    private NotificationProvider inAppProvider;

    private NotificationService notificationService;

    private User user;

    private UserPreference preference;

    @BeforeEach
    void setUp() {

        when(emailProvider.getChannel())
                .thenReturn(NotificationChannel.EMAIL);

        when(smsProvider.getChannel())
                .thenReturn(NotificationChannel.SMS);

        when(pushProvider.getChannel())
                .thenReturn(NotificationChannel.PUSH);

        when(inAppProvider.getChannel())
                .thenReturn(NotificationChannel.IN_APP);

        notificationService =
                new NotificationService(
                        userRepository,
                        userPreferenceRepository,
                        notificationHistoryRepository,
                        List.of(
                                emailProvider,
                                smsProvider,
                                pushProvider,
                                inAppProvider
                        )
                );

        user = User.builder()
                .id(1L)
                .name("Test User")
                .email("test@example.com")
                .phone("9876543210")
                .deviceToken("test-device")
                .build();

        preference = UserPreference.builder()
                .id(1L)
                .user(user)
                .emailEnabled(true)
                .smsEnabled(false)
                .pushEnabled(true)
                .inAppEnabled(true)
                .build();
    }
}