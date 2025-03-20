package com.test.mspatient.service;

import com.test.mspatient.dto.ChangeRoleDto;
import com.test.mspatient.dto.UserLogDto;
import com.test.mspatient.dto.UserToLogDto;
import com.test.mspatient.exception.UserAlreadyExistsException;
import com.test.mspatient.model.Role;
import com.test.mspatient.model.User;
import com.test.mspatient.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("User with username " + user.getUsername() + " already exists");
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with email " + user.getEmail() + " already exists");
        }

        return userRepository.save(user);
    }

    public UserToLogDto loginUser(UserLogDto user) {
        Optional<User> user1 = userRepository.findByEmail(user.getEmail());
        if (user1.isPresent()) {
            return new UserToLogDto(user1.get().getEmail(), user1.get().getPassword(),user1.get().getRole().toString());
        }
        return null;
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(UserLogDto email) {
        User user = userRepository.findByEmail(email.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email.getEmail()));
        userRepository.delete(user);
    }

    public void changeRole(ChangeRoleDto changeRoleDto) {
        User user = userRepository.findByUsername(changeRoleDto.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found with username: " + changeRoleDto.getUsername()));

        try {
            Role newRole = Role.valueOf(changeRoleDto.getRole().toUpperCase());
            user.setRole(newRole);
            userRepository.save(user);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid role: " + changeRoleDto.getRole());
        }
    }

    public User findByEmail(UserToLogDto email) {
        return userRepository.findByEmail(email.getEmail()).orElseThrow(() -> new RuntimeException("User not found with email: " + email.getEmail()));
    }
}
