package com.test.msdiabetes.controller;

import com.test.msdiabetes.dto.DiabetesResultDto;
import com.test.msdiabetes.dto.PatientIdDto;
import com.test.msdiabetes.service.DiabetesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiabetesController {
    @Autowired
    private DiabetesService diabetesService;

    @PostMapping("/diabetes-score")
    public ResponseEntity<DiabetesResultDto> getDiabetesScore(@RequestBody PatientIdDto patientIdDto) {
        return ResponseEntity.ok(diabetesService.calculateDiabetes(patientIdDto.getPatientId()));
    }
}
