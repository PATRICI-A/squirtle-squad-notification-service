package com.patricia.notification.entrypoints.rest.controller;

import com.patricia.notification.application.dto.request.SendNotificationRequest;
import com.patricia.notification.application.dto.request.UpdatePreferencesRequest;
import com.patricia.notification.application.dto.response.NotificationPreferencesResponse;
import com.patricia.notification.application.dto.response.NotificationResponse;
import com.patricia.notification.application.dto.response.UnreadNotificationCountResponse;
import com.patricia.notification.application.mapper.NotificationMapper;
import com.patricia.notification.domain.ports.in.GetNotificationsUseCase;
import com.patricia.notification.domain.ports.in.GetPreferencesUseCase;
import com.patricia.notification.domain.ports.in.GetUnreadCountUseCase;
import com.patricia.notification.domain.ports.in.MarkAsReadUseCase;
import com.patricia.notification.domain.ports.in.SendNotificationUseCase;
import com.patricia.notification.domain.ports.in.UpdatePreferencesUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * REST controller for managing user notifications.
 *
 * <p>All endpoints require the caller to supply the recipient's user ID via the
 * {@code X-User-Id} header, which is expected to be injected by the API gateway
 * after authentication.</p>
 */
@Tag(name = "Notifications", description = "Manage in-app notifications for users")
@Validated
@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final SendNotificationUseCase sendNotificationUseCase;
    private final GetNotificationsUseCase getNotificationsUseCase;
    private final GetUnreadCountUseCase getUnreadCountUseCase;
    private final MarkAsReadUseCase markAsReadUseCase;
    private final GetPreferencesUseCase getPreferencesUseCase;
    private final UpdatePreferencesUseCase updatePreferencesUseCase;
    private final NotificationMapper mapper;

    /**
     * Creates and delivers a notification to a user.
     *
     * <p>If the user is connected via WebSocket the notification is delivered in real time;
     * otherwise it is persisted in MongoDB for later retrieval.</p>
     */
    @Operation(summary = "Send notification", description = "Creates and delivers a notification to the specified user. If the user is connected via WebSocket it is delivered in real time; otherwise it is persisted for later retrieval.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Notification created and delivered"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "409", description = "Notification type disabled by the user")
    })
    @PostMapping
    public ResponseEntity<NotificationResponse> sendNotification(
            @Valid @RequestBody SendNotificationRequest request) {

        NotificationResponse response = mapper.toResponse(
                sendNotificationUseCase.execute(
                        request.getUserId(),
                        request.getType(),
                        request.getTitle(),
                        request.getBody(),
                        request.getReferenceId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Returns a paginated list of notifications for the requesting user,
     * sorted by creation date descending.
     */
    @Operation(summary = "List notifications", description = "Returns a paginated list of notifications for the user, sorted by creation date descending.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Notification list"),
            @ApiResponse(responseCode = "400", description = "Invalid pagination parameters")
    })
    @GetMapping
    public ResponseEntity<List<NotificationResponse>> getNotifications(
            @Parameter(hidden = true) @RequestHeader("X-User-Id") String userId,
            @Parameter(description = "Zero-based page index", example = "0") @Min(0) @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size (1–100)", example = "20") @Min(1) @Max(100) @RequestParam(defaultValue = "20") int size) {

        List<NotificationResponse> response = getNotificationsUseCase
                .execute(UUID.fromString(userId), page, size)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    /**
     * Returns the number of unread notifications for the requesting user.
     */
    @Operation(summary = "Get unread count", description = "Returns the number of unread notifications for the user.")
    @ApiResponse(responseCode = "200", description = "Unread notification count")
    @GetMapping("/unread/count")
    public ResponseEntity<UnreadNotificationCountResponse> getUnreadCount(
            @Parameter(hidden = true) @RequestHeader("X-User-Id") String userId) {

        int count = getUnreadCountUseCase.execute(UUID.fromString(userId));
        return ResponseEntity.ok(mapper.toUnreadCountResponse(count));
    }

    /**
     * Marks a single notification as read.
     */
    @Operation(summary = "Mark one as read", description = "Marks the specified notification as read. Only the notification owner should call this endpoint.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Notification marked as read"),
            @ApiResponse(responseCode = "404", description = "Notification not found")
    })
    @PutMapping("/{notificationId}/read")
    public ResponseEntity<Void> markSingleAsRead(
            @Parameter(description = "Notification ID", example = "550e8400-e29b-41d4-a716-446655440000") @PathVariable String notificationId,
            @Parameter(hidden = true) @RequestHeader("X-User-Id") String userId) {

        markAsReadUseCase.executeSingle(UUID.fromString(notificationId), UUID.fromString(userId));
        return ResponseEntity.noContent().build();
    }

    /**
     * Marks all notifications belonging to the requesting user as read.
     */
    @Operation(summary = "Mark all as read", description = "Marks all notifications for the user as read.")
    @ApiResponse(responseCode = "204", description = "All notifications marked as read")
    @PutMapping("/read")
    public ResponseEntity<Void> markAllAsRead(
            @Parameter(hidden = true) @RequestHeader("X-User-Id") String userId) {

        markAsReadUseCase.executeAll(UUID.fromString(userId));
        return ResponseEntity.noContent().build();
    }

    /**
     * Returns the notification preferences for the requesting user.
     * Defaults are returned if no preferences have been saved yet.
     */
    @Operation(summary = "Get preferences", description = "Returns the user's notification preferences. If no preferences exist, defaults are returned (all types enabled except NEARBY_PARCHE).")
    @ApiResponse(responseCode = "200", description = "User notification preferences")
    @GetMapping("/preferences")
    public ResponseEntity<NotificationPreferencesResponse> getPreferences(
            @Parameter(hidden = true) @RequestHeader("X-User-Id") String userId) {

        return ResponseEntity.ok(
                mapper.toPreferencesResponse(
                        getPreferencesUseCase.execute(UUID.fromString(userId))));
    }

    /**
     * Enables or disables a specific notification type for the requesting user.
     */
    @Operation(summary = "Update preference", description = "Enables or disables a specific notification type for the user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Preference updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    @PutMapping("/preferences")
    public ResponseEntity<NotificationPreferencesResponse> updatePreferences(
            @Parameter(hidden = true) @RequestHeader("X-User-Id") String userId,
            @Valid @RequestBody UpdatePreferencesRequest request) {

        return ResponseEntity.ok(
                mapper.toPreferencesResponse(
                        updatePreferencesUseCase.execute(
                                UUID.fromString(userId),
                                request.getType(),
                                request.getEnabled())));
    }
}
