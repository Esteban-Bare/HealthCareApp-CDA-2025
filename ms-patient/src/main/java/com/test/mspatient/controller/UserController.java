package com.test.mspatient.controller;

import com.test.mspatient.dto.ChangeRoleDto;
import com.test.mspatient.dto.UserLogDto;
import com.test.mspatient.dto.UserToLogDto;
import com.test.mspatient.exception.UserAlreadyExistsException;
import com.test.mspatient.model.User;
import com.test.mspatient.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User registeredUser = userService.registerUser(user);
            return ResponseEntity.ok(registeredUser);
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public UserToLogDto loginUser(@RequestBody UserLogDto email) {
        return userService.loginUser(email);
    }

    @PostMapping("/all")
    public Iterable<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestBody UserLogDto email) {
        try {
            userService.deleteUser(email);
            return ResponseEntity.ok("User deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/change-role")
    public ResponseEntity<?> changeRole(@RequestBody ChangeRoleDto changeRoleDto) {
        try {
            userService.changeRole(changeRoleDto);
            return ResponseEntity.ok("Role changed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/email")
    public ResponseEntity<?> findByEmail(@RequestBody UserToLogDto email) {
        try {
            User user = userService.findByEmail(email);
            return ResponseEntity.ok(new User(null, user.getUsername(),user.getEmail(), user.getPassword() ,user.getRole()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
