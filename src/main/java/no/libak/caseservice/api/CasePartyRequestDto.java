package no.libak.caseservice.api;

import jakarta.validation.constraints.NotEmpty;

public record CasePartyRequestDto(@NotEmpty String ssn,
                                  @NotEmpty String firstName,
                                  @NotEmpty String lastName,
                                  @NotEmpty String addressLine1,
                                  String addressLine2,
                                  String addressLine3,
                                  @NotEmpty String postalCode,
                                  @NotEmpty String city,
                                  @NotEmpty String country,
                                  @NotEmpty String phoneNumber,
                                  @NotEmpty String email) {
}
