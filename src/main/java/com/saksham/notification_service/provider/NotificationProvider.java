package com.saksham.notification_service.provider;

import com.saksham.notification_service.entity.User;
import com.saksham.notification_service.enums.NotificationChannel;

public interface NotificationProvider {

    NotificationChannel getChannel();

    void send(User user, String title, String body);

    default String format(String title, String body) {
        return title + "\n" + body;
    }
}