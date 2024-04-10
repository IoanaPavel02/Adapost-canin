package ro.adapostcanin.adapostcanin.rest.animal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record ModificaAnimalDto(
        @NotBlank
        String nume,
        @NotBlank
        String detaliiFisaMedicala,
        @NotEmpty
        List<Long> angajatIds,
        @NotEmpty
        List<Long> tipMancareIds
) {
}
