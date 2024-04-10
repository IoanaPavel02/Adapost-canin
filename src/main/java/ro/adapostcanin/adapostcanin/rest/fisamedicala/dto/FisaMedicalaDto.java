package ro.adapostcanin.adapostcanin.rest.fisamedicala.dto;

import ro.adapostcanin.adapostcanin.entity.fisamedicala.FisaMedicala;

import java.util.Date;

public record FisaMedicalaDto(
        Long id,
        String detalii,
        Date inceputSimptome,
        Long animalId
) {


    public FisaMedicalaDto(FisaMedicala fisaMedicala) {
        this(fisaMedicala.getId(), fisaMedicala.getDetalii(),
                fisaMedicala.getInceputSimptome(), fisaMedicala.getAnimal().getId());
    }
}
