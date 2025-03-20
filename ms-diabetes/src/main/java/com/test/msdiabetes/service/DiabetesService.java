package com.test.msdiabetes.service;

import com.test.msdiabetes.dto.Note;
import com.test.msdiabetes.dto.PatientDto;
import com.test.msdiabetes.dto.PatientIdDto;
import com.test.msdiabetes.service.client.MsNotesFeignClient;
import com.test.msdiabetes.service.client.MsPatientFeignClient;
import com.test.msdiabetes.util.MedicalTerms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class DiabetesService {

    @Autowired
    private MsNotesFeignClient msNotesFeignClient;
    @Autowired
    private MsPatientFeignClient msPatientFeignClient;

    public String calculateDiabetes(String patientId) {
        List<Note> notes = msNotesFeignClient.getPatientNotes(patientId).getBody();
        ResponseEntity<PatientDto> response = msPatientFeignClient.getPatientById(new PatientIdDto(patientId));

        if (response.getBody() == null) {
            return "Patient not found";
        }

        PatientDto info = response.getBody();

        if (notes.isEmpty()) {
            return "No notes found";
        }

        int age = calculateAge(info.getBirthday());
        String gender = info.getGender();

        int countTriggers = countTriggerTerms(notes);

        String riskLevel = getRiskLevel(countTriggers, age, gender);

        return "Patient: " + info.getFirstName() + " " + info.getLastName() +
                " (" + age + " years) diabetes assessment: " + riskLevel;
    }

    private int calculateAge(LocalDate birthday) {
        return Period.between(birthday, LocalDate.now()).getYears();
    }

    private int countTriggerTerms(List<Note> notes) {
        int count = 0;
        for (String term : MedicalTerms.TRIGGER_TERMS) {
            for (Note note : notes) {
                if (containsWord(note.getContent().toLowerCase(), term.toLowerCase())) {
                    count++;
                    break;
                }
            }
        }
        return count;
    }

    private Boolean containsWord(String text, String word) {
        return text.contains(word);
    }

    private String getRiskLevel(int triggerCount, int age, String gender) {
        boolean isOver30 = age > 30;
        boolean isMale = "male".equalsIgnoreCase(gender);

        if (triggerCount == 0) {
            return "NONE";
        }

        if (triggerCount == 2 && isOver30) {
            return "BORDERLINE";
        }

        if ((!isOver30 && isMale && triggerCount == 3) ||
                (!isOver30 && !isMale && triggerCount == 4) ||
                (isOver30 && triggerCount == 6)) {
            return "IN DANGER";
        }

        if ((!isOver30 && isMale && triggerCount >= 5) ||
                (!isOver30 && !isMale && triggerCount >= 7) ||
                (isOver30 && triggerCount >= 8)) {
            return "EARLY ONSET";
        }

        if (isOver30) {
            if (triggerCount < 2) {
                return "NONE";
            } else if (triggerCount < 6) {
                return "BORDERLINE";
            } else if (triggerCount == 7) {
                return "IN DANGER";
            }
        } else {
            if (isMale && triggerCount < 3) {
                return "NONE";
            } else if (isMale && triggerCount == 4) {
                return "IN DANGER";
            } else if (!isMale && triggerCount < 4) {
                return "NONE";
            } else if (!isMale && triggerCount < 7) {
                return "IN DANGER";
            }
        }

        return "NONE";
    }
}
