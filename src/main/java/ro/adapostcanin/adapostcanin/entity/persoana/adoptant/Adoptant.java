package ro.adapostcanin.adapostcanin.entity.persoana.adoptant;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import ro.adapostcanin.adapostcanin.entity.persoana.Persoana;
import ro.adapostcanin.adapostcanin.entity.animal.Animal;

import java.util.List;

@Entity
@Getter
@Setter
public class Adoptant extends Persoana {
    @OneToMany(mappedBy = "adoptant", cascade = CascadeType.ALL)
    List<Animal> animaleAdoptate;
}