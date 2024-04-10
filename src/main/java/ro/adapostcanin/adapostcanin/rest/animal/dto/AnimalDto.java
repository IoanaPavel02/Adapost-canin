package ro.adapostcanin.adapostcanin.rest.animal.dto;

import ro.adapostcanin.adapostcanin.entity.animal.Animal;
import ro.adapostcanin.adapostcanin.entity.mancare.Mancare;
import ro.adapostcanin.adapostcanin.entity.persoana.Persoana;
import ro.adapostcanin.adapostcanin.entity.persoana.angajat.Angajat;

import java.util.List;

import static java.util.Optional.ofNullable;

public record AnimalDto(
        Long id,
        String nume,
        List<Long> ingrijitoriIds,
        Long adoptantId,
        String adoptantNume,
        Long fisaMedicalaId,
        String fisaMedicalaDescriere,
        List<Long> mancareTipIds
) {

    public AnimalDto(Animal animal) {
        this(animal.getId(),
                animal.getNume(),
                animal.getAngajati()
                        .stream()
                        .map(Angajat::getId)
                        .toList(),
                ofNullable(animal.getAdoptant()).map(Persoana::getId).orElse(null),
                ofNullable(animal.getAdoptant()).map(Persoana::getNume).orElse(null),
                animal.getFisaMedicala().getId(),
                animal.getFisaMedicala().getDetalii(),
                animal.getMancaruri()
                        .stream()
                        .map(Mancare::getId)
                        .toList()

        );
    }

}
