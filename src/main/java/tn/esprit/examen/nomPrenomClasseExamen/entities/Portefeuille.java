package tn.esprit.examen.nomPrenomClasseExamen.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@Entity
public class Portefeuille implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer idPortefeuille;
    @Column(unique = true)
    Integer reference;
    float solde;

    @OneToMany(mappedBy = "portefeuille",cascade = CascadeType.ALL)
    List<ElementPortefeuille> elementPortefeuilles;

    @OneToMany(mappedBy = "portefeuille",cascade = CascadeType.ALL)
    List<Ordre> ordres;
}
