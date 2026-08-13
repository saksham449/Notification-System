package com.saksham.notification_service.provider;

import com.saksham.notification_service.entity.User;
import com.saksham.notification_service.enums.NotificationChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class InAppNotificationProvider implements NotificationProvider {

    private static final Logger log =
            LoggerFactory.getLogger(InAppNotificationProvider.class);

    @Override
    public NotificationChannel getChannel() {
        return NotificationChannel.IN_APP;
    }

    @Override
    public void send(User user, String title, String body) {

        log.info(
                "Mock IN_APP notification created for user {} | Title: {} | Body: {}",
                user.getId(),
                title,
                body
        );
    }
}