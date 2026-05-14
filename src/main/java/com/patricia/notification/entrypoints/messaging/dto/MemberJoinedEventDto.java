package com.patricia.notification.entrypoints.messaging.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberJoinedEventDto {
    private UUID capitanId;
    private UUID estudianteId;
    private UUID nombreParche;
    private LocalDateTime timestamp;
}
