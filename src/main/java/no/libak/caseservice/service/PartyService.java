package no.libak.caseservice.service;

import no.libak.caseservice.exception.NotFoundException;
import no.libak.caseservice.model.Party;
import no.libak.caseservice.repository.PartyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PartyService {

    private final PartyRepository partyRepository;
    private final Logger log = LoggerFactory.getLogger(PartyService.class);

    public PartyService(PartyRepository partyRepository) {
        this.partyRepository = partyRepository;
    }

    public void createParty(Party party) {
        log.debug("Creating new party");
        party.setCreatedDate(LocalDateTime.now());
        partyRepository.save(party);
    }

    public Party getParty(String ssn) {
        log.debug("Getting party with ssn {}", ssn);
        return partyRepository.findBySsn(ssn)
                .orElseThrow(() -> new NotFoundException("Party not found: " + ssn));
    }
}
