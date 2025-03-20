package com.test.msnotes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDto {
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String gender;
}
