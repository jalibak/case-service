package no.libak.caseservice.rule;

import no.libak.caseservice.constant.CaseStatus;
import no.libak.caseservice.exception.InvalidInputException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StatusChangeRuleTest {

    @Test
    void openToInProgressTest() {
        assertThatCode(() -> StatusChangeRule.validateStatusChange(CaseStatus.IN_PROGRESS, CaseStatus.OPEN)).doesNotThrowAnyException();
    }

    @Test
    void inProgressToPausedTest() {
        assertThatCode(() -> StatusChangeRule.validateStatusChange(CaseStatus.PAUSED, CaseStatus.IN_PROGRESS)).doesNotThrowAnyException();
    }

    @Test
    void inProgressToClosedTest() {
        assertThatCode(() -> StatusChangeRule.validateStatusChange(CaseStatus.CLOSED, CaseStatus.IN_PROGRESS)).doesNotThrowAnyException();
    }

    @Test
    void pausedToInProgressTest() {
        assertThatCode(() -> StatusChangeRule.validateStatusChange(CaseStatus.IN_PROGRESS, CaseStatus.PAUSED)).doesNotThrowAnyException();
    }

    @Test
    void closedToOpenTest() {
        assertThatCode(() -> StatusChangeRule.validateStatusChange(CaseStatus.OPEN, CaseStatus.CLOSED)).doesNotThrowAnyException();
    }

    @Test
    void openToClosedErrorTest() {
        assertThatThrownBy(() -> StatusChangeRule.validateStatusChange(CaseStatus.CLOSED, CaseStatus.OPEN))
                .isInstanceOf(InvalidInputException.class);
    }

    @Test
    void inProgressToOpenErrorTest() {
        assertThatThrownBy(() -> StatusChangeRule.validateStatusChange(CaseStatus.OPEN, CaseStatus.IN_PROGRESS))
                .isInstanceOf(InvalidInputException.class);
    }

    @Test
    void pausedToClosedErrorTest() {
        assertThatThrownBy(() -> StatusChangeRule.validateStatusChange(CaseStatus.CLOSED, CaseStatus.PAUSED))
                .isInstanceOf(InvalidInputException.class);
    }

    @Test
    void closedToPausedErrorTest() {
        assertThatThrownBy(() -> StatusChangeRule.validateStatusChange(CaseStatus.PAUSED, CaseStatus.CLOSED))
                .isInstanceOf(InvalidInputException.class);
    }

}