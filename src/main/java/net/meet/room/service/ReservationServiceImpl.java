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
import java.util.List;
import java.util.Optional;

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
            return new Result<>(null, true, "Reservations can't intersect by time");
        }
        Reservation res = new Reservation();
        res.setDateFrom(reservation.getDateFrom());
        res.setDateTo(reservation.getDateTo());
        Optional<Room> room = roomRepository.findById(reservation.getRoomId());
        if (!room.isPresent())
            return new Result<>(null, true, "There is no room with id:" + reservation.getRoomId() +
                    ". Asc administrator to create the room");
        res.setRoom(room.get());
        for (Long id : reservation.getUsers())
        {
            Optional<User> user = userRepository.findById(id);
            if (!user.isPresent())
                return null;
            res.addUser(user.get());
        }
        Reservation result;
        try {
            result = reservRespository.save(res);
        }
        catch (Exception e) {
            return new Result<>(null, true, e.getMessage());
        }
        return new Result<>(result);
    }

    @Override
    public Result<Boolean> deleteReservation(Long id) {
        Optional<Reservation> reserv = reservRespository.findById(id);
        if (!reserv.isPresent()) {
            return new Result<>(false, true, "There is no reservation with id " + id);
        }
        reservRespository.delete(reserv.get());
        return new Result<>(true);
    }

    @Override
    public Result<List<Reservation>> getReservationsForWeek(Long roomId, int week) {
        final ZonedDateTime input = ZonedDateTime.now(ZoneId.systemDefault()).truncatedTo(ChronoUnit.DAYS);
        final ZonedDateTime startOfWeek = input.plusWeeks(week).with(DayOfWeek.MONDAY);
        final ZonedDateTime endOfWeek = startOfWeek.plusDays(6);
        return new Result<>(reservRespository.getByRange(startOfWeek, endOfWeek, roomId));
    }
}
