package com.test.msnotes.controller;

import com.test.msnotes.dto.PatientIdDto;
import com.test.msnotes.model.Note;
import com.test.msnotes.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping("/notes")
    public List<Note> findAll() {
        return noteService.findAll();
    }

    @PostMapping("/add-note")
    public ResponseEntity<String> save(@RequestBody Note note) {
        return noteService.save(note);
    }

    @PostMapping("/patient/{patientId}/notes")
    public ResponseEntity<List<Note>> getPatientNotes(@PathVariable String patientId) {
        return noteService.getPatientNotes(patientId);
    }

    @PostMapping("/update-note")
    public ResponseEntity<String> update(@RequestBody Note note) {
        return noteService.update(note);
    }
}