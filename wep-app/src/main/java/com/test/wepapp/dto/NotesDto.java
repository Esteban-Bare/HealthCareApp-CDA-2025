package com.test.wepapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotesDto {
    private String id;
    private String title;
    private String content;
    private String patientId;
}
