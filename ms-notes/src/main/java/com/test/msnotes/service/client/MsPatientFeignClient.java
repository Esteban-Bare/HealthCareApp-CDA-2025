package com.test.msnotes.service.client;

import com.test.msnotes.dto.PatientDto;
import com.test.msnotes.dto.PatientIdDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("ms-patient")
public interface MsPatientFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "/patient/patient-exists")
    ResponseEntity<String> patientExists(@RequestParam("patientId") String patientId);

    @RequestMapping(method = RequestMethod.POST, value= "/patient/by-id", consumes = "application/json")
    ResponseEntity<PatientDto> getPatientById(@RequestBody PatientIdDto patientIdDto);
}
