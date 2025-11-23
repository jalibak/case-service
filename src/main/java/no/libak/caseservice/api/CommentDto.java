package no.libak.caseservice.api;

import java.time.LocalDateTime;

public record CommentDto(String comment,
                         String createdBy,
                         LocalDateTime createdDate) {
}
