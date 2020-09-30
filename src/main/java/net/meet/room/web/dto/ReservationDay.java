package net.meet.room.web.dto;

import net.meet.room.model.Reservation;

import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;

public class ReservationDay {

    public ReservationDay(){
        reservations = new LinkedList<Reservation>();
    }
    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
        this.dayOfWeek = date.getDayOfWeek().toString();
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public void addReservation(Reservation reservation){
        reservations.add(reservation);
    }

    private String dayOfWeek;
    private ZonedDateTime date;
    private List<Reservation> reservations;
}
