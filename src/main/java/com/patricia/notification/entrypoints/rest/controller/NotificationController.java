package com.patricia.notification.entrypoints.rest.controller;

import com.patricia.notification.application.dto.request.UpdatePreferencesRequest;
import com.patricia.notification.application.dto.response.NotificationPreferencesResponse;
import com.patricia.notification.application.dto.response.NotificationResponse;
import com.patricia.notification.application.dto.response.UnreadNotificationCountResponse;
import com.patricia.notification.domain.ports.in.GetNotificationsUseCase;
import com.patricia.notification.domain.ports.in.GetPreferencesUseCase;
import com.patricia.notification.domain.ports.in.GetUnreadCountUseCase;
import com.patricia.notification.domain.ports.in.MarkAsReadUseCase;
import com.patricia.notification.domain.ports.in.UpdatePreferencesUseCase;
import com.patricia.notification.application.mapper.NotificationMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final GetNotificationsUseCase getNotificationsUseCase;
    private final GetUnreadCountUseCase getUnreadCountUseCase;
    private final MarkAsReadUseCase markAsReadUseCase;
    private final GetPreferencesUseCase getPreferencesUseCase;
    private final UpdatePreferencesUseCase updatePreferencesUseCase;
    private final NotificationMapper mapper;

    @GetMapping
    public ResponseEntity<List<NotificationResponse>> getNotifications(
            @RequestHeader("X-User-Id") String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        List<NotificationResponse> response = getNotificationsUseCase
                .execute(userId, page, size)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/unread/count")
    public ResponseEntity<UnreadNotificationCountResponse> getUnreadCount(
            @RequestHeader("X-User-Id") String userId) {

        int count = getUnreadCountUseCase.execute(userId);
        return ResponseEntity.ok(mapper.toUnreadCountResponse(count));
    }

    @PutMapping("/{notificationId}/read")
    public ResponseEntity<Void> markSingleAsRead(
            @PathVariable String notificationId,
            @RequestHeader("X-User-Id") String userId) {

        markAsReadUseCase.executeSingle(notificationId, userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/read")
    public ResponseEntity<Void> markAllAsRead(
            @RequestHeader("X-User-Id") String userId) {

        markAsReadUseCase.executeAll(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/preferences")
    public ResponseEntity<NotificationPreferencesResponse> getPreferences(
            @RequestHeader("X-User-Id") String userId) {

        return ResponseEntity.ok(
                mapper.toPreferencesResponse(
                        getPreferencesUseCase.execute(userId)));
    }

    @PutMapping("/preferences")
    public ResponseEntity<NotificationPreferencesResponse> updatePreferences(
            @RequestHeader("X-User-Id") String userId,
            @Valid @RequestBody UpdatePreferencesRequest request) {

        return ResponseEntity.ok(
                mapper.toPreferencesResponse(
                        updatePreferencesUseCase.execute(
                                userId,
                                request.getType(),
                                request.getEnabled())));
    }



}