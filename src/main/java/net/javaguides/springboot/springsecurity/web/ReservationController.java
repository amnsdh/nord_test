package net.javaguides.springboot.springsecurity.web;

import javax.validation.Valid;

import net.javaguides.springboot.springsecurity.model.Reservation;
import net.javaguides.springboot.springsecurity.service.ReservationService;
import net.javaguides.springboot.springsecurity.service.Result;
import net.javaguides.springboot.springsecurity.web.dto.ReservationDto;
import net.javaguides.springboot.springsecurity.web.dto.ReservationWeek;
import net.javaguides.springboot.springsecurity.web.dto.RoomReservationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import net.javaguides.springboot.springsecurity.model.User;
import net.javaguides.springboot.springsecurity.service.UserService;
import net.javaguides.springboot.springsecurity.web.dto.UserRegistrationDto;

import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    private ReservationService reservService;

    @GetMapping
    public Result<ReservationWeek> getReservationsForWeek(Long roomId, int week) {
        Result<List<Reservation>> result = reservService.getReservationsForWeek(roomId, week);
        Result<ReservationWeek> resultWeek = new Result(ReservationWeek.fromReservationsList(result.value));
        resultWeek.setErrorString(result.getErrorString());
        resultWeek.setHasError(result.hasError);
        return resultWeek;
    }

    @PostMapping
    public Result<Reservation> Create(RoomReservationDto model) {
        return new Result(reservService.createReservation(model));
    }

    @DeleteMapping
    public Result<Boolean> Delete(Long id) {
        return reservService.deleteReservation(id);
    }
}
