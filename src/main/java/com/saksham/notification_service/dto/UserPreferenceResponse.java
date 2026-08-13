package com.saksham.notification_service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPreferenceResponse {

    private Long userId;

    private boolean emailEnabled;

    private boolean smsEnabled;

    private boolean pushEnabled;

    private boolean inAppEnabled;
}
