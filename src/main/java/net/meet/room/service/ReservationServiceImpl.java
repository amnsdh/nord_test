package net.meet.room.service;

import net.meet.room.model.Reservation;
import net.meet.room.model.Room;
import net.meet.room.model.User;
import net.meet.room.repository.ReservationRepository;
import net.meet.room.repository.RoomRepository;
import net.meet.room.repository.UserRepository;
import net.meet.room.web.dto.RoomReservationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    private ReservationRepository reservRespository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public Result<Reservation> createReservation(RoomReservationDto reservation) {
        if (reservRespository.getIntersected(reservation.getDateFrom(),
                reservation.getDateTo(),
                reservation.getRoomId()).size() > 0)
        {
            return new Result<Reservation>(null, true, "Reservations can't intersect by time");
        }
        Reservation res = new Reservation();
        res.setDateFrom(reservation.getDateFrom());
        res.setDateTo(reservation.getDateTo());
        Room room = roomRepository.getOne(reservation.getRoomId());
        if (room == null)
            return null;
        res.setRoom(room);
        for (Long id : reservation.getUsers())
        {
            User user = userRepository.getOne(id);
            if (user == null)
                return null;
            res.addUser(user);
        }
        return new Result(reservRespository.save(res));
    }

    @Override
    public Result<Boolean> deleteReservation(Long id) {
        Reservation reserv = reservRespository.getOne(id);
        if (reserv == null) {
            return new Result<Boolean>(false, true, "There is no reservation with id " + id);
        }
        reservRespository.delete(reserv);
        return new Result(true);
    }

    @Override
    public Result<List<Reservation>> getReservationsForWeek(Long roomId, int week) {
        final ZonedDateTime input = ZonedDateTime.now(ZoneId.systemDefault()).truncatedTo(ChronoUnit.DAYS);
        final ZonedDateTime startOfWeek = input.plusWeeks(week).with(DayOfWeek.MONDAY);
        final ZonedDateTime endOfWeek = startOfWeek.plusDays(6);
        return new Result(reservRespository.getByRange(startOfWeek, endOfWeek, roomId));
    }
}
