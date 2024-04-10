package ro.adapostcanin.adapostcanin.entity.persoana.angajat;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AngajatRepository extends JpaRepository<Angajat, Long> {

    Optional<Angajat> findByCnp(String nome);

    Optional<Angajat> findByUsername(String username);

}
