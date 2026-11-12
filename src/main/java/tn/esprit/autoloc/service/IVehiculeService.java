package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Vehicule;

import java.util.List;

public interface IVehiculeService {

    Vehicule addVehicule(Vehicule vehicule);

    List<Vehicule> addVehicules(List<Vehicule> vehicules);

    List<Vehicule> retrieveAllVehicules();

    Vehicule retrieveVehicule(Long id);

    Vehicule updateVehicule(Long id, Vehicule vehicule);

    void removeVehicule(Long id);

    // Scénario 2 : rattacher un véhicule à une agence
    Vehicule affecterVehicule(Long idVehicule, Long idAgence);

    // Scénario 3 : installer / retirer un équipement sur un véhicule
    Vehicule affecterEquipement(Long idVehicule, Long idEquipement);

    Vehicule desaffecterEquipement(Long idVehicule, Long idEquipement);
}
