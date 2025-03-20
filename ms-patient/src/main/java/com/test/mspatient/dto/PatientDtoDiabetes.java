package com.test.mspatient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDtoDiabetes {
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String gender;
}
