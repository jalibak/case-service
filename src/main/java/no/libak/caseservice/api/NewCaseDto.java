package no.libak.caseservice.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import no.libak.caseservice.constant.CaseType;

public record NewCaseDto(@Valid CaseType caseType,
                         @NotEmpty String description) {
}
