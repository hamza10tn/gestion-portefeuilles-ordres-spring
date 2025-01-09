package tn.esprit.examen.nomPrenomClasseExamen.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Client;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Ordre;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Status;

import java.time.LocalDate;
import java.util.List;

public interface IOrdreRepository extends JpaRepository<Ordre, Integer> {


    @Query("SELECT DISTINCT a.symbole " +
            "FROM Action a JOIN a.ordres o " +
            "WHERE o.portefeuille.reference = :referencePorteFeuille " +
            "AND a.dateEmission BETWEEN :startDate AND :endDate")
    List<String> findSymbolsByPortefeuilleAndDateRange(
            @Param("referencePorteFeuille") Integer referencePorteFeuille,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
List<Ordre> findByStatus(Status status);
}
