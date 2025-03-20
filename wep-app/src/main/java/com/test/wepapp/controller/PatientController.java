package com.test.wepapp.controller;

import com.test.wepapp.dto.NoteAddDto;
import com.test.wepapp.dto.PatientDto;
import com.test.wepapp.service.client.MsNotesClientFeign;
import com.test.wepapp.service.client.MsPatientFeignClient;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PatientController {
    @Autowired
    private MsPatientFeignClient msPatientFeignClient;

    @Autowired
    private MsNotesClientFeign msNotesClientFeign;

    @GetMapping("/patients")
    public String patients(Model model) {
        model.addAttribute("patients", msPatientFeignClient.getAllPatients());
        return "patients";
    }

    @GetMapping("/patient/add")
    public String addPatient(Model model) {
        model.addAttribute("patient", new PatientDto());
        return "addPatient";
    }

    @PostMapping("/patient/add")
    public String addPatient(PatientDto patientDto, Model model) {
        msPatientFeignClient.addPatient(patientDto);
        return "redirect:/patients";
    }

    @GetMapping("/patient/notes/{patientId}")
    public String patientNotes(@PathVariable String patientId, Model model) {
        try {
            ResponseEntity<?> response = msNotesClientFeign.getPatientNotes(patientId);
            if (response.getStatusCode() == HttpStatus.OK) {
                model.addAttribute("notes", response.getBody());
                model.addAttribute("patientId", patientId);
                return "patient-notes";
            } else {
                model.addAttribute("error", "Error: " + response.getStatusCode() + " - " + response.getBody());
                return "redirect:/patients";
            }
        } catch (FeignException.NotFound e) {
            model.addAttribute("error", "Notes for patient ID " + patientId + " not found");
            return "redirect:/patients";
        } catch (FeignException e) {
            model.addAttribute("error", "Service error: " + e.getMessage());
            return "redirect:/patients";
        } catch (Exception e) {
            model.addAttribute("error", "Unexpected error retrieving notes for patient ID " + patientId);
            return "redirect:/patients";
        }
    }

    @PostMapping("/patient/add-note")
    public String addNoteToPatient(NoteAddDto note, Model model) {
        try {
            ResponseEntity<String> response = msNotesClientFeign.addNoteToPatient(note);
            if (response.getStatusCode() == HttpStatus.OK) {
                return "redirect:/patient/notes/" + note.getPatientId();
            } else {
                model.addAttribute("error", "Error: " + response.getStatusCode() + " - " + response.getBody());
                return "redirect:/patients";
            }
        } catch (FeignException e) {
            model.addAttribute("error", "Service error: " + e.getMessage());
            return "redirect:/patients";
        } catch (Exception e) {
            model.addAttribute("error", "Unexpected error adding note to patient ID " + note.getPatientId());
            return "redirect:/patients";
        }
    }

    @PostMapping("/patient/update-note")
    public String updateNoteToPatient(NoteAddDto note, Model model) {
        try {
            ResponseEntity<String> response = msNotesClientFeign.updateNoteToPatient(note);
            if (response.getStatusCode() == HttpStatus.OK) {
                return "redirect:/patient/notes/" + note.getPatientId();
            } else {
                model.addAttribute("error", "Error: " + response.getStatusCode() + " - " + response.getBody());
                return "redirect:/patients";
            }
        } catch (FeignException e) {
            model.addAttribute("error", "Service error: " + e.getMessage());
            return "redirect:/patients";
        } catch (Exception e) {
            model.addAttribute("error", "Unexpected error updating note to patient ID " + note.getPatientId());
            return "redirect:/patients";
        }
    }
}
