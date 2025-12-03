package com.enrtreprise.api.dto;

import com.enrtreprise.api.model.Payroll;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PayrollDTO {
    private String id;
    private String employeeId;
    private String employeeFirstName;
    private String employeeLastName;
    private Integer month;
    private Integer year;
    private Double baseSalary;
    private Double bonuses;
    private Double allowances;
    private Double deductions;
    private Double netSalary;
    private Payroll.Status status;
    private String payslipUrl;
    private LocalDateTime createdAt;
}
