package com.test.msdiabetes.service.client;

import com.test.msdiabetes.dto.PatientDto;
import com.test.msdiabetes.dto.PatientIdDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("ms-patient")
public interface MsPatientFeignClient {

    @RequestMapping(method = RequestMethod.POST, value= "/patient/by-id", consumes = "application/json")
    ResponseEntity<PatientDto> getPatientById(@RequestBody PatientIdDto patientIdDto);
}
