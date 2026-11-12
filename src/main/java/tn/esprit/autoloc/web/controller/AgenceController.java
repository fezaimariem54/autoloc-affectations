package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.service.IAgenceService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Agences", description = "Gestion des agences AutoLoc")
public class AgenceController {

    private final IAgenceService iagence;

    @Operation(summary = "Ajouter une agence")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Agence créée"),
            @ApiResponse(responseCode = "409", description = "Doublon ou champ obligatoire manquant")
    })
    @PostMapping("/AddAgence")
    @ResponseStatus(HttpStatus.CREATED)
    public Agence addAgence(@RequestBody Agence agence) {
        return iagence.addAgence(agence);
    }

    @Operation(summary = "Ajouter plusieurs agences en une seule requête")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Liste créée"),
            @ApiResponse(responseCode = "409", description = "Doublon ou champ obligatoire manquant")
    })
    @PostMapping("/addAllAgence")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Agence> addAllAgence(@RequestBody List<Agence> listeAgence) {
        return iagence.addAgences(listeAgence);
    }

    @Operation(summary = "Lister toutes les agences")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste retournée (éventuellement vide)")
    })
    @GetMapping("/GetAgence")
    public List<Agence> getAgences() {
        return iagence.retrieveAllAgences();
    }

    @Operation(summary = "Récupérer une agence par son identifiant")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Trouvé"),
            @ApiResponse(responseCode = "404", description = "Identifiant inexistant")
    })
    @GetMapping("/GetAgence/{id}")
    public Agence getAgenceById(@PathVariable Long id) {
        return iagence.retrieveAgence(id);
    }

    @Operation(summary = "Modifier une agence")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Modifié"),
            @ApiResponse(responseCode = "404", description = "Identifiant inexistant"),
            @ApiResponse(responseCode = "409", description = "Doublon ou champ obligatoire manquant")
    })
    @PutMapping("/UpdateAgence/{id}")
    public Agence updateAgence(@PathVariable Long id, @RequestBody Agence agence) {
        return iagence.updateAgence(id, agence);
    }

    @Operation(summary = "Supprimer une agence")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Supprimé"),
            @ApiResponse(responseCode = "404", description = "Identifiant inexistant"),
            @ApiResponse(responseCode = "409", description = "Suppression impossible : encore référencé ailleurs")
    })
    @DeleteMapping("/DeleteAgence/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAgence(@PathVariable Long id) {
        iagence.removeAgence(id);
    }
}
