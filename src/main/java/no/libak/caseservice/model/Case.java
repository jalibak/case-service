package no.libak.caseservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import no.libak.caseservice.constant.CaseStatus;
import no.libak.caseservice.constant.CaseType;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@Table(name = "case_t")
@NoArgsConstructor
@AllArgsConstructor
public class Case {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long caseId;
    @Enumerated(EnumType.STRING)
    private CaseType caseType;
    @Enumerated(EnumType.STRING)
    private CaseStatus caseStatus;
    private String description;
    private String caseNumber;
    private String createdBy;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private String assignedUnit;
    private String assignedUser;

    @OneToMany(mappedBy = "aCase")
    private List<CasePartyRole> caseParties;

    @OneToMany(mappedBy = "aCase")
    private List<Comment> comments;
    private Boolean sensitive;
}
