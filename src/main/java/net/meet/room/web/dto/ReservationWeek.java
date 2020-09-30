package net.meet.room.web.dto;

import net.meet.room.model.Reservation;

import java.time.DayOfWeek;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ReservationWeek {

    public ReservationWeek() {
        days = new ArrayList<ReservationDay>();
    }
    public static ReservationWeek fromReservationsList(List<Reservation> reservations, int weekNumber){
        final ZonedDateTime input = ZonedDateTime.now(ZoneId.systemDefault()).truncatedTo(ChronoUnit.DAYS);
        final ZonedDateTime startOfWeek = input.plusWeeks(weekNumber).with(DayOfWeek.MONDAY);

        ReservationWeek week = new ReservationWeek();
        week.days = new LinkedList<ReservationDay>();
        for (int i = 0; i < daysInAWeek; i++){
            week.days.add(new ReservationDay());
            week.days.get(i).setDate(startOfWeek.plusDays(i));
        }

        if (reservations == null)
            return week;
        for (Reservation res : reservations)
        {
            ReservationDay day = week.days.get(res.getDateFrom().getDayOfWeek().ordinal());
            day.addReservation(res);
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

    private static int daysInAWeek = 6;
}
