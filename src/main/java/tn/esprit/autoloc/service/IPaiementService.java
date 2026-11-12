package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Paiement;

import java.util.List;

public interface IPaiementService {

    Paiement addPaiement(Paiement paiement);

    List<Paiement> addPaiements(List<Paiement> paiements);

    List<Paiement> retrieveAllPaiements();

    Paiement retrievePaiement(Long id);

    Paiement updatePaiement(Long id, Paiement paiement);

    void removePaiement(Long id);
}
