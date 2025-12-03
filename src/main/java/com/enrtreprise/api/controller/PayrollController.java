package com.enrtreprise.api.controller;
import com.enrtreprise.api.dto.CreatePayrollRequest;
import com.enrtreprise.api.dto.PayrollDTO;
import com.enrtreprise.api.mapper.PayrollMapper;
import com.enrtreprise.api.model.Payroll;
import com.enrtreprise.api.service.PayrollService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/payroll")
@RequiredArgsConstructor
public class PayrollController {

    private final PayrollService payrollService;
    // Get all payrolls
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','FINANCE')")
    public ResponseEntity<List<PayrollDTO>> getAll(){
        var list = payrollService.getAllPayrolls();
        return ResponseEntity.ok(list.stream().map(PayrollMapper::toDTO).collect(Collectors.toList()));
    }

    // ADMIN only: create payroll for a single employee
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PayrollDTO> create(@RequestBody CreatePayrollRequest req) {
        Payroll p = payrollService.createPayrollForEmployee(
                req.getEmployeeId(), req.getMonth(), req.getYear(),
                req.getBonuses(), req.getAllowances(), req.getDeductions()
        );
        return ResponseEntity.ok(PayrollMapper.toDTO(p));
    }

    // ADMIN generate batch for month
    @PostMapping("/generate/{month}/{year}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PayrollDTO>> generateBatch(
            @PathVariable int month, @PathVariable int year,
            @RequestParam(required = false, defaultValue = "false") boolean overwrite
    ) {
        var list = payrollService.generatePayrollForMonth(month, year, overwrite);
        return ResponseEntity.ok(list.stream().map(PayrollMapper::toDTO).collect(Collectors.toList()));
    }

    // Finance marks paid
    @PostMapping("/pay/{id}")
    @PreAuthorize("hasRole('FINANCE') or hasRole('ADMIN')")
    public ResponseEntity<PayrollDTO> pay(@PathVariable String id){
        var p = payrollService.markAsPaid(id);
        return ResponseEntity.ok(PayrollMapper.toDTO(p));
    }

    // Get payroll by id (admin/finance or owner)
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','FINANCE') or @securityService.isPayrollOwner(#id)")
    public ResponseEntity<PayrollDTO> getById(@PathVariable String id){
        Payroll p = payrollService.getPayrollById(id);
        return ResponseEntity.ok(PayrollMapper.toDTO(p));
    }

    // Employee history (self or admin)
    @GetMapping("/employee/{employeeId}")
    @PreAuthorize("hasAnyRole('ADMIN','FINANCE') or @securityService.isSelfEmployee(#employeeId)")
    public ResponseEntity<List<PayrollDTO>> history(@PathVariable String employeeId){
        var list = payrollService.getPayrollsByEmployee(employeeId);
        return ResponseEntity.ok(list.stream().map(PayrollMapper::toDTO).collect(Collectors.toList()));
    }

    // Generate payslip (pdf) and return url
    @PostMapping("/payslip/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','FINANCE') or @securityService.isPayrollOwner(#id)")
    public ResponseEntity<String> generatePayslip(@PathVariable String id){
        String url = payrollService.generatePayslipPdf(id);
        return ResponseEntity.ok(url);
    }

    // List for month (admin/finance)
    @GetMapping("/month/{month}/{year}")
    @PreAuthorize("hasAnyRole('ADMIN','FINANCE')")
    public ResponseEntity<List<PayrollDTO>> getByMonth(@PathVariable int month, @PathVariable int year) {
        var list = payrollService.getPayrollsForMonth(month, year);
        return ResponseEntity.ok(list.stream().map(PayrollMapper::toDTO).collect(Collectors.toList()));
    }
}