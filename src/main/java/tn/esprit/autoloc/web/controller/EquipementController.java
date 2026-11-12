package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.service.IEquipementService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Équipements", description = "Gestion des équipements AutoLoc")
public class EquipementController {

    private final IEquipementService iequipement;

    @Operation(summary = "Ajouter un équipement")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Équipement créé"),
            @ApiResponse(responseCode = "409", description = "Doublon ou champ obligatoire manquant")
    })
    @PostMapping("/AddEquipement")
    @ResponseStatus(HttpStatus.CREATED)
    public Equipement addEquipement(@RequestBody Equipement equipement) {
        return iequipement.addEquipement(equipement);
    }

    @Operation(summary = "Ajouter plusieurs équipements en une seule requête")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Liste créée"),
            @ApiResponse(responseCode = "409", description = "Doublon ou champ obligatoire manquant")
    })
    @PostMapping("/addAllEquipement")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Equipement> addAllEquipement(@RequestBody List<Equipement> listeEquipement) {
        return iequipement.addEquipements(listeEquipement);
    }

    @Operation(summary = "Lister tous les équipements")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste retournée (éventuellement vide)")
    })
    @GetMapping("/GetEquipement")
    public List<Equipement> getEquipements() {
        return iequipement.retrieveAllEquipements();
    }

    @Operation(summary = "Récupérer un équipement par son identifiant")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Trouvé"),
            @ApiResponse(responseCode = "404", description = "Identifiant inexistant")
    })
    @GetMapping("/GetEquipement/{id}")
    public Equipement getEquipementById(@PathVariable Long id) {
        return iequipement.retrieveEquipement(id);
    }

    @Operation(summary = "Modifier un équipement")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Modifié"),
            @ApiResponse(responseCode = "404", description = "Identifiant inexistant"),
            @ApiResponse(responseCode = "409", description = "Doublon ou champ obligatoire manquant")
    })
    @PutMapping("/UpdateEquipement/{id}")
    public Equipement updateEquipement(@PathVariable Long id, @RequestBody Equipement equipement) {
        return iequipement.updateEquipement(id, equipement);
    }

    @Operation(summary = "Supprimer un équipement")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Supprimé"),
            @ApiResponse(responseCode = "404", description = "Identifiant inexistant"),
            @ApiResponse(responseCode = "409", description = "Suppression impossible : encore référencé ailleurs")
    })
    @DeleteMapping("/DeleteEquipement/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEquipement(@PathVariable Long id) {
        iequipement.removeEquipement(id);
    }
}
