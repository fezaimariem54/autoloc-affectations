package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.service.IEmployeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Employés", description = "Gestion des employés AutoLoc")
public class EmployeController {

    private final IEmployeService iemploye;

    @Operation(summary = "Ajouter un employé")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Employé créé"),
            @ApiResponse(responseCode = "409", description = "Doublon ou champ obligatoire manquant")
    })
    @PostMapping("/AddEmploye")
    @ResponseStatus(HttpStatus.CREATED)
    public Employe addEmploye(@RequestBody Employe employe) {
        return iemploye.addEmploye(employe);
    }

    @Operation(summary = "Ajouter plusieurs employés en une seule requête")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Liste créée"),
            @ApiResponse(responseCode = "409", description = "Doublon ou champ obligatoire manquant")
    })
    @PostMapping("/addAllEmploye")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Employe> addAllEmploye(@RequestBody List<Employe> listeEmploye) {
        return iemploye.addEmployes(listeEmploye);
    }

    @Operation(summary = "Lister tous les employés")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste retournée (éventuellement vide)")
    })
    @GetMapping("/GetEmploye")
    public List<Employe> getEmployes() {
        return iemploye.retrieveAllEmployes();
    }

    @Operation(summary = "Récupérer un employé par son identifiant")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Trouvé"),
            @ApiResponse(responseCode = "404", description = "Identifiant inexistant")
    })
    @GetMapping("/GetEmploye/{id}")
    public Employe getEmployeById(@PathVariable Long id) {
        return iemploye.retrieveEmploye(id);
    }

    @Operation(summary = "Modifier un employé")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Modifié"),
            @ApiResponse(responseCode = "404", description = "Identifiant inexistant"),
            @ApiResponse(responseCode = "409", description = "Doublon ou champ obligatoire manquant")
    })
    @PutMapping("/UpdateEmploye/{id}")
    public Employe updateEmploye(@PathVariable Long id, @RequestBody Employe employe) {
        return iemploye.updateEmploye(id, employe);
    }

    @Operation(summary = "Supprimer un employé")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Supprimé"),
            @ApiResponse(responseCode = "404", description = "Identifiant inexistant"),
            @ApiResponse(responseCode = "409", description = "Suppression impossible : encore référencé ailleurs")
    })
    @DeleteMapping("/DeleteEmploye/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmploye(@PathVariable Long id) {
        iemploye.removeEmploye(id);
    }

    // ===================== Scénario 1 : mutation d'un agent =====================

    @Operation(summary = "Scénario 1 (étape 1) : désaffecter un employé de son agence",
            description = "Un employé ne peut appartenir qu'à une seule agence : un transfert passe d'abord par une désaffectation.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employé désaffecté (agence = null)"),
            @ApiResponse(responseCode = "404", description = "Employé introuvable"),
            @ApiResponse(responseCode = "409", description = "L'employé n'est affecté à aucune agence")
    })
    @PutMapping("/desaffecterEmploye/{idEmploye}")
    public Employe desaffecterEmploye(@PathVariable Long idEmploye) {
        return iemploye.desaffecterEmploye(idEmploye);
    }

    @Operation(summary = "Scénario 1 (étape 2) : affecter un employé à une agence",
            description = "Le service RH mute l'agent vers une agence (ex. Tunis). L'employé doit être désaffecté au préalable.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employé affecté à l'agence"),
            @ApiResponse(responseCode = "404", description = "Employé ou agence introuvable"),
            @ApiResponse(responseCode = "409", description = "L'employé appartient encore à une agence")
    })
    @PutMapping("/affecterEmploye/{idEmploye}/{idAgence}")
    public Employe affecterEmploye(@PathVariable Long idEmploye, @PathVariable Long idAgence) {
        return iemploye.affecterEmploye(idEmploye, idAgence);
    }
}
