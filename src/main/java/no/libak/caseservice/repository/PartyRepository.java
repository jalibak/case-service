package no.libak.caseservice.repository;

import no.libak.caseservice.model.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@RequestMapping
public interface PartyRepository extends JpaRepository<Party, Long> {

    Optional<Party> findBySsn(String ssn);
}
