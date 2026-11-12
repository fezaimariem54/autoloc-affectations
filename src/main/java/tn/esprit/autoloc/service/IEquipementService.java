package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Equipement;

import java.util.List;

public interface IEquipementService {

    Equipement addEquipement(Equipement equipement);

    List<Equipement> addEquipements(List<Equipement> equipements);

    List<Equipement> retrieveAllEquipements();

    Equipement retrieveEquipement(Long id);

    Equipement updateEquipement(Long id, Equipement equipement);

    void removeEquipement(Long id);
}
