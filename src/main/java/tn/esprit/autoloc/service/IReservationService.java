package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Reservation;

import java.util.List;

public interface IReservationService {

    Reservation addReservation(Reservation reservation);

    List<Reservation> addReservations(List<Reservation> reservations);

    List<Reservation> retrieveAllReservations();

    Reservation retrieveReservation(Long id);

    Reservation updateReservation(Long id, Reservation reservation);

    void removeReservation(Long id);
}
