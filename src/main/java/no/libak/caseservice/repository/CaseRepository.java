package no.libak.caseservice.repository;

import no.libak.caseservice.constant.CaseType;
import no.libak.caseservice.model.Case;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CaseRepository extends JpaRepository<Case, Integer> {
    List<Case> getCasesByCaseType(CaseType caseType);

    Optional<Case> findCaseByCaseNumber(String caseNumber);

    @Query(value = "SELECT NEXTVAL('case_number_sequence')", nativeQuery = true)
    Long getNextSequenceValue();
}
