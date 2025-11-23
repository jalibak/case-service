package no.libak.caseservice.controller;

import jakarta.validation.Valid;
import no.libak.caseservice.api.CaseDto;
import no.libak.caseservice.api.NewCaseDto;
import no.libak.caseservice.api.NewCommentDto;
import no.libak.caseservice.constant.CaseStatus;
import no.libak.caseservice.constant.CaseType;
import no.libak.caseservice.mapper.CaseMapper;
import no.libak.caseservice.service.CaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/case")
public class CaseController {

    private final CaseService caseService;
    private final Logger log = LoggerFactory.getLogger(CaseController.class);

    private static final String CLAIM_PREFFERED_USERNAME = "preferred_username";

    public CaseController(CaseService caseService) {
        this.caseService = caseService;
    }

    @PostMapping
    public void createNewCase(@RequestBody @Valid NewCaseDto caseRequest) {
        log.debug("Creating a new case");
        log.info("AUDIT: Creating new case - {}", extractUserNameFromToken());
        var newCase = CaseMapper.newCaseDtoToCase(caseRequest, extractUserNameFromToken());
        caseService.createNewCase(newCase);
    }

    @GetMapping("/{caseNumber}")
    public CaseDto getCase(@PathVariable String caseNumber) {
        log.debug("Retrieving case {}", caseNumber);
        log.info("AUDIT: Get case by case number: {} - {}", caseNumber, extractUserNameFromToken());
        var aCase = caseService.getCase(caseNumber);
        return CaseMapper.caseToCaseDto(aCase);
    }

    @GetMapping("/type/{caseType}")
    public List<CaseDto> getCaseByCaseType(@PathVariable @Valid CaseType caseType) {
        log.debug("Retrieving cases by case type: {}", caseType);
        log.info("AUDIT: Get cases by case type: {} - {}", caseType, extractUserNameFromToken());
        return caseService.getCaseByCaseType(caseType).stream()
                .map(CaseMapper::caseToCaseDto)
                .toList();
    }

    @PutMapping("/{caseNumber}/status/{caseStatus}")
    public void changeCaseStatus(@PathVariable String caseNumber, @PathVariable @Valid CaseStatus caseStatus) {
        log.debug("Changing case status for case {} to {}", caseNumber, caseStatus);
        log.info("AUDIT: Changing case status for case: {} - {}", caseNumber, extractUserNameFromToken());
        caseService.changeCaseStatus(caseNumber, caseStatus);
    }

    @PutMapping("/{caseNumber}/user/{user}")
    public void assignUser(@PathVariable String caseNumber, @PathVariable String user) {
        log.debug("Assigning user {} to case {}", user, caseNumber);
        log.info("AUDIT: Assigning user to case number: {} - {}", caseNumber, extractUserNameFromToken());
        caseService.assignUser(caseNumber, user);
    }

    @PostMapping("/{caseNumber}/comment")
    public void commentCase(@PathVariable String caseNumber, @RequestBody @Valid NewCommentDto newComment) {
        log.debug("Creating a new comment for case {}", caseNumber);
        log.info("AUDIT: Creating comment for case with case number: {} - {}", caseNumber, extractUserNameFromToken());
        caseService.commentCase(caseNumber, CaseMapper.newCommentDtoToComment(newComment, extractUserNameFromToken()));
    }

    private String extractUserNameFromToken() {
        var authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return authentication.getToken().getClaim(CLAIM_PREFFERED_USERNAME);
    }
}
