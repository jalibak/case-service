package no.libak.caseservice.api;

import jakarta.validation.constraints.NotEmpty;

public record NewCommentDto(@NotEmpty String comment) {
}
