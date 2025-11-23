package no.libak.caseservice.api;

import no.libak.caseservice.constant.CaseStatus;
import no.libak.caseservice.constant.CaseType;

import java.time.LocalDateTime;
import java.util.List;

public record CaseDto(CaseType caseType,
                      CaseStatus caseStatus,
                      String caseNumber,
                      String description,
                      String createdBy,
                      LocalDateTime createdDate,
                      LocalDateTime lastModifiedDate,
                      String assignedUnit,
                      String assignedUser,
                      List<CommentDto> comments) {
}
