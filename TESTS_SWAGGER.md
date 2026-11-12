# Tests Swagger – Affectations (AutoLoc)

## Lancer l'application

1. Démarrer MySQL (XAMPP).
2. Lancer `AutolocApiApplication` depuis IntelliJ (ou `mvn spring-boot:run`).
3. Ouvrir Swagger UI : **http://localhost:8082/autoloc/swagger-ui/index.html**

Pour chaque requête dans Swagger : cliquer sur l'endpoint → **Try it out** → remplir → **Execute**.

> Les id ci-dessous (1, 2…) supposent une base vide. Si ta base contient déjà des données,
> utilise les id renvoyés dans les réponses.

---

## 0. Préparer les données

**Agences** → `POST /addAllAgence`
```json
[
  { "nom": "AutoLoc Sousse", "ville": "Sousse", "adresse": "Av. Habib Bourguiba", "telephone": "73000000" },
  { "nom": "AutoLoc Tunis",  "ville": "Tunis",  "adresse": "Rue de Marseille",    "telephone": "71000000" },
  { "nom": "AutoLoc Sfax",   "ville": "Sfax",   "adresse": "Route de Tunis km 2", "telephone": "74000000" }
]
```
→ Sousse = 1, Tunis = 2, Sfax = 3

**Employé** → `POST /AddEmploye`
```json
{ "nom": "Ben Ali", "prenom": "Sami", "role": "AGENT" }
```
→ employé 1 (sans agence)

> `role` : AGENT ou MANAGER.

**Véhicule** → `POST /AddVehicule`
```json
{
  "immatriculation": "245 TU 9999",
  "marque": "Peugeot",
  "modele": "208",
  "categorie": "CITADINE",
  "tarifJournalier": 90.00,
  "statut": "DISPONIBLE"
}
```
→ véhicule 1 (sans agence). `categorie` : CITADINE, BERLINE, SUV, UTILITAIRE — `statut` : DISPONIBLE, LOUE, MAINTENANCE.

**Équipement** → `POST /AddEquipement`
```json
{ "libelle": "Siège bébé" }
```
→ équipement 1

---

## Scénario 1 – Mutation d'un agent vers Tunis (`@ManyToOne` Employe → Agence)

| # | Requête | Résultat attendu |
|---|---------|------------------|
| 1 | `PUT /affecterEmploye/1/1` | **200** – l'employé est à Sousse (`agence.idAgence = 1`) |
| 2 | `PUT /affecterEmploye/1/2` | **409** – « appartient déjà à l'agence 1 : il faut d'abord le désaffecter » |
| 3 | `PUT /desaffecterEmploye/1` | **200** – `agence = null` |
| 4 | `PUT /affecterEmploye/1/2` | **200** – l'employé est à Tunis (`agence.idAgence = 2`) |
| 5 | `GET /GetEmploye/1` | confirme l'agence Tunis |

Cas d'erreur : `PUT /desaffecterEmploye/1` deux fois de suite → **409** ; `PUT /affecterEmploye/999/2` → **404**.

## Scénario 2 – Un véhicule neuf rattaché à Sfax (`@ManyToOne` Vehicule → Agence)

| # | Requête | Résultat attendu |
|---|---------|------------------|
| 1 | `PUT /affecterVehicule/1/3` | **200** – `agence.idAgence = 3` (Sfax) |
| 2 | `GET /GetVehicule/1` | confirme l'agence Sfax |

Cas d'erreur : `PUT /affecterVehicule/1/999` → **404** (agence introuvable).

## Scénario 3 – Siège bébé (`@ManyToMany` Vehicule ↔ Equipement)

| # | Requête | Résultat attendu |
|---|---------|------------------|
| 1 | `PUT /affecterEquipement/1/1` | **200** – `equipements` contient « Siège bébé » |
| 2 | `PUT /affecterEquipement/1/1` | **409** – déjà installé |
| 3 | `PUT /desaffecterEquipement/1/1` | **200** – `equipements` est vide |
| 4 | `PUT /desaffecterEquipement/1/1` | **409** – pas installé |

Vérification en base (phpMyAdmin) : la table de jointure `vehicule_equipement` contient la ligne
`(1, 1)` après l'étape 1 et plus rien après l'étape 3.

---

## Ce qui a été ajouté au projet

- `IEmployeService` / `EmployeServiceImpl` : `desaffecterEmploye`, `affecterEmploye`
- `IVehiculeService` / `VehiculeServiceImpl` : `affecterVehicule`, `affecterEquipement`, `desaffecterEquipement`
- `EmployeController` (nouveau) : CRUD + `/desaffecterEmploye/{idEmploye}` + `/affecterEmploye/{idEmploye}/{idAgence}`
- `EquipementController` (nouveau, il manquait alors que la collection Postman l'utilisait) : CRUD
- `VehiculeController` : `/affecterVehicule/{idVehicule}/{idAgence}`, `/affecterEquipement/{idVehicule}/{idEquipement}`, `/desaffecterEquipement/{idVehicule}/{idEquipement}`
- `GlobalExceptionHandler` : 404 (introuvable), 409 (règle métier / doublon), 400 (donnée invalide)
- `pom.xml` : suppression de la dépendance springdoc en double (version 2.1.0)

Les associations existaient déjà dans les entités :
- `Employe.agence` et `Vehicule.agence` : `@ManyToOne` (côté propriétaire, colonne `id_agence`)
- `Vehicule.equipements` : `@ManyToMany` propriétaire (table `vehicule_equipement`), `Equipement.vehicules` : côté inverse (`mappedBy`)
