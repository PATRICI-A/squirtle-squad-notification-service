package com.patricia.notification.entrypoints.messaging.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageEventDto {

    /** ID of the user who receives the notification. */
    @NotNull
    private UUID recipientUserId;

    /** Display name of the user who sent the message. */
    @NotBlank
    private String senderName;

    /** ID of the conversation/chat, used as referenceId. */
    @NotNull
    private UUID conversationId;
}