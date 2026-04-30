package com.patricia.notification.domain.ports.out;

import com.patricia.notification.domain.model.FcmToken;
import com.patricia.notification.domain.model.Notification;

public interface PushDeliveryPort {
    void send(Notification notification, FcmToken token);
}