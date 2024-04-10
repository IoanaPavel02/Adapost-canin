package ro.adapostcanin.adapostcanin.rest.adoptant.dto;

import jakarta.validation.constraints.NotBlank;

public record ModificaAdoptantDto(
        @NotBlank
        String nume,
        @NotBlank
        String prenume,
        @NotBlank
        String cnp) {
}
