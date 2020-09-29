package net.javaguides.springboot.springsecurity.service;

import net.javaguides.springboot.springsecurity.model.Reservation;
import net.javaguides.springboot.springsecurity.web.dto.RoomReservationDto;

import java.util.Date;
import java.util.List;

public interface ReservationService {
    /**
     * Creates new reservation
     * @param reservation
     * @return
     */
    Result<Reservation> createReservation(RoomReservationDto reservation);

    /**
     * Deletes reservation
     * @param id - reservation id
     * @return true if reservation was deleted, else - false
     */
    Result<Boolean> deleteReservation(Long id);

    /**
     * Returns list of reservation for a given week
     * @param week - week number relative to current week. e.g. current week = 0, next week = 1,
     *               next after next = 2 and so on
     * @param roomId - room id
     * @return list of reservation
     */
    Result<List<Reservation>> getReservationsForWeek(Long roomId, int week);
}
