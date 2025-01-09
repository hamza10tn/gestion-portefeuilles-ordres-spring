package tn.esprit.examen.nomPrenomClasseExamen.services;

import tn.esprit.examen.nomPrenomClasseExamen.entities.Action;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Client;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Ordre;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Portefeuille;

import java.time.LocalDate;
import java.util.List;

public interface IServices {
    Client add(Client client);
    void test();

    public List<Action> addActions(List<Action> listeActions);
    public Portefeuille addPortefeuilleWithElements(Portefeuille portefeuille);
    public Ordre addOrdreAndAffectToActionAndPortefeuille(Ordre ordre,String symbol, Integer reference);
    List<String> listSymbolesActionsParPortefeuilleEtDateEmission(Integer referencePorteFeuille, LocalDate startDate,LocalDate endDate);
    void checkOrdre();
}
