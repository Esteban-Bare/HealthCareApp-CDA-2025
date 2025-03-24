package com.test.msnotes.service;

import com.test.msnotes.dto.PatientDto;
import com.test.msnotes.dto.PatientIdDto;
import com.test.msnotes.model.Note;
import com.test.msnotes.repository.NoteRepository;
import com.test.msnotes.service.client.MsPatientFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class NoteService {
    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    public MsPatientFeignClient msPatientFeignClient;

    public List<Note> findAll() {
        return noteRepository.findAll();
    }

    public ResponseEntity<String> save(Note note) {
        try {
            ResponseEntity<String> response = msPatientFeignClient.patientExists(note.getPatientId());

            Note savedNote = noteRepository.save(note);
            return ResponseEntity.ok("Note saved");
        } catch (feign.FeignException.NotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        } catch (feign.FeignException e) {
            return ResponseEntity.status(e.status())
                    .body("Error saving note");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error saving note");
        }
    }

    public ResponseEntity<List<Note>> getPatientNotes(String patientId) {
        try {
            ResponseEntity<String> response = msPatientFeignClient.patientExists(patientId);
            List<Note> notes = noteRepository.findByPatientId(patientId);
            return ResponseEntity.ok(notes);
        } catch (feign.FeignException.NotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        } catch (feign.FeignException e) {
            return ResponseEntity.status(e.status())
                    .body(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    public ResponseEntity<String> update(Note note) {
        try {
            ResponseEntity<String> response = msPatientFeignClient.patientExists(note.getPatientId());
            Note savedNote = noteRepository.save(note);
            return ResponseEntity.ok("Note updated");
        } catch (feign.FeignException.NotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        } catch (feign.FeignException e) {
            return ResponseEntity.status(e.status())
                    .body("Error updating note");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating note");
        }
    }
}