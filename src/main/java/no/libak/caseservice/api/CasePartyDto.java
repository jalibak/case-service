package no.libak.caseservice.api;

import java.time.LocalDateTime;

public record CasePartyDto(String ssn,
                           String firstName,
                           String lastName,
                           String addressLine1,
                           String addressLine2,
                           String addressLine3,
                           String postalCode,
                           String city,
                           String country,
                           String phoneNumber,
                           String email,
                           LocalDateTime createdDate,
                           LocalDateTime lastModifiedDate) {
}
