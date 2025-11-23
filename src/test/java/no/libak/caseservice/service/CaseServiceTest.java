package no.libak.caseservice.service;

import no.libak.caseservice.constant.CaseType;
import no.libak.caseservice.exception.NotFoundException;
import no.libak.caseservice.model.Case;
import no.libak.caseservice.repository.CaseRepository;
import no.libak.caseservice.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CaseServiceTest {

    @Mock
    private CaseRepository caseRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private EventService eventService;

    @InjectMocks
    private CaseService caseService;

    @Test
    void createNewCase() {
        var newCase = new Case();
        newCase.setCaseType(CaseType.REQUEST);

        when(caseRepository.getNextSequenceValue()).thenReturn(1L);

        caseService.createNewCase(newCase);

        verify(caseRepository, times(1)).save(any());
        verify(eventService, times(1)).sendEvent(any());
    }

    @Test
    void getCaseTest() {
        var aCase = new Case();
        aCase.setCaseNumber("REQ-0001");

        when(caseRepository.findCaseByCaseNumber("REQ-0001")).thenReturn(Optional.of(aCase));

        var actual = caseService.getCase("REQ-0001");
        assertThat(actual.getCaseNumber()).isEqualTo("REQ-0001");
    }

    @Test
    void getCaseNotFoundTest() {
        when(caseRepository.findCaseByCaseNumber("REQ-0001")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> caseService.getCase("REQ-0001"))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void getCasesByCaseTypeTest() {
        var aCase = new Case();
        aCase.setCaseNumber("REQ-0001");

        when(caseRepository.getCasesByCaseType(CaseType.REQUEST)).thenReturn(List.of(aCase));

        var cases = caseService.getCaseByCaseType(CaseType.REQUEST);
        assertThat(cases).hasSize(1);
    }

}