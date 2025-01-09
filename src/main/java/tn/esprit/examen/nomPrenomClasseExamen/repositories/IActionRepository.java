package tn.esprit.examen.nomPrenomClasseExamen.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Action;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Client;

public interface IActionRepository extends JpaRepository<Action, Integer> {
    Action findBySymbole(String symbole);
}
