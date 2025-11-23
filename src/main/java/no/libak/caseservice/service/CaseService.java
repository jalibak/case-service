package no.libak.caseservice.service;

import no.libak.caseservice.api.EventDto;
import no.libak.caseservice.constant.CaseStatus;
import no.libak.caseservice.constant.CaseType;
import no.libak.caseservice.exception.NotFoundException;
import no.libak.caseservice.model.Case;
import no.libak.caseservice.model.Comment;
import no.libak.caseservice.repository.CaseRepository;
import no.libak.caseservice.repository.CommentRepository;
import no.libak.caseservice.rule.StatusChangeRule;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static no.libak.caseservice.constant.EventConstants.EVENT_ASSIGNED_USER;
import static no.libak.caseservice.constant.EventConstants.EVENT_CREATED;
import static no.libak.caseservice.constant.EventConstants.EVENT_NEW_COMMENT;
import static no.libak.caseservice.constant.EventConstants.EVENT_STATUS_CHANGE;

@Service
public class CaseService {

    private final EventService eventService;
    private final CaseRepository caseRepository;
    private final CommentRepository commentRepository;

    public CaseService(EventService eventService,
                       CaseRepository caseRepository,
                       CommentRepository commentRepository) {
        this.eventService = eventService;
        this.caseRepository = caseRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional
    public void createNewCase(Case newCase) {
        newCase.setCaseNumber(newCase.getCaseType().name() + "-" + caseRepository.getNextSequenceValue());
        caseRepository.save(newCase);

        eventService.sendEvent(new EventDto(LocalDateTime.now(), EVENT_CREATED, newCase.getCaseNumber()));
    }

    @Transactional
    public Case getCase(String caseNumber) {
        return caseRepository.findCaseByCaseNumber(caseNumber)
                .orElseThrow(() -> new NotFoundException("Case not found: " + caseNumber));
    }

    @Transactional
    public List<Case> getCaseByCaseType(CaseType caseType) {
        return caseRepository.getCasesByCaseType(caseType);
    }

    @Transactional
    public void changeCaseStatus(String caseNumber, CaseStatus caseStatus) {
        var aCase = caseRepository.findCaseByCaseNumber(caseNumber)
                .orElseThrow(() -> new NotFoundException("Case not found: " + caseNumber));

        StatusChangeRule.validateStatusChange(caseStatus, aCase.getCaseStatus());

        aCase.setLastModifiedDate(LocalDateTime.now());
        aCase.setCaseStatus(caseStatus);
        caseRepository.save(aCase);

        eventService.sendEvent(new EventDto(LocalDateTime.now(), EVENT_STATUS_CHANGE, caseNumber));
    }

    @Transactional
    public void assignUser(String caseNumber, String user) {
        var aCase = caseRepository.findCaseByCaseNumber(caseNumber)
                .orElseThrow(() -> new NotFoundException("Case not found: " + caseNumber));

        aCase.setLastModifiedDate(LocalDateTime.now());
        aCase.setAssignedUser(user);
        caseRepository.save(aCase);

        eventService.sendEvent(new EventDto(LocalDateTime.now(), EVENT_ASSIGNED_USER, caseNumber));
    }

    @Transactional
    public void commentCase(String caseNumber, Comment comment) {
        var aCase = caseRepository.findCaseByCaseNumber(caseNumber)
                .orElseThrow(() -> new NotFoundException("Case not found: " + caseNumber));

        var comments = aCase.getComments();
        comment.setCreatedDate(LocalDateTime.now());
        comment.setACase(aCase);
        commentRepository.save(comment);

        if (comments == null) {
            comments = new ArrayList<>();
            comments.add(comment);
            aCase.setComments(comments);
        } else {
            comments.add(comment);
        }

        aCase.setLastModifiedDate(LocalDateTime.now());
        caseRepository.save(aCase);

        eventService.sendEvent(new EventDto(LocalDateTime.now(), EVENT_NEW_COMMENT, caseNumber));
    }
}
