package tn.esprit.autoloc.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.repository.ClientRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService {

    private final ClientRepository clientRepository;

    @Override
    @Transactional
    public Client addClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    @Transactional
    public List<Client> addClients(List<Client> clients) {
        return clientRepository.saveAll(clients);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Client> retrieveAllClients() {
        return clientRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Client retrieveClient(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client introuvable avec l'id " + id));
    }

    @Override
    @Transactional
    public Client updateClient(Long id, Client client) {
        if (!clientRepository.existsById(id)) {
            throw new EntityNotFoundException("Client introuvable avec l'id " + id);
        }
        client.setIdClient(id);
        return clientRepository.saveAndFlush(client);
    }

    @Override
    @Transactional
    public void removeClient(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new EntityNotFoundException("Client introuvable avec l'id " + id);
        }
        clientRepository.deleteById(id);
        clientRepository.flush();
    }
}
