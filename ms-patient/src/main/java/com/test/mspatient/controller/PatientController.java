package com.test.mspatient.controller;

import com.test.mspatient.dto.PatientDto;
import com.test.mspatient.dto.PatientDtoDiabetes;
import com.test.mspatient.dto.PatientIdDto;
import com.test.mspatient.model.Patient;
import com.test.mspatient.service.PatientService;
import com.test.mspatient.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @PostMapping("/all")
    public Iterable<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    @PostMapping("/save")
    public Patient savePatient(@RequestBody PatientDto patient) {
        return patientService.savePatient(patient);

        // example of json request body:
        // {
        //     "firstName": "John",
        //     "lastName": "Doe",
        //     "birthday": "1990-01-01",
        //     "gender": "male",
        //     "address": "123 Main St",
        //     "phoneNumber": "123-456-7890",
        //     "email": "john@email.ex"
        // }
    }

    @PostMapping("/by-id")
    public ResponseEntity<PatientDtoDiabetes> getPatientById(@RequestBody PatientIdDto patientIdDto) {
        return patientService.getPatientById(patientIdDto.getPatientId()).map(patient -> {
            PatientDtoDiabetes patientDtoDiabetes = new PatientDtoDiabetes();
            patientDtoDiabetes.setFirstName(patient.getFirstName());
            patientDtoDiabetes.setLastName(patient.getLastName());
            patientDtoDiabetes.setBirthday(patient.getBirthday());
            patientDtoDiabetes.setGender(patient.getGender());
            return ResponseEntity.ok(patientDtoDiabetes);
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/patient-exists")
    public ResponseEntity<String> patientExists(@RequestParam("patientId") String patientId) {
        return patientService.patientExists(patientId);
    }
}
