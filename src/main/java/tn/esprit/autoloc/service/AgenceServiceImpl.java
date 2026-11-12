package tn.esprit.autoloc.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.repository.AgenceRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgenceServiceImpl implements IAgenceService {

    private final AgenceRepository agenceRepository;

    @Override
    @Transactional
    public Agence addAgence(Agence agence) {
        return agenceRepository.save(agence);
    }

    @Override
    @Transactional
    public List<Agence> addAgences(List<Agence> agences) {
        return agenceRepository.saveAll(agences);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Agence> retrieveAllAgences() {
        return agenceRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Agence retrieveAgence(Long id) {
        return agenceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agence introuvable avec l'id " + id));
    }

    @Override
    @Transactional
    public Agence updateAgence(Long id, Agence agence) {
        if (!agenceRepository.existsById(id)) {
            throw new EntityNotFoundException("Agence introuvable avec l'id " + id);
        }
        agence.setIdAgence(id);
        return agenceRepository.saveAndFlush(agence);
    }

    @Override
    @Transactional
    public void removeAgence(Long id) {
        if (!agenceRepository.existsById(id)) {
            throw new EntityNotFoundException("Agence introuvable avec l'id " + id);
        }
        agenceRepository.deleteById(id);
        agenceRepository.flush();
    }
}
