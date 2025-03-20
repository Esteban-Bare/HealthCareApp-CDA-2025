package com.test.wepapp.controller;

import com.test.wepapp.dto.UserRegistrationDto;
import com.test.wepapp.dto.UserDto;
import com.test.wepapp.service.client.MsPatientFeignClient;
import com.test.wepapp.util.PasswordUtil;
import feign.FeignException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private MsPatientFeignClient msPatientFeignClient;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") UserRegistrationDto userDto,
                               BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "register";
        }
        if (!PasswordUtil.isValidPassword(userDto.getPassword())) {
            result.rejectValue("password", "error.user", "Password must contain at least 8 characters, one uppercase letter, one lowercase letter, one digit, and one special character");
            return "register";
        }
        if (!userDto.getPassword().equals(userDto.getPasswordConfirmation())) {
            result.rejectValue("passwordConfirmation", "error.user", "Passwords do not match");
            return "register";
        }

        UserDto userToRegisterDto = new UserDto();
        userToRegisterDto.setUsername(userDto.getUsername());
        userToRegisterDto.setEmail(userDto.getEmail());
        userToRegisterDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userToRegisterDto.setRole("PATIENT");

        try {
            ResponseEntity<?> response = msPatientFeignClient.registerUser(userToRegisterDto);
            redirectAttributes.addFlashAttribute("successMessage", "User registered successfully");
            return "redirect:/login";

        } catch (FeignException.Conflict ex) {
            String errorMessage = ex.contentUTF8();
            model.addAttribute("errorMessage", errorMessage);
            return "register";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred while registering user");
            return "register";
        }
    }

    @GetMapping("/login")
    public String showLoginForm(@RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "logout", required = false) String logout,
                                Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password");
        }
        if (logout != null) {
            model.addAttribute("message", "You have been logged out");
        }
        return "login";
    }
}
