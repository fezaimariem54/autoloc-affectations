package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.service.IVehiculeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Véhicules", description = "Gestion de la flotte de véhicules AutoLoc")
public class VehiculeController {

    private final IVehiculeService ivehicule;

    @Operation(summary = "Ajouter un véhicule")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Véhicule créé"),
            @ApiResponse(responseCode = "404", description = "Agence indiquée introuvable"),
            @ApiResponse(responseCode = "409", description = "Doublon ou champ obligatoire manquant")
    })
    @PostMapping("/AddVehicule")
    @ResponseStatus(HttpStatus.CREATED)
    public Vehicule addVehicule(@RequestBody Vehicule vehicule) {
        return ivehicule.addVehicule(vehicule);
    }

    @Operation(summary = "Ajouter plusieurs véhicules en une seule requête")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Liste créée"),
            @ApiResponse(responseCode = "404", description = "Agence indiquée introuvable"),
            @ApiResponse(responseCode = "409", description = "Doublon ou champ obligatoire manquant")
    })
    @PostMapping("/addAllVehicule")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Vehicule> addAllVehicule(@RequestBody List<Vehicule> listeVehicule) {
        return ivehicule.addVehicules(listeVehicule);
    }

    @Operation(summary = "Lister tous les véhicules")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste retournée (éventuellement vide)")
    })
    @GetMapping("/GetVehicule")
    public List<Vehicule> getVehicules() {
        return ivehicule.retrieveAllVehicules();
    }

    @Operation(summary = "Récupérer un véhicule par son identifiant")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Trouvé"),
            @ApiResponse(responseCode = "404", description = "Identifiant inexistant")
    })
    @GetMapping("/GetVehicule/{id}")
    public Vehicule getVehiculeById(@PathVariable Long id) {
        return ivehicule.retrieveVehicule(id);
    }

    @Operation(summary = "Modifier un véhicule")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Modifié"),
            @ApiResponse(responseCode = "404", description = "Identifiant inexistant"),
            @ApiResponse(responseCode = "409", description = "Doublon ou champ obligatoire manquant")
    })
    @PutMapping("/UpdateVehicule/{id}")
    public Vehicule updateVehicule(@PathVariable Long id, @RequestBody Vehicule vehicule) {
        return ivehicule.updateVehicule(id, vehicule);
    }

    @Operation(summary = "Supprimer un véhicule")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Supprimé"),
            @ApiResponse(responseCode = "404", description = "Identifiant inexistant"),
            @ApiResponse(responseCode = "409", description = "Suppression impossible : encore référencé ailleurs")
    })
    @DeleteMapping("/DeleteVehicule/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVehicule(@PathVariable Long id) {
        ivehicule.removeVehicule(id);
    }

    // ===================== Affectations =====================

    @Operation(summary = "Scénario 2 : rattacher un véhicule à une agence",
            description = "Un véhicule neuf arrive et est rattaché à une agence (ex. Sfax).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Véhicule rattaché à l'agence"),
            @ApiResponse(responseCode = "404", description = "Véhicule ou agence introuvable")
    })
    @PutMapping("/affecterVehicule/{idVehicule}/{idAgence}")
    public Vehicule affecterVehicule(@PathVariable Long idVehicule, @PathVariable Long idAgence) {
        return ivehicule.affecterVehicule(idVehicule, idAgence);
    }

    @Operation(summary = "Scénario 3 : installer un équipement sur un véhicule",
            description = "Un client demande un siège bébé : l'agent l'installe sur le véhicule.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Équipement installé"),
            @ApiResponse(responseCode = "404", description = "Véhicule ou équipement introuvable"),
            @ApiResponse(responseCode = "409", description = "Équipement déjà installé sur ce véhicule")
    })
    @PutMapping("/affecterEquipement/{idVehicule}/{idEquipement}")
    public Vehicule affecterEquipement(@PathVariable Long idVehicule, @PathVariable Long idEquipement) {
        return ivehicule.affecterEquipement(idVehicule, idEquipement);
    }

    @Operation(summary = "Scénario 3 : retirer un équipement d'un véhicule",
            description = "Au retour du véhicule, l'agent retire le siège bébé.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Équipement retiré"),
            @ApiResponse(responseCode = "404", description = "Véhicule ou équipement introuvable"),
            @ApiResponse(responseCode = "409", description = "Équipement non installé sur ce véhicule")
    })
    @PutMapping("/desaffecterEquipement/{idVehicule}/{idEquipement}")
    public Vehicule desaffecterEquipement(@PathVariable Long idVehicule, @PathVariable Long idEquipement) {
        return ivehicule.desaffecterEquipement(idVehicule, idEquipement);
    }
}
