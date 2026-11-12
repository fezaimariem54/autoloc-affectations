package tn.esprit.autoloc.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.repository.AgenceRepository;
import tn.esprit.autoloc.repository.EmployeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeServiceImpl implements IEmployeService {

    private final EmployeRepository employeRepository;
    private final AgenceRepository agenceRepository;

    @Override
    @Transactional
    public Employe addEmploye(Employe employe) {
        return employeRepository.save(employe);
    }

    @Override
    @Transactional
    public List<Employe> addEmployes(List<Employe> employes) {
        return employeRepository.saveAll(employes);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employe> retrieveAllEmployes() {
        return employeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Employe retrieveEmploye(Long id) {
        return employeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employe introuvable avec l'id " + id));
    }

    @Override
    @Transactional
    public Employe updateEmploye(Long id, Employe employe) {
        if (!employeRepository.existsById(id)) {
            throw new EntityNotFoundException("Employe introuvable avec l'id " + id);
        }
        employe.setIdEmploye(id);
        return employeRepository.saveAndFlush(employe);
    }

    @Override
    @Transactional
    public void removeEmploye(Long id) {
        if (!employeRepository.existsById(id)) {
            throw new EntityNotFoundException("Employe introuvable avec l'id " + id);
        }
        employeRepository.deleteById(id);
        employeRepository.flush();
    }

    /**
     * Scénario 1 (étape 1) : l'employé quitte son agence actuelle.
     * Un employé ne peut appartenir qu'à une seule agence : avant toute mutation
     * il faut d'abord le désaffecter.
     */
    @Override
    @Transactional
    public Employe desaffecterEmploye(Long idEmploye) {
        Employe employe = retrieveEmploye(idEmploye);
        if (employe.getAgence() == null) {
            throw new IllegalStateException("L'employe " + idEmploye + " n'est affecte a aucune agence");
        }
        employe.setAgence(null);   // côté propriétaire (@ManyToOne) : met id_agence à NULL
        return employeRepository.save(employe);
    }

    /**
     * Scénario 1 (étape 2) : l'employé est affecté à sa nouvelle agence (ex. Tunis).
     * Refusé (409) s'il appartient encore à une agence : il faut le désaffecter avant.
     */
    @Override
    @Transactional
    public Employe affecterEmploye(Long idEmploye, Long idAgence) {
        Employe employe = retrieveEmploye(idEmploye);
        Agence agence = agenceRepository.findById(idAgence)
                .orElseThrow(() -> new EntityNotFoundException("Agence introuvable avec l'id " + idAgence));
        if (employe.getAgence() != null) {
            throw new IllegalStateException("L'employe " + idEmploye + " appartient deja a l'agence "
                    + employe.getAgence().getIdAgence() + " : il faut d'abord le desaffecter");
        }
        employe.setAgence(agence); // côté propriétaire (@ManyToOne) : renseigne id_agence
        return employeRepository.save(employe);
    }
}
