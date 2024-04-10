package ro.adapostcanin.adapostcanin.rest.angajat;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ro.adapostcanin.adapostcanin.entity.persoana.angajat.AngajatRepository;
import ro.adapostcanin.adapostcanin.exception.BadRequestException;
import ro.adapostcanin.adapostcanin.rest.angajat.dto.AngajatDto;

import java.util.List;

import static ro.adapostcanin.adapostcanin.utils.constants.Constants.ANGAJAT_NU_EXISTA;

@RequiredArgsConstructor
@RestController
@RequestMapping("/angajat")
@Transactional(readOnly = true)
public class AngajatReadController {

    final AngajatRepository angajatRepository;

    @GetMapping
    public List<AngajatDto> readAll() {
        return angajatRepository.findAll()
                .stream()
                .map(AngajatDto::new)
                .toList();
    }

    @GetMapping("/{angajatId}")
    public AngajatDto findById(@PathVariable Long angajatId) {
        return angajatRepository.findById(angajatId)
                .map(AngajatDto::new)
                .orElseThrow(BadRequestException.supply(ANGAJAT_NU_EXISTA));
    }

}
