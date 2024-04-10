package ro.adapostcanin.adapostcanin.entity.persoana;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
public abstract class Persoana {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    protected Long id;
    @Setter
    protected String nume;
    @Setter
    protected String prenume;
    @Setter
    protected String cnp;
}
