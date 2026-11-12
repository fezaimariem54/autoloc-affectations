package tn.esprit.autoloc.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.repository.ContratRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements IContratService {

    private final ContratRepository contratRepository;

    @Override
    @Transactional
    public Contrat addContrat(Contrat contrat) {
        return contratRepository.save(contrat);
    }

    @Override
    @Transactional
    public List<Contrat> addContrats(List<Contrat> contrats) {
        return contratRepository.saveAll(contrats);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contrat> retrieveAllContrats() {
        return contratRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Contrat retrieveContrat(Long id) {
        return contratRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Contrat introuvable avec l'id " + id));
    }

    @Override
    @Transactional
    public Contrat updateContrat(Long id, Contrat contrat) {
        if (!contratRepository.existsById(id)) {
            throw new EntityNotFoundException("Contrat introuvable avec l'id " + id);
        }
        contrat.setIdContrat(id);
        return contratRepository.saveAndFlush(contrat);
    }

    @Override
    @Transactional
    public void removeContrat(Long id) {
        if (!contratRepository.existsById(id)) {
            throw new EntityNotFoundException("Contrat introuvable avec l'id " + id);
        }
        contratRepository.deleteById(id);
        contratRepository.flush();
    }
}
