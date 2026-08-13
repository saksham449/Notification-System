package com.saksham.notification_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserPreferenceRequest {

    @NotNull
    private Boolean emailEnabled;

    @NotNull
    private Boolean smsEnabled;

    @NotNull
    private Boolean pushEnabled;

    @NotNull
    private Boolean inAppEnabled;
}
