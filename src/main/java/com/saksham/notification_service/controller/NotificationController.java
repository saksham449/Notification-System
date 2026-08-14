package com.saksham.notification_service.controller;

import com.saksham.notification_service.dto.NotificationHistoryResponse;
import com.saksham.notification_service.dto.NotificationRequest;
import com.saksham.notification_service.dto.NotificationResponse;
import com.saksham.notification_service.service.NotificationHistoryService;
import com.saksham.notification_service.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final NotificationHistoryService notificationHistoryService;

    @PostMapping
    public ResponseEntity<NotificationResponse> sendNotification(
            @Valid @RequestBody NotificationRequest request) {

        return ResponseEntity.ok(
                notificationService.sendNotification(request)
        );
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<List<NotificationHistoryResponse>> getHistory(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                notificationHistoryService.getHistory(userId)
        );
    }
}