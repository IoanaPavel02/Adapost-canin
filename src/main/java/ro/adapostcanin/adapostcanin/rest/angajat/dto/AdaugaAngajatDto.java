package ro.adapostcanin.adapostcanin.rest.angajat.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ro.adapostcanin.adapostcanin.config.UserType;

public record AdaugaAngajatDto
        (@NotBlank
         String nume,
         @NotBlank
         String prenume,
         @NotBlank
         String cnp, @NotBlank
         String username,
         @NotBlank
         String password,
         @NotNull
         UserType role) {
}
