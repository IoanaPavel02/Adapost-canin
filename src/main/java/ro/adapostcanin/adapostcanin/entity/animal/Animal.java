package ro.adapostcanin.adapostcanin.entity.animal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ro.adapostcanin.adapostcanin.entity.fisamedicala.FisaMedicala;
import ro.adapostcanin.adapostcanin.entity.mancare.Mancare;
import ro.adapostcanin.adapostcanin.entity.persoana.adoptant.Adoptant;
import ro.adapostcanin.adapostcanin.entity.persoana.angajat.Angajat;

import java.util.List;

@Entity
@Getter
@Setter
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String nume;

    @ManyToMany(mappedBy = "animale", cascade = CascadeType.ALL)
    List<Angajat> angajati;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "adoptant_id")
    Adoptant adoptant;

    @OneToOne(mappedBy = "animal", cascade = CascadeType.ALL, optional = false)
    FisaMedicala fisaMedicala;

    @ManyToMany(mappedBy = "animale", cascade = CascadeType.ALL)
    List<Mancare> mancaruri;
}

