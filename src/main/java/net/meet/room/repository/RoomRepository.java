package net.meet.room.repository;

import net.meet.room.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.meet.room.model.User;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
}
