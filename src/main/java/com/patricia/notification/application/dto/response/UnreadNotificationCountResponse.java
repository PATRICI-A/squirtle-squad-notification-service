package com.patricia.notification.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

/**
 * Response payload containing the number of unread notifications for a user.
 */
@Schema(description = "Unread notification count for the user")
@Getter
@Builder
public class UnreadNotificationCountResponse {

    @Schema(description = "Number of notifications not yet read by the user", example = "5")
    private int count;
}
