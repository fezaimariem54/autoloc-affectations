package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.service.IClientService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Clients", description = "Gestion des clients AutoLoc")
public class ClientController {

    private final IClientService iclient;

    @Operation(summary = "Ajouter un client")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Client créé"),
            @ApiResponse(responseCode = "409", description = "Doublon ou champ obligatoire manquant")
    })
    @PostMapping("/AddClient")
    @ResponseStatus(HttpStatus.CREATED)
    public Client addClient(@RequestBody Client client) {
        return iclient.addClient(client);
    }

    @Operation(summary = "Ajouter plusieurs clients en une seule requête")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Liste créée"),
            @ApiResponse(responseCode = "409", description = "Doublon ou champ obligatoire manquant")
    })
    @PostMapping("/addAllClient")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Client> addAllClient(@RequestBody List<Client> listeClient) {
        return iclient.addClients(listeClient);
    }

    @Operation(summary = "Lister tous les clients")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste retournée (éventuellement vide)")
    })
    @GetMapping("/GetClient")
    public List<Client> getClients() {
        return iclient.retrieveAllClients();
    }

    @Operation(summary = "Récupérer un client par son identifiant")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Trouvé"),
            @ApiResponse(responseCode = "404", description = "Identifiant inexistant")
    })
    @GetMapping("/GetClient/{id}")
    public Client getClientById(@PathVariable Long id) {
        return iclient.retrieveClient(id);
    }

    @Operation(summary = "Modifier un client")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Modifié"),
            @ApiResponse(responseCode = "404", description = "Identifiant inexistant"),
            @ApiResponse(responseCode = "409", description = "Doublon ou champ obligatoire manquant")
    })
    @PutMapping("/UpdateClient/{id}")
    public Client updateClient(@PathVariable Long id, @RequestBody Client client) {
        return iclient.updateClient(id, client);
    }

    @Operation(summary = "Supprimer un client")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Supprimé"),
            @ApiResponse(responseCode = "404", description = "Identifiant inexistant"),
            @ApiResponse(responseCode = "409", description = "Suppression impossible : encore référencé ailleurs")
    })
    @DeleteMapping("/DeleteClient/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClient(@PathVariable Long id) {
        iclient.removeClient(id);
    }
}
