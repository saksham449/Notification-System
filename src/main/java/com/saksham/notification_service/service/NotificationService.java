package com.saksham.notification_service.service;

import com.saksham.notification_service.dto.NotificationRequest;
import com.saksham.notification_service.dto.NotificationResponse;
import com.saksham.notification_service.dto.NotificationResult;
import com.saksham.notification_service.entity.NotificationHistory;
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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private final UserRepository userRepository;
    private final UserPreferenceRepository userPreferenceRepository;
    private final NotificationHistoryRepository notificationHistoryRepository;

    private final Map<NotificationChannel, NotificationProvider> providerMap;

    public NotificationService(
            UserRepository userRepository,
            UserPreferenceRepository userPreferenceRepository,
            NotificationHistoryRepository notificationHistoryRepository,
            List<NotificationProvider> providers) {

        this.userRepository = userRepository;
        this.userPreferenceRepository = userPreferenceRepository;
        this.notificationHistoryRepository = notificationHistoryRepository;

        this.providerMap = providers.stream()
                .collect(Collectors.toMap(
                        NotificationProvider::getChannel,
                        Function.identity()
                ));
    }
    public NotificationResponse sendNotification(
            NotificationRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new UserNotFoundException(request.getUserId())
                );

        UserPreference preference =
                userPreferenceRepository.findByUserId(user.getId())
                        .orElseThrow(() ->
                                new UserPreferenceNotFoundException(user.getId())
                        );

        List<NotificationResult> results =
                request.getChannels()
                        .stream()
                        .map(channel ->
                                processChannel(
                                        user,
                                        preference,
                                        channel,
                                        request
                                )
                        )
                        .toList();

        return NotificationResponse.builder()
                .userId(user.getId())
                .results(results)
                .build();
    }
    private NotificationResult processChannel(
            User user,
            UserPreference preference,
            NotificationChannel channel,
            NotificationRequest request) {

        if (!isChannelEnabled(preference, channel)) {

            saveHistory(
                    user,
                    channel,
                    DeliveryStatus.SKIPPED,
                    request.getTitle(),
                    "User has opted out of " + channel + " notifications"
            );

            return NotificationResult.builder()
                    .channel(channel)
                    .status(DeliveryStatus.SKIPPED)
                    .message(
                            "User has opted out of "
                                    + channel
                                    + " notifications"
                    )
                    .build();
        }

        try {

            NotificationProvider provider =
                    providerMap.get(channel);

            if (provider == null) {

                throw new IllegalStateException(
                        "No provider configured for channel: " + channel
                );
            }

            provider.send(
                    user,
                    request.getTitle(),
                    request.getBody()
            );

            saveHistory(
                    user,
                    channel,
                    DeliveryStatus.SUCCESS,
                    request.getTitle(),
                    null
            );

            return NotificationResult.builder()
                    .channel(channel)
                    .status(DeliveryStatus.SUCCESS)
                    .message("Notification dispatched successfully")
                    .build();

        } catch (Exception exception) {

            saveHistory(
                    user,
                    channel,
                    DeliveryStatus.FAILED,
                    request.getTitle(),
                    exception.getMessage()
            );

            return NotificationResult.builder()
                    .channel(channel)
                    .status(DeliveryStatus.FAILED)
                    .message("Notification dispatch failed")
                    .build();
        }
    }
    private boolean isChannelEnabled(
            UserPreference preference,
            NotificationChannel channel) {

        return switch (channel) {

            case EMAIL -> preference.isEmailEnabled();

            case SMS -> preference.isSmsEnabled();

            case PUSH -> preference.isPushEnabled();

            case IN_APP -> preference.isInAppEnabled();
        };
    }
    private void saveHistory(
            User user,
            NotificationChannel channel,
            DeliveryStatus status,
            String title,
            String errorMessage) {

        NotificationHistory history =
                NotificationHistory.builder()
                        .user(user)
                        .channel(channel)
                        .status(status)
                        .messageTitle(title)
                        .errorMessage(errorMessage)
                        .build();

        notificationHistoryRepository.save(history);
    }
}