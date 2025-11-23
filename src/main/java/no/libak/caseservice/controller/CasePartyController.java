package no.libak.caseservice.controller;

import jakarta.validation.Valid;
import no.libak.caseservice.api.CasePartyDto;
import no.libak.caseservice.api.CasePartyRequestDto;
import no.libak.caseservice.model.Party;
import no.libak.caseservice.service.PartyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/party")
public class CasePartyController {

    private final PartyService partyService;
    private final Logger log = LoggerFactory.getLogger(CaseController.class);

    public CasePartyController(PartyService partyService) {
        this.partyService = partyService;
    }

    @PostMapping
    public void createCaseParty(@Valid @RequestBody CasePartyRequestDto caseParty) {
        var newParty = new Party();
        newParty.setSsn(caseParty.ssn());
        newParty.setFirstName(caseParty.firstName());
        newParty.setLastName(caseParty.lastName());
        newParty.setAddressLine1(caseParty.addressLine1());
        newParty.setAddressLine2(caseParty.addressLine2());
        newParty.setAddressLine3(caseParty.addressLine3());
        newParty.setPostalCode(caseParty.postalCode());
        newParty.setCity(caseParty.city());
        newParty.setCountry(caseParty.country());
        newParty.setPhoneNumber(caseParty.phoneNumber());
        newParty.setEmail(caseParty.email());

        partyService.createParty(newParty);
    }

    @GetMapping("/{ssn}")
    public CasePartyDto getCaseParty(@PathVariable String ssn) {
        log.debug("Getting party with ssn {}", ssn);
        var caseParty = partyService.getParty(ssn);

        return new CasePartyDto(caseParty.getSsn(),
                caseParty.getFirstName(),
                caseParty.getLastName(),
                caseParty.getAddressLine1(),
                caseParty.getAddressLine2(),
                caseParty.getAddressLine3(),
                caseParty.getPostalCode(),
                caseParty.getCity(),
                caseParty.getCountry(),
                caseParty.getPhoneNumber(),
                caseParty.getEmail(),
                caseParty.getCreatedDate(),
                caseParty.getLastModifiedDate());
    }
}
