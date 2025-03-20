package com.test.wepapp.service.client;

import com.test.wepapp.dto.NoteAddDto;
import com.test.wepapp.dto.NotesDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("ms-notes")
public interface MsNotesClientFeign {

    @RequestMapping(method = RequestMethod.POST, value = "/patient/{patientId}/notes")
    ResponseEntity<?> getPatientNotes(@PathVariable("patientId") String patientId);

    @RequestMapping(method = RequestMethod.POST, value = "/add-note", consumes = "application/json")
    ResponseEntity<String> addNoteToPatient(@RequestBody NoteAddDto noteAddDto);

    @RequestMapping(method = RequestMethod.POST, value = "/update-note", consumes = "application/json")
    ResponseEntity<String> updateNoteToPatient(@RequestBody NoteAddDto note);
}
