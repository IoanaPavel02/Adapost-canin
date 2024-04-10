package ro.adapostcanin.adapostcanin.authz;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.adapostcanin.adapostcanin.entity.fisamedicala.FisaMedicalaRepository;
import ro.adapostcanin.adapostcanin.entity.persoana.angajat.Angajat;
import ro.adapostcanin.adapostcanin.exception.BadRequestException;

import static ro.adapostcanin.adapostcanin.utils.constants.Constants.FISA_NU_EXISTA;

@Service
@RequiredArgsConstructor
@Slf4j
public class AngajatAuthzService {

    final FisaMedicalaRepository fisaMedicalaRepository;

    public boolean areDrepturiAsupraFisei(Long fisaMedicalaId, Long angajatId) {

        var fisaMedicala = fisaMedicalaRepository.findById(fisaMedicalaId)
                .orElseThrow(BadRequestException.supply(FISA_NU_EXISTA));

        return fisaMedicala.getAnimal()
                .getAngajati()
                .stream()
                .map(Angajat::getId)
                .toList()
                .contains(angajatId);

    }
}
