package tn.esprit.autoloc.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.AgenceRepository;
import tn.esprit.autoloc.repository.EquipementRepository;
import tn.esprit.autoloc.repository.VehiculeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private final VehiculeRepository vehiculeRepository;
    private final AgenceRepository agenceRepository;
    private final EquipementRepository equipementRepository;

    @Override
    @Transactional
    public Vehicule addVehicule(Vehicule vehicule) {
        rattacherAgence(vehicule);
        return vehiculeRepository.save(vehicule);
    }

    @Override
    @Transactional
    public List<Vehicule> addVehicules(List<Vehicule> vehicules) {
        vehicules.forEach(this::rattacherAgence);
        return vehiculeRepository.saveAll(vehicules);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Vehicule> retrieveAllVehicules() {
        return vehiculeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Vehicule retrieveVehicule(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vehicule introuvable avec l'id " + id));
    }

    @Override
    @Transactional
    public Vehicule updateVehicule(Long id, Vehicule vehicule) {
        if (!vehiculeRepository.existsById(id)) {
            throw new EntityNotFoundException("Vehicule introuvable avec l'id " + id);
        }
        rattacherAgence(vehicule);
        vehicule.setIdVehicule(id);
        return vehiculeRepository.saveAndFlush(vehicule);
    }

    @Override
    @Transactional
    public void removeVehicule(Long id) {
        if (!vehiculeRepository.existsById(id)) {
            throw new EntityNotFoundException("Vehicule introuvable avec l'id " + id);
        }
        vehiculeRepository.deleteById(id);
        vehiculeRepository.flush();
    }

    /**
     * Scénario 2 : un véhicule neuf est rattaché à une agence (ex. Sfax).
     * Relation @ManyToOne : Vehicule est le côté propriétaire, on renseigne donc vehicule.agence.
     */
    @Override
    @Transactional
    public Vehicule affecterVehicule(Long idVehicule, Long idAgence) {
        Vehicule vehicule = retrieveVehicule(idVehicule);
        Agence agence = agenceRepository.findById(idAgence)
                .orElseThrow(() -> new EntityNotFoundException("Agence introuvable avec l'id " + idAgence));
        vehicule.setAgence(agence);
        return vehiculeRepository.save(vehicule);
    }

    /**
     * Scénario 3 (étape 1) : l'agent installe un équipement (ex. siège bébé) sur le véhicule.
     * Relation @ManyToMany : Vehicule est le côté propriétaire (table vehicule_equipement),
     * on ajoute donc l'équipement dans vehicule.equipements.
     */
    @Override
    @Transactional
    public Vehicule affecterEquipement(Long idVehicule, Long idEquipement) {
        Vehicule vehicule = retrieveVehicule(idVehicule);
        Equipement equipement = findEquipement(idEquipement);
        if (vehicule.getEquipements().contains(equipement)) {
            throw new IllegalStateException("L'equipement " + idEquipement
                    + " est deja installe sur le vehicule " + idVehicule);
        }
        vehicule.getEquipements().add(equipement);
        return vehiculeRepository.save(vehicule);
    }

    /**
     * Scénario 3 (étape 2) : au retour du véhicule, l'agent retire l'équipement.
     */
    @Override
    @Transactional
    public Vehicule desaffecterEquipement(Long idVehicule, Long idEquipement) {
        Vehicule vehicule = retrieveVehicule(idVehicule);
        Equipement equipement = findEquipement(idEquipement);
        if (!vehicule.getEquipements().remove(equipement)) {
            throw new IllegalStateException("L'equipement " + idEquipement
                    + " n'est pas installe sur le vehicule " + idVehicule);
        }
        return vehiculeRepository.save(vehicule);
    }

    private Equipement findEquipement(Long idEquipement) {
        return equipementRepository.findById(idEquipement)
                .orElseThrow(() -> new EntityNotFoundException("Equipement introuvable avec l'id " + idEquipement));
    }

    // Remplace l'agence reçue en JSON ({"idAgence": 1}) par l'agence réelle de la base
    private void rattacherAgence(Vehicule vehicule) {
        Agence agence = vehicule.getAgence();
        if (agence == null) {
            return;
        }
        Long idAgence = agence.getIdAgence();
        if (idAgence == null) {
            throw new IllegalArgumentException("L'agence du véhicule doit contenir un idAgence");
        }
        vehicule.setAgence(agenceRepository.findById(idAgence)
                .orElseThrow(() -> new EntityNotFoundException("Agence introuvable avec l'id " + idAgence)));
    }
}
