package ro.adapostcanin.adapostcanin.rest.animal;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.adapostcanin.adapostcanin.entity.animal.AnimalRepository;
import ro.adapostcanin.adapostcanin.exception.BadRequestException;
import ro.adapostcanin.adapostcanin.rest.animal.dto.AnimalDto;

import java.util.List;

import static ro.adapostcanin.adapostcanin.utils.constants.Constants.ANIMAL_NU_EXISTA;

@RequiredArgsConstructor
@RestController
@RequestMapping("/animal")
@Transactional(readOnly = true)
public class AnimalReadController {

    private final AnimalRepository animalRepository;

    @GetMapping
    public List<AnimalDto> readAll() {
        return animalRepository.findAll()
                .stream()
                .map(AnimalDto::new)
                .toList();
    }

    @GetMapping("/{animalId}")
    public AnimalDto findById(@PathVariable Long animalId) {
        return animalRepository.findById(animalId)
                .map(AnimalDto::new)
                .orElseThrow(BadRequestException.supply(ANIMAL_NU_EXISTA));
    }

}
