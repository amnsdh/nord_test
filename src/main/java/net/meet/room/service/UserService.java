package net.meet.room.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import net.meet.room.model.User;
import net.meet.room.web.dto.UserRegistrationDto;

public interface UserService extends UserDetailsService {

    User findByEmail(String email);

    User save(UserRegistrationDto registration);
}
