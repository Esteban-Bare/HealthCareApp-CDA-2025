package com.test.wepapp.service.client;

import com.test.wepapp.dto.DiabetesResultDto;
import com.test.wepapp.dto.PatientIdDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("ms-diabetes")
public interface MsDiabetesClientFeign {

    @RequestMapping(method = RequestMethod.POST, value = "/diabetes-score", consumes = "application/json")
    ResponseEntity<DiabetesResultDto> getDiabetesScore(@RequestBody PatientIdDto patientIdDto);
}
