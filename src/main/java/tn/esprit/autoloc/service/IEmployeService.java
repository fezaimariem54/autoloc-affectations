package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Employe;

import java.util.List;

public interface IEmployeService {

    Employe addEmploye(Employe employe);

    List<Employe> addEmployes(List<Employe> employes);

    List<Employe> retrieveAllEmployes();

    Employe retrieveEmploye(Long id);

    Employe updateEmploye(Long id, Employe employe);

    void removeEmploye(Long id);

    // Scénario 1 : mutation d'un agent (désaffectation puis affectation)
    Employe desaffecterEmploye(Long idEmploye);

    Employe affecterEmploye(Long idEmploye, Long idAgence);
}
