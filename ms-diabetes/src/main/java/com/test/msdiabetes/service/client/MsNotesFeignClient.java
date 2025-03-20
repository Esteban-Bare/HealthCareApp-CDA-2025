package com.test.msdiabetes.service.client;

import com.test.msdiabetes.dto.Note;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("ms-notes")
public interface MsNotesFeignClient {

    @RequestMapping(method = RequestMethod.POST, value = "/patient/{patientId}/notes")
    ResponseEntity<List<Note>> getPatientNotes(@PathVariable("patientId") String patientId);
}
