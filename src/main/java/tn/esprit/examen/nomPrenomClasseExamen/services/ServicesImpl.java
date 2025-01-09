package tn.esprit.examen.nomPrenomClasseExamen.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.entities.*;
import tn.esprit.examen.nomPrenomClasseExamen.repositories.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ServicesImpl implements IServices {

    private final IClientRepository clientRepository;
    private final IPortefeuilleRepository portefeuilleRepository;
    private final IOrdreRepository ordreRepository;
    private final IElementPortefeuilleRepository elementPortefeuilleRepository;
    private final IActionRepository actionRepository;

    @Override
    public Client add(Client client) {
        return clientRepository.save(client);
    }

    @Scheduled(cron = "*/15 * * * * *")
    @Override
    public void test() {
        log.info("testing");
    }

    ////////////////////////////

    @Override
    public List<Action> addActions(List<Action> listeActions) {
        return actionRepository.saveAll(listeActions);
    }

    @Override
    public Portefeuille addPortefeuilleWithElements(Portefeuille portefeuille) {
        portefeuille = portefeuilleRepository.save(portefeuille);
        for (ElementPortefeuille element : portefeuille.getElementPortefeuilles()) {
            element.setPortefeuille(portefeuille);
            elementPortefeuilleRepository.save(element);
        }
        return portefeuille;
    }


    @Override
    public Ordre addOrdreAndAffectToActionAndPortefeuille(Ordre ordre, String symbol, Integer reference) {

        Action action = actionRepository.findBySymbole(symbol);
        Portefeuille portefeuille = portefeuilleRepository.findByReference(reference);
        if (action != null && portefeuille != null) {
            ordre.setAction(action);
            ordre.setPortefeuille(portefeuille);
            ordre.setStatus(Status.EN_ATTENTE);
            ordre.setDateCreation(LocalDate.now());
            if (ordre.getTypeOrdre().equals("ACHAT")) {
                ordre.setMontant(ordre.getVolume() * action.getPrixAchatActuel());
            } else if (ordre.getTypeOrdre().equals("VENTE")) {
                ordre.setMontant(ordre.getVolume() * action.getPrixVenteActuel());
            }
            return ordreRepository.save(ordre);
        }
        return null;
    }


    @Override
    public List<String> listSymbolesActionsParPortefeuilleEtDateEmission(Integer referencePorteFeuille, LocalDate startDate, LocalDate endDate) {
        Portefeuille portefeuille = portefeuilleRepository.findByReference(referencePorteFeuille);
        if (portefeuille != null) {
            return ordreRepository.findSymbolsByPortefeuilleAndDateRange(portefeuille.getIdPortefeuille(), startDate, endDate);
        }
        return Collections.emptyList();
    }


    @Override
    @Scheduled(cron = "*/120 * * * * *") // Exécution toutes les 2 minutes
    public void checkOrdre() {
        // Récupérer tous les ordres en attente
        List<Ordre> ordresEnAttente = ordreRepository.findByStatus(Status.EN_ATTENTE);

        for (Ordre ordre : ordresEnAttente) {
            Portefeuille portefeuille = ordre.getPortefeuille();
            Action action = ordre.getAction();

            if (ordre.getTypeOrdre() == TypeOrdre.ACHAT) {
                // Calculer le montant total de l'achat
                float montantTotal = ordre.getVolume() * action.getPrixAchatActuel();

                // Vérifier les conditions pour un ordre d'achat
                if (action.getVolume() >= ordre.getVolume() && portefeuille.getSolde() >= montantTotal) {
                    // Mise à jour du solde du portefeuille et du volume d'actions
                    portefeuille.setSolde(portefeuille.getSolde() - montantTotal);
                    action.setVolume(action.getVolume() - ordre.getVolume());

                    // Mise à jour de l'ordre
                    ordre.setStatus(Status.EXECUTE);
                    ordre.setDateExecution(LocalDate.now());

                    // Sauvegarde
                    portefeuilleRepository.save(portefeuille);
                    actionRepository.save(action);
                    ordreRepository.save(ordre);

                    log.info("Ordre d'achat exécuté : {}", ordre.getIdAction());
                } else {
                    log.warn("Ordre d'achat échoué : Solde insuffisant ou volume d'action indisponible pour l'ordre {}", ordre.getIdAction());
                }
            } else if (ordre.getTypeOrdre() == TypeOrdre.VENTE) {
                // Vérification pour un ordre de vente
                Optional<ElementPortefeuille> elementPortefeuilleOpt = portefeuille.getElementPortefeuilles()
                        .stream()
                        .filter(e -> e.getSymbole().equals(action.getSymbole()))
                        .findFirst();

                if (elementPortefeuilleOpt.isPresent()) {
                    ElementPortefeuille elementPortefeuille = elementPortefeuilleOpt.get();

                    // Vérifier si le portefeuille contient suffisamment d'actions pour la vente
                    if (elementPortefeuille.getNombreActions() >= ordre.getVolume()) {
                        // Mise à jour du solde et du volume d'actions dans le portefeuille
                        float montantVente = ordre.getVolume() * action.getPrixVenteActuel();
                        portefeuille.setSolde(portefeuille.getSolde() + montantVente);
                        elementPortefeuille.setNombreActions(elementPortefeuille.getNombreActions() - ordre.getVolume());

                        // Mise à jour de l'ordre
                        ordre.setStatus(Status.EXECUTE);
                        ordre.setDateExecution(LocalDate.now());

                        // Sauvegarde
                        portefeuilleRepository.save(portefeuille);
                        elementPortefeuilleRepository.save(elementPortefeuille);
                        ordreRepository.save(ordre);

                        log.info("Ordre de vente exécuté : {}", ordre.getIdAction());
                    } else {
                        log.warn("Ordre de vente échoué : Nombre d'actions insuffisant pour l'ordre {}", ordre.getIdAction());
                    }
                } else {
                    log.warn("Ordre de vente échoué : Aucune action correspondante dans le portefeuille pour l'ordre {}", ordre.getIdAction());
                }
            }
        }
    }

}