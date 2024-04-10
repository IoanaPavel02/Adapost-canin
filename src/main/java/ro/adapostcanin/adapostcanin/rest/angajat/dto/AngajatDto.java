package ro.adapostcanin.adapostcanin.rest.angajat.dto;

import ro.adapostcanin.adapostcanin.entity.animal.Animal;
import ro.adapostcanin.adapostcanin.entity.persoana.angajat.Angajat;

import java.util.List;

public record AngajatDto(
        Long id,
        String nume,
        String prenume,
        String cnp,
        List<Long> animaleAsociateIds
) {
    public AngajatDto(Angajat angajat) {
        this(angajat.getId(), angajat.getNume(), angajat.getPrenume(), angajat.getCnp(),
                angajat.getAnimale()
                        .stream()
                        .map(Animal::getId)
                        .toList());
    }
}
