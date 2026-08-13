package com.saksham.notification_service.provider;

import com.saksham.notification_service.entity.User;
import com.saksham.notification_service.enums.NotificationChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationProvider implements NotificationProvider {

    private static final Logger log =
            LoggerFactory.getLogger(EmailNotificationProvider.class);

    @Override
    public NotificationChannel getChannel() {
        return NotificationChannel.EMAIL;
    }

    @Override
    public void send(User user, String title, String body) {

        log.info(
                "Mock EMAIL sent to {} | Title: {} | Body: {}",
                user.getEmail(),
                title,
                body
        );
    }
}