package tn.esprit.examen.nomPrenomClasseExamen.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Action;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Client;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Ordre;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Portefeuille;
import tn.esprit.examen.nomPrenomClasseExamen.services.IServices;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("examen")
@RestController
public class ClientRestController {
    private final IServices services;

    @PostMapping("/a-addActions")
    public List<Action> addActions(@RequestBody  List<Action> listeActions){
        return  services.addActions(listeActions);
    }
    @PostMapping("/b-addPortefeuilleWithElements")
    public Portefeuille addPortefeuilleWithElements(@RequestBody Portefeuille portefeuille) {
        return services.addPortefeuilleWithElements(portefeuille);
    }
    @PostMapping("/c-addOrdreAndAffectToActionAndPortefeuille")
    public Ordre addOrdreAndAffectToActionAndPortefeuille(@RequestBody Ordre ordre, @RequestParam String symbol, @RequestParam Integer reference) {
        return services.addOrdreAndAffectToActionAndPortefeuille(ordre, symbol, reference);
    }

    @GetMapping("/d-listSymbolesActionsParPortefeuilleEtDateEmission")
    public List<String> listSymbolesActionsParPortefeuilleEtDateEmission( @RequestParam Integer referencePorteFeuille, @RequestParam String startDate, @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return services.listSymbolesActionsParPortefeuilleEtDateEmission(referencePorteFeuille, start, end);
    }


}
