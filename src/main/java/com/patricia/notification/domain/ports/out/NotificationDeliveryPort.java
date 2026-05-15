package com.patricia.notification.domain.ports.out;

import com.patricia.notification.domain.model.Notification;
import com.patricia.notification.domain.model.enums.NotificationChannel;

/**
 * Output port for delivering a notification through a specific channel.
 *
 * <p>Multiple implementations may be registered (e.g. WebSocket, Email). The
 * use case selects the correct one by matching the notification's channel against
 * {@link #supportedChannel()}.</p>
 */
public interface NotificationDeliveryPort {

    /**
     * Delivers the notification through this adapter's channel.
     *
     * @param notification the notification to deliver
     */
    void deliver(Notification notification);

    /**
     * Returns the channel this adapter is responsible for.
     *
     * @return the {@link NotificationChannel} handled by this implementation
     */
    NotificationChannel supportedChannel();
}
