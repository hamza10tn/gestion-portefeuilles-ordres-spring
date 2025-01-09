package tn.esprit.examen.nomPrenomClasseExamen.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Client;
import tn.esprit.examen.nomPrenomClasseExamen.entities.ElementPortefeuille;

public interface IElementPortefeuilleRepository extends JpaRepository<ElementPortefeuille, Integer> {
}
