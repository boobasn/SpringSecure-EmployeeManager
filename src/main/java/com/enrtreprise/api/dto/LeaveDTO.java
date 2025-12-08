package com.enrtreprise.api.dto;

import java.time.LocalDate;

import com.enrtreprise.api.model.Leave;

import com.enrtreprise.api.model.Leave.Status;
import lombok.AllArgsConstructor;
import com.enrtreprise.api.model.Employee;  
import lombok.Data;
import com.enrtreprise.api.model.Leave.TypeConge;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveDTO {  
    private String id;
    private Employee employee;
    private LocalDate startDate;
    private LocalDate endDate;
    private TypeConge typeConge;
    private String motif;
    private Status status;
}
