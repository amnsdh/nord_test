package net.javaguides.springboot.springsecurity.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import net.javaguides.springboot.springsecurity.model.Reservation;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query(value = "SELECT * FROM reservation WHERE room_id = :roomId AND date_from BETWEEN :dateFrom AND :dateTo", nativeQuery = true)
    List<Reservation> getByRange(@Param("dateFrom") ZonedDateTime dateFrom, @Param("dateTo") ZonedDateTime dateTo, @Param("roomId") Long roomId);
}
