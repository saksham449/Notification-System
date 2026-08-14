package com.saksham.notification_service.service;

import com.saksham.notification_service.dto.NotificationHistoryResponse;
import com.saksham.notification_service.entity.NotificationHistory;
import com.saksham.notification_service.exception.UserNotFoundException;
import com.saksham.notification_service.repository.NotificationHistoryRepository;
import com.saksham.notification_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationHistoryService {

    private final NotificationHistoryRepository notificationHistoryRepository;
    private final UserRepository userRepository;

    public List<NotificationHistoryResponse> getHistory(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }

        return notificationHistoryRepository
                .findByUserIdOrderBySentAtDesc(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private NotificationHistoryResponse mapToResponse(
            NotificationHistory history) {

        return NotificationHistoryResponse.builder()
                .id(history.getId())
                .userId(history.getUser().getId())
                .channel(history.getChannel())
                .status(history.getStatus())
                .messageTitle(history.getMessageTitle())
                .errorMessage(history.getErrorMessage())
                .sentAt(history.getSentAt())
                .build();
    }
}