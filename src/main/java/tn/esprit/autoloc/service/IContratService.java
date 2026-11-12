package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Contrat;

import java.util.List;

public interface IContratService {

    Contrat addContrat(Contrat contrat);

    List<Contrat> addContrats(List<Contrat> contrats);

    List<Contrat> retrieveAllContrats();

    Contrat retrieveContrat(Long id);

    Contrat updateContrat(Long id, Contrat contrat);

    void removeContrat(Long id);
}
