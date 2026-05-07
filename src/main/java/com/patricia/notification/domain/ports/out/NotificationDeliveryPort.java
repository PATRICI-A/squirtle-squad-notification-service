package com.patricia.notification.domain.ports.out;

import com.patricia.notification.domain.model.Notification;
import com.patricia.notification.domain.model.enums.NotificationChannel;

public interface NotificationDeliveryPort {
    void deliver(Notification notification);
    NotificationChannel supportedChannel();
}