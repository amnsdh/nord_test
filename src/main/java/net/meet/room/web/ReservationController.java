package net.meet.room.web;

import javax.validation.Valid;

import net.meet.room.model.Reservation;
import net.meet.room.service.ReservationService;
import net.meet.room.service.Result;
import net.meet.room.web.dto.ReservationDto;
import net.meet.room.web.dto.ReservationWeek;
import net.meet.room.web.dto.RoomReservationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import net.meet.room.model.User;
import net.meet.room.service.UserService;
import net.meet.room.web.dto.UserRegistrationDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    private ReservationService reservService;

    @Autowired
    private UserService userService;

    @GetMapping
    public Result<ReservationWeek> getReservationsForWeek(Long roomId, int week) {
        Result<List<Reservation>> result = reservService.getReservationsForWeek(roomId, week);
        Result<ReservationWeek> resultWeek = new Result(ReservationWeek.fromReservationsList(result.value, week));
        resultWeek.setErrorString(result.getErrorString());
        resultWeek.setHasError(result.hasError);
        return resultWeek;
    }

    @PostMapping
    public ResponseEntity Create(@Valid @RequestBody RoomReservationDto model, @AuthenticationPrincipal UserDetails userDetails) {
        if (model.getDateFrom() == null || model.getDateTo() == null)
            return ResponseEntity.badRequest().body("Date range is not valid");

        if (model != null && userDetails != null) {
            User user = userService.findByEmail(userDetails.getUsername());
            model.addUser(user.getId());
        }
        Result<Reservation> reservation = reservService.createReservation(model);
        if (reservation.hasError)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(reservation.errorString);
        }
        return new ResponseEntity(reservation.value, HttpStatus.OK);
    }

    @DeleteMapping
    public Result<Boolean> Delete(Long id) {
        return reservService.deleteReservation(id);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
