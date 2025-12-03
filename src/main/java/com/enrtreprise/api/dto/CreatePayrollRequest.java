package com.enrtreprise.api.dto;

import lombok.Data;

@Data
public class CreatePayrollRequest {
    private String employeeId;
    private Integer month;
    private Integer year;
    private Double bonuses = 0.0;
    private Double allowances = 0.0;
    private Double deductions = 0.0;
}
