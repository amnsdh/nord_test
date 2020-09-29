package net.javaguides.springboot.springsecurity.web.dto;

import net.javaguides.springboot.springsecurity.model.Reservation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ReservationWeek {

    public ReservationWeek() {
        days = new ArrayList<ReservationDay>();
    }
    public static ReservationWeek fromReservationsList(List<Reservation> reservations){
        ReservationWeek week = new ReservationWeek();
        week.days = new LinkedList<ReservationDay>();
        for (int i = 0; i < daysInAWeek; i++){
            week.days.add(new ReservationDay());
        }

        if (reservations == null)
            return week;
        for (Reservation res : reservations)
        {
            ReservationDay day = week.days.get(res.getDateFrom().getDayOfWeek().ordinal());
            day.addReservation(res);
            day.setDate(res.getDateFrom());
        }
        return week;
    }

    public List<ReservationDay> getDays() {
        return days;
    }

    public void setDays(List<ReservationDay> days) {
        this.days = days;
    }

    private List<ReservationDay> days;

    private static int daysInAWeek = 7;
}
