package tn.esprit.examen.nomPrenomClasseExamen.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Ordre;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Portefeuille;

public interface IPortefeuilleRepository extends JpaRepository<Portefeuille, Integer> {

    Portefeuille findByReference(Integer reference);

}
