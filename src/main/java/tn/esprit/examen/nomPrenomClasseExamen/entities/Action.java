package tn.esprit.examen.nomPrenomClasseExamen.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@Entity
public class Action implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer idAction;
    float prixAchatActuel;
    float prixVenteActuel;
    Integer volume;
    LocalDate dateEmission;
    @Column(unique = true)
    String symbole;

    @OneToMany(mappedBy = "action",cascade = CascadeType.ALL)
    List<Ordre> ordres;

}
