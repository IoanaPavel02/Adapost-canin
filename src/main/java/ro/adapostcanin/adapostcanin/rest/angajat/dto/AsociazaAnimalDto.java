package ro.adapostcanin.adapostcanin.rest.angajat.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record AsociazaAnimalDto(@NotEmpty List<Long> animalIds) {
}
