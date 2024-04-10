package ro.adapostcanin.adapostcanin.entity.fisamedicala;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ro.adapostcanin.adapostcanin.entity.animal.Animal;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class FisaMedicala {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank
    String detalii;

    @Temporal(TemporalType.DATE)
    Date inceputSimptome;

    @OneToOne(optional = false)
    @JoinColumn(name = "animal_id")
    Animal animal;

    public FisaMedicala(String detalii) {
        this.detalii = detalii;
    }
}
