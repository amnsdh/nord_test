package net.javaguides.springboot.springsecurity.repository;

import net.javaguides.springboot.springsecurity.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.javaguides.springboot.springsecurity.model.User;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
}
