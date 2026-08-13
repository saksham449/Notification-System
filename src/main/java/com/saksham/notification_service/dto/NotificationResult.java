package com.saksham.notification_service.dto;

import com.saksham.notification_service.enums.DeliveryStatus;
import com.saksham.notification_service.enums.NotificationChannel;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResult {

    private NotificationChannel channel;

    private DeliveryStatus status;

    private String message;
}