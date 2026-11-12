package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Maintenance;

import java.util.List;

public interface IMaintenanceService {

    Maintenance addMaintenance(Maintenance maintenance);

    List<Maintenance> addMaintenances(List<Maintenance> maintenances);

    List<Maintenance> retrieveAllMaintenances();

    Maintenance retrieveMaintenance(Long id);

    Maintenance updateMaintenance(Long id, Maintenance maintenance);

    void removeMaintenance(Long id);
}
