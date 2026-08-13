package com.saksham.notification_service.dto;

import com.saksham.notification_service.enums.DeliveryStatus;
import com.saksham.notification_service.enums.NotificationChannel;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationHistoryResponse {

    private Long id;

    private Long userId;

    private NotificationChannel channel;

    private DeliveryStatus status;

    private String messageTitle;

    private String errorMessage;

    private LocalDateTime sentAt;
}