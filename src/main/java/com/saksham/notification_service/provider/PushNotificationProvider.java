package com.saksham.notification_service.provider;

import com.saksham.notification_service.entity.User;
import com.saksham.notification_service.enums.NotificationChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PushNotificationProvider implements NotificationProvider {

    private static final Logger log =
            LoggerFactory.getLogger(PushNotificationProvider.class);

    @Override
    public NotificationChannel getChannel() {
        return NotificationChannel.PUSH;
    }

    @Override
    public String format(String title, String body) {

        if (body.length() > 100) {
            return title + " - "
                    + body.substring(0, 97)
                    + "...";
        }

        return title + " - " + body;
    }
    @Override
    public void send(User user, String title, String body) {

        String formattedMessage = format(title, body);

        log.info(
                "Mock PUSH notification sent to device {} | Message: {}",
                user.getDeviceToken(),
                formattedMessage
        );
    }
}