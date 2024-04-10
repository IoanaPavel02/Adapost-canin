package ro.adapostcanin.adapostcanin.entity.fisamedicala;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FisaMedicalaRepository extends JpaRepository<FisaMedicala, Long> {

    @Query(value = """
            SELECT FM.* FROM FISA_MEDICALA FM
            JOIN ANIMAL A ON A.ID = FM.ANIMAL_ID
            WHERE A.ID = :animalId
            """, nativeQuery = true)
    Optional<FisaMedicala> findByAnimalId(Long animalId);

}
