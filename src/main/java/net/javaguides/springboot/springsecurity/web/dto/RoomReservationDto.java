package net.javaguides.springboot.springsecurity.web.dto;

import javax.validation.constraints.NotEmpty;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

public class RoomReservationDto {

    @NotEmpty
    private Long roomId;

    @NotEmpty
    private ZonedDateTime dateFrom;

    @NotEmpty
    private ZonedDateTime dateTo;

    @NotEmpty
    private List<Long> users;

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public ZonedDateTime getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(ZonedDateTime dateFrom) {
        this.dateFrom = dateFrom;
    }

    public ZonedDateTime getDateTo() {
        return dateTo;
    }

    public void setDateTo(ZonedDateTime dateTo) {
        this.dateTo = dateTo;
    }

    public List<Long> getUsers() {
        return users;
    }

    public void setUsers(List<Long> users) {
        this.users = users;
    }
}

