package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Agence;

import java.util.List;

public interface IAgenceService {

    Agence addAgence(Agence agence);

    List<Agence> addAgences(List<Agence> agences);

    List<Agence> retrieveAllAgences();

    Agence retrieveAgence(Long id);

    Agence updateAgence(Long id, Agence agence);

    void removeAgence(Long id);
}
