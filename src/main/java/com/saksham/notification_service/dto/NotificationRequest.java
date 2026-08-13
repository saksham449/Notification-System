package com.saksham.notification_service.dto;

import com.saksham.notification_service.enums.NotificationChannel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Body is required")
    private String body;

    @NotEmpty(message = "At least one notification channel is required")
    private Set<NotificationChannel> channels;
}