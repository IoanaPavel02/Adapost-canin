package ro.adapostcanin.adapostcanin.rest.adoptant.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record AdoptaAnimalDto(
        @NotBlank String nume,
        @NotBlank String prenume,
        @NotBlank String cnp,
        @NotEmpty List<Long> animalIds
) {
}
