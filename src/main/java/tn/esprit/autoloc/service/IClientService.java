package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Client;

import java.util.List;

public interface IClientService {

    Client addClient(Client client);

    List<Client> addClients(List<Client> clients);

    List<Client> retrieveAllClients();

    Client retrieveClient(Long id);

    Client updateClient(Long id, Client client);

    void removeClient(Long id);
}
