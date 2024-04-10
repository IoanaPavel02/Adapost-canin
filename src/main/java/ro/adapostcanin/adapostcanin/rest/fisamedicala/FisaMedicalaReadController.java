package ro.adapostcanin.adapostcanin.rest.fisamedicala;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.adapostcanin.adapostcanin.entity.fisamedicala.FisaMedicalaRepository;
import ro.adapostcanin.adapostcanin.rest.fisamedicala.dto.FisaMedicalaDto;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/fisa-medicala")
@Transactional(readOnly = true)
public class FisaMedicalaReadController {

    private final FisaMedicalaRepository fisaMedicalaRepository;

    @GetMapping
    public List<FisaMedicalaDto> readAll() {
        return fisaMedicalaRepository.findAll()
                .stream()
                .map(FisaMedicalaDto::new)
                .toList();
    }

    @GetMapping("animal/{animalId}")
    public List<FisaMedicalaDto> findByAnimal(@PathVariable Long animalId) {
        return fisaMedicalaRepository.findByAnimalId(animalId)
                .stream()
                .map(FisaMedicalaDto::new)
                .toList();
    }
}
