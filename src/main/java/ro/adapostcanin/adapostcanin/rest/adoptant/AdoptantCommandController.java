package ro.adapostcanin.adapostcanin.rest.adoptant;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ro.adapostcanin.adapostcanin.entity.animal.Animal;
import ro.adapostcanin.adapostcanin.entity.persoana.adoptant.Adoptant;
import ro.adapostcanin.adapostcanin.entity.persoana.adoptant.AdoptantRepository;
import ro.adapostcanin.adapostcanin.entity.animal.AnimalRepository;
import ro.adapostcanin.adapostcanin.rest.adoptant.dto.AdoptaAnimalDto;
import ro.adapostcanin.adapostcanin.rest.adoptant.dto.ModificaAdoptantDto;
import ro.adapostcanin.adapostcanin.exception.BadRequestException;

import java.util.Objects;

import static ro.adapostcanin.adapostcanin.utils.constants.Constants.ANIMALELE_DEJA_ADOPTATE;
import static ro.adapostcanin.adapostcanin.utils.constants.Constants.ANIMALELE_NU_EXISTA;

@RequiredArgsConstructor
@RestController
@RequestMapping("/adoptant")
@Transactional
@Slf4j
public class AdoptantCommandController {

    final AdoptantRepository adoptantRepository;
    final AnimalRepository animalRepository;

    @PostMapping
    public ResponseEntity<String> adoptaAnimal(@RequestBody @Valid AdoptaAnimalDto dto) {

        var animale = animalRepository.findAllById(dto.animalIds());

        log.info("Animale " + animale);

        if (animale.isEmpty())
            throw new BadRequestException(ANIMALELE_NU_EXISTA);

        if (animale
                .stream()
                .map(Animal::getAdoptant)
                .anyMatch(Objects::nonNull))
            throw new BadRequestException(ANIMALELE_DEJA_ADOPTATE);

        Adoptant adoptant = ((Adoptant) new Adoptant()
                .setAnimaleAdoptate(animale)
                .setNume(dto.nume())
                .setPrenume(dto.prenume())
                .setCnp(dto.cnp()));

        log.info("Adoptant " + adoptant);

        animale.forEach(animal -> animal.setAdoptant(adoptant));

        adoptantRepository.save(adoptant);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Animalele au fost adoptate cu succes");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> modificaAdoptant(@PathVariable Long id,
                                                   @RequestBody @Valid ModificaAdoptantDto dto) {
        adoptantRepository.findById(id)
                .ifPresentOrElse(
                        adoptant -> {
                            adoptant.setNume(dto.nume())
                                    .setPrenume(dto.prenume())
                                    .setCnp(dto.cnp());

                            log.info("Adoptant " + adoptant);

                            adoptantRepository.save(adoptant);
                        },
                        () -> {
                            throw new BadRequestException(ANIMALELE_NU_EXISTA);
                        }
                );

        return ResponseEntity.ok("Persoana a fost modifica cu succes");
    }
}
