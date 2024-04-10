package ro.adapostcanin.adapostcanin.entity.persoana.angajat;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import ro.adapostcanin.adapostcanin.config.UserType;
import ro.adapostcanin.adapostcanin.entity.animal.Animal;
import ro.adapostcanin.adapostcanin.entity.persoana.Persoana;

import java.util.List;

@Entity
@Getter
@Setter
public class Angajat extends Persoana {

    @NotBlank
    String username;
    @NotBlank
    String password;
    @Enumerated(EnumType.STRING)
    UserType role;

    @ManyToMany
    List<Animal> animale;
}
