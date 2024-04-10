package ro.adapostcanin.adapostcanin.rest.fisamedicala;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ro.adapostcanin.adapostcanin.authz.AngajatAuthzService;
import ro.adapostcanin.adapostcanin.entity.fisamedicala.FisaMedicalaRepository;
import ro.adapostcanin.adapostcanin.exception.BadRequestException;
import ro.adapostcanin.adapostcanin.rest.fisamedicala.dto.ModificaFisaMedicalaDto;

import static ro.adapostcanin.adapostcanin.utils.constants.Constants.FISA_NU_EXISTA;

@RequiredArgsConstructor
@RestController
@RequestMapping("/fisa-medicala")
@Transactional
@Slf4j
public class FisaMedicalaCommandController {

    private final FisaMedicalaRepository fisaMedicalaRepository;
    private final AngajatAuthzService angajatAuthzService;

    @PutMapping("/{id}")
    @PreAuthorize("@angajatAuthzService.areDrepturiAsupraFisei(#id,#angajatId)")

    public ResponseEntity<String> modificaFisaMedicala(@PathVariable Long id,
                                                       @RequestParam Long angajatId,
                                                       @RequestBody @Valid ModificaFisaMedicalaDto dto) {

        var fisaMedicala = fisaMedicalaRepository.findById(id)
                .orElseThrow(BadRequestException.supply(FISA_NU_EXISTA));

        fisaMedicala.setDetalii(dto.detalii());
        fisaMedicala.setInceputSimptome(dto.inceputSimptome());

        fisaMedicalaRepository.save(fisaMedicala);

        return ResponseEntity.ok("Fişa medicalǎ a fost modificatǎ cu succes.");
    }

}
