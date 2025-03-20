package com.test.mspatient.service;

import com.test.mspatient.dto.PatientDto;
import com.test.mspatient.model.Patient;
import com.test.mspatient.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient savePatient(PatientDto patient) {
        return patientRepository.save(new Patient(patient.getFirstName(), patient.getLastName(), patient.getBirthday(), patient.getGender(),patient.getAddress(), patient.getPhoneNumber(), patient.getEmail()));
    }

    public ResponseEntity<String> patientExists(String patientId) {
        if (patientRepository.existsById(Long.parseLong(patientId))) {
            return ResponseEntity.ok("Patient exists");
        } else {
            return ResponseEntity.status(404).body("Patient does not exist");
        }
    }

    public Optional<Patient> getPatientById(String patientId) {
        return patientRepository.findById(Long.parseLong(patientId));
    }
}
