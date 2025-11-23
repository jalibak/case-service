package no.libak.caseservice.rule;

import no.libak.caseservice.constant.CaseStatus;
import no.libak.caseservice.exception.InvalidInputException;

public class StatusChangeRule {

    private StatusChangeRule() {
        throw new IllegalStateException("Utility class");
    }

    public static void validateStatusChange(CaseStatus newStatus, CaseStatus oldStatus) {
        if (oldStatus == CaseStatus.OPEN && newStatus != CaseStatus.IN_PROGRESS) {
            throw new InvalidInputException("New case status must be IN_PROGRESS");
        } else if (oldStatus == CaseStatus.IN_PROGRESS && (newStatus != CaseStatus.CLOSED && newStatus != CaseStatus.PAUSED)) {
            throw new InvalidInputException("New case status must be CLOSED or PAUSED");
        } else if (oldStatus == CaseStatus.PAUSED && newStatus != CaseStatus.IN_PROGRESS) {
            throw new InvalidInputException("New case status must be IN_PROGRESS");
        } else if (oldStatus == CaseStatus.CLOSED && newStatus != CaseStatus.OPEN) {
            throw new InvalidInputException("New case status must be OPEN");
        }
    }
}
