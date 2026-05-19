package com.patricia.notification.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

/**
 * Response payload representing a user's notification preferences.
 *
 * <p>Each boolean field corresponds to a notification type. {@code false} means the
 * user has disabled that category and will not receive notifications of that type.</p>
 */
@Schema(description = "User notification preferences")
@Getter
@Builder
public class NotificationPreferencesResponse {

    @Schema(description = "Connection request notifications enabled", example = "true")
    private boolean connectionRequest;

    @Schema(description = "Parche message notifications enabled", example = "true")
    private boolean parcheMessage;

    @Schema(description = "Event reminder notifications enabled", example = "true")
    private boolean eventReminder;

    @Schema(description = "Nearby parche notifications enabled", example = "false")
    private boolean nearbyParche;

    @Schema(description = "Achievement unlocked notifications enabled", example = "true")
    private boolean achievementUnlocked;

    @Schema(description = "Parche invitation notifications enabled", example = "true")
    private boolean parcheInvitation;

    @Schema(description = "OTP verification notifications enabled", example = "true")
    private boolean otpVerification;

    @Schema(description = "Password reset notifications enabled", example = "true")
    private boolean passwordReset;

    @Schema(description = "Invitation accepted notifications enabled", example = "true")
    private boolean invitationAccepted;

    @Schema(description = "Invitation sent notifications enabled", example = "true")
    private boolean invitationSent;

    @Schema(description = "Member joined parche notifications enabled", example = "true")
    private boolean memberJoined;

    @Schema(description = "Match received notifications enabled", example = "true")
    private boolean matchReceived;

    @Schema(description = "Match response notifications enabled", example = "true")
    private boolean matchResponse;

    @Schema(description = "Friendship created notifications enabled", example = "true")
    private boolean friendshipCreated;
}
