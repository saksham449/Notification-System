package com.saksham.notification_service.repository;

import com.saksham.notification_service.entity.NotificationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationHistoryRepository
        extends JpaRepository<NotificationHistory, Long> {

    List<NotificationHistory> findByUserIdOrderBySentAtDesc(Long userId);
}