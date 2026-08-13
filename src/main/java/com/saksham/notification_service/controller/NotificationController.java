package com.saksham.notification_service.controller;

import com.saksham.notification_service.dto.NotificationRequest;
import com.saksham.notification_service.dto.NotificationResponse;
import com.saksham.notification_service.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<NotificationResponse> sendNotification(
            @Valid @RequestBody NotificationRequest request) {

        NotificationResponse response =
                notificationService.sendNotification(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}