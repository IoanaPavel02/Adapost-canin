package ro.adapostcanin.adapostcanin.rest.angajat;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ro.adapostcanin.adapostcanin.entity.animal.AnimalRepository;
import ro.adapostcanin.adapostcanin.entity.persoana.angajat.Angajat;
import ro.adapostcanin.adapostcanin.entity.persoana.angajat.AngajatRepository;
import ro.adapostcanin.adapostcanin.exception.BadRequestException;
import ro.adapostcanin.adapostcanin.rest.angajat.dto.AdaugaAngajatDto;
import ro.adapostcanin.adapostcanin.rest.angajat.dto.AsociazaAnimalDto;

import static ro.adapostcanin.adapostcanin.utils.constants.Constants.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/angajat")
@Transactional
@Slf4j
public class AngajatCommandController {

    private final AngajatRepository angajatRepository;
    private final AnimalRepository animalRepository;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<String> adaugaAngajat(@RequestBody @Valid AdaugaAngajatDto dto) {

        if (angajatRepository.findByCnp(dto.cnp()).isPresent())
            throw new BadRequestException(ANGAJAT_EXISTA);

        Angajat angajat = ((Angajat) new Angajat()
                .setCnp(dto.cnp())
                .setNume(dto.nume())
                .setPrenume(dto.prenume()))
                .setUsername(dto.username())
                .setPassword(dto.password())
                .setRole(dto.role());

        angajatRepository.save(angajat);

        return ResponseEntity.ok()
                .body("Angajatul a fost adăugat cu succes");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> asociazaAnimal(@PathVariable @NotNull Long id,
                                                 @RequestBody @Valid AsociazaAnimalDto dto) {

        var angajat = angajatRepository.findById(id)
                .orElseThrow(BadRequestException.supply(ANGAJAT_NU_EXISTA));

        var animale = animalRepository.findAllById(dto.animalIds());

        if (animale.isEmpty())
            throw new BadRequestException(ANIMALELE_NU_EXISTA);

        var animaleAsociate = angajat.getAnimale();
        var animaleDeAsociat = dto.animalIds();
        if (animaleAsociate
                .stream()
                .anyMatch(animal -> animaleDeAsociat.contains(animal.getId()))) {
            throw new BadRequestException("Unul sau mai multe animale sunt deja asociate acestui angajat");
        }

        angajat.setAnimale(animale);

        angajatRepository.save(angajat);

        return ResponseEntity.ok()
                .body("Animalele au fost asociate angajatului cu succes");
    }

    @PutMapping("/{id}/dezasociaza")
    public ResponseEntity<String> dezasociazaAnimal(@PathVariable @NotNull Long id,
                                                    @RequestParam @NotNull Long animalId) {

        var angajat = angajatRepository.findById(id)
                .orElseThrow(BadRequestException.supply(ANGAJAT_NU_EXISTA));

        var animal = animalRepository.findById(animalId)
                .orElseThrow(BadRequestException.supply(ANIMAL_NU_EXISTA));

        if (!animal.getAngajati().contains(angajat)) {
            throw new BadRequestException("Angajatul nu se ocupǎ de acest animal.");
        }

        if (animal.getAngajati().size() == 1) {
            throw new BadRequestException("Angajatul nu poate fi dezasociat deoarece animalul rǎmane fǎrǎ îngrijitor(angajat).");
        }

        log.info("Angajat before removing animal " + angajat);

        angajat.getAnimale().remove(animal);

        log.info("Angajat after removing animal" + angajat);

        angajatRepository.save(angajat);

        return ResponseEntity.ok()
                .body("Angajatul a fost dezasociat cu succes");
    }
}
