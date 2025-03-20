package com.test.wepapp.service.client;

import com.test.wepapp.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Optional;

@FeignClient("ms-patient")
public interface MsPatientFeignClient {

    @RequestMapping(method = RequestMethod.POST, value = "/user/login", consumes = "application/json")
    Optional<UserToLogDto> logByEmail(UserLogDto email);

    @RequestMapping(method = RequestMethod.POST, value = "/user/email", consumes = "application/json")
    Optional<UserDto> findByEmail(UserLogDto email);

    @RequestMapping(method = RequestMethod.POST, value = "/user/verify-email", consumes = "application/json")
    Boolean verifyEmail(UserLogDto email);

    @RequestMapping(method = RequestMethod.POST, value = "/user/verify-username", consumes = "application/json")
    Boolean verifyUsername(UsernameDto username);

    @RequestMapping(method = RequestMethod.POST, value = "/user/register", consumes = "application/json")
    ResponseEntity<?> registerUser(UserDto user);

    @RequestMapping(method = RequestMethod.POST, value = "/user/all")
    List<UserDto> getAllUsers();

    @RequestMapping(method = RequestMethod.POST, value = "/user/change-role", consumes = "application/json")
    ResponseEntity<?> changeRole(ChangeRoleDto changeRoleDto);

    @RequestMapping(method = RequestMethod.POST, value = "/patient/all")
    List<PatientDto> getAllPatients();

    @RequestMapping(method = RequestMethod.POST, value = "/patient/save", consumes = "application/json")
    PatientDto addPatient(PatientDto patientDto);
}