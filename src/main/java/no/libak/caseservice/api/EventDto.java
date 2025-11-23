package no.libak.caseservice.api;

import java.time.LocalDateTime;

public record EventDto(LocalDateTime timestamp,
                       String eventType,
                       String caseNumber) {
}
