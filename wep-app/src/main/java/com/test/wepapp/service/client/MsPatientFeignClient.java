package com.test.wepapp.service.client;

import com.test.wepapp.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@FeignClient("ms-patient")
public interface MsPatientFeignClient {

    @RequestMapping(method = RequestMethod.POST, value = "/user/login", consumes = "application/json")
    Optional<UserToLogDto> findByEmail(UserLogDto email);

    @RequestMapping(method = RequestMethod.POST, value = "/user/verify-email", consumes = "application/json")
    Boolean verifyEmail(UserLogDto email);

    @RequestMapping(method = RequestMethod.POST, value = "/user/verify-username", consumes = "application/json")
    Boolean verifyUsername(UsernameDto username);

    @RequestMapping(method = RequestMethod.POST, value = "/user/register", consumes = "application/json")
    ResponseEntity<?> registerUser(UserToRegisterDto user);
}
