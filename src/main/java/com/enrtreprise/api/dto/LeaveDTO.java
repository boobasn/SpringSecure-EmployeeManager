package com.enrtreprise.api.dto;

import java.time.LocalDate;

import com.enrtreprise.api.model.Leave;

import com.enrtreprise.api.model.Leave.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveDTO {  
    private String id;
    private String employeeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String motif;
    private Status status;
}
