package ro.adapostcanin.adapostcanin.rest.animal;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ro.adapostcanin.adapostcanin.entity.animal.Animal;
import ro.adapostcanin.adapostcanin.entity.animal.AnimalRepository;
import ro.adapostcanin.adapostcanin.entity.fisamedicala.FisaMedicala;
import ro.adapostcanin.adapostcanin.entity.fisamedicala.FisaMedicalaRepository;
import ro.adapostcanin.adapostcanin.entity.mancare.MancareRepository;
import ro.adapostcanin.adapostcanin.entity.persoana.angajat.AngajatRepository;
import ro.adapostcanin.adapostcanin.exception.BadRequestException;
import ro.adapostcanin.adapostcanin.rest.animal.dto.AdaugaAnimalDto;
import ro.adapostcanin.adapostcanin.rest.animal.dto.ModificaAnimalDto;

import java.util.List;

import static ro.adapostcanin.adapostcanin.utils.constants.Constants.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/animal")
@Transactional
@Slf4j
public class AnimalCommandController {

    final AnimalRepository animalRepository;
    final AngajatRepository angajatRepository;
    final MancareRepository mancareRepository;
    private final FisaMedicalaRepository fisaMedicalaRepository;

    @PostMapping
    public ResponseEntity<String> adaugaAnimal(@RequestBody @Valid AdaugaAnimalDto dto) {

        var angajati = angajatRepository.findAllById(dto.angajatIds());
        if (angajati.isEmpty())
            throw new BadRequestException(ANGAJATII_NU_EXISTA);

        var mancare = mancareRepository.findAllById(dto.tipMancareIds());
        if (mancare.isEmpty())
            throw new BadRequestException(MANCARE_NU_EXISTA);

        var fisaMedicala = new FisaMedicala(dto.detaliiFisaMedicala());

        log.info("Fisa medicala: " + fisaMedicala);

        var animal = new Animal()
                .setNume(dto.nume())
                .setAngajati(angajati)
                .setFisaMedicala(fisaMedicala)
                .setMancaruri(mancare);

        log.info("Animal: " + animal);

        fisaMedicala.setAnimal(animal);
        mancare.forEach(m -> m.setAnimale(List.of(animal)));
        angajati.forEach(a -> a.setAnimale(List.of(animal)));

        animalRepository.save(animal);

        return ResponseEntity.status(HttpStatus.OK)
                .body("Animalul a fost adăugat cu succes.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> modificaAnimal(@PathVariable Long id, @RequestBody @Valid ModificaAnimalDto dto) {

        var animal = animalRepository.findById(id)
                .orElseThrow(BadRequestException.supply(ANIMAL_NU_EXISTA));

        log.info("Animal before: " + animal);

        var fisaMedicala = fisaMedicalaRepository.findById(animal.getFisaMedicala().getId())
                .orElseThrow(BadRequestException.supply(FISA_NU_EXISTA));

        var angajati = angajatRepository.findAllById(dto.angajatIds());
        if (angajati.isEmpty())
            throw new BadRequestException(ANGAJATII_NU_EXISTA);

        var mancaruri = mancareRepository.findAllById(dto.tipMancareIds());
        if (mancaruri.isEmpty())
            throw new BadRequestException(MANCARE_NU_EXISTA);

        fisaMedicala.setDetalii(dto.detaliiFisaMedicala());

        animal.setNume(dto.nume());
        animal.setAngajati(angajati);
        animal.setFisaMedicala(fisaMedicala);
        animal.setMancaruri(mancaruri);

        log.info("Animal after: " + animal);

        animalRepository.save(animal);

        return ResponseEntity.ok("Animalul a fost modifica cu succes");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> stergeAdoptant(@PathVariable Long id) {

        animalRepository.findById(id)
                .ifPresentOrElse(
                        animalRepository::delete,
                        () -> {
                            log.error(ANIMAL_NU_EXISTA); //i ll throw exception anyway - just for demo
                            throw new BadRequestException(ANIMAL_NU_EXISTA);
                        }
                );

        return ResponseEntity.ok("Animalul a fost șters cu succes");
    }
}
