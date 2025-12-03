package com.enrtreprise.api.mapper;

import com.enrtreprise.api.model.Payroll;
import com.enrtreprise.api.dto.PayrollDTO;
import com.enrtreprise.api.dto.CreatePayrollRequest;
import com.enrtreprise.api.model.Employee;

public class PayrollMapper {
    public static PayrollDTO toDTO(Payroll payroll) {
        PayrollDTO dto = new PayrollDTO();
        dto.setId(payroll.getId());
        Employee emp = payroll.getEmployee();
        dto.setEmployeeId(emp.getId());
        dto.setEmployeeFirstName(emp.getFirstName());
        dto.setEmployeeLastName(emp.getLastName());
        dto.setMonth(payroll.getMonth());
        dto.setYear(payroll.getYear());
        dto.setBaseSalary(payroll.getBaseSalary());
        dto.setBonuses(payroll.getBonuses());
        dto.setAllowances(payroll.getAllowances());
        dto.setDeductions(payroll.getDeductions());
        dto.setNetSalary(payroll.getNetSalary());
        dto.setStatus(payroll.getStatus());
        dto.setPayslipUrl(payroll.getPayslipUrl());
        dto.setCreatedAt(payroll.getCreatedAt());
        return dto;
    }
    public static Payroll toEntity(CreatePayrollRequest request, Employee employee, Double baseSalary) {
        Payroll payroll = new Payroll();
        payroll.setEmployee(employee);
        payroll.setMonth(request.getMonth());
        payroll.setYear(request.getYear());
        payroll.setBaseSalary(baseSalary);
        payroll.setBonuses(request.getBonuses());
        payroll.setAllowances(request.getAllowances());
        payroll.setDeductions(request.getDeductions());
        Double netSalary = baseSalary + request.getBonuses() + request.getAllowances() - request.getDeductions();
        payroll.setNetSalary(netSalary);
        payroll.setStatus(Payroll.Status.GENERATED);
        return payroll;
    }


}
