package com.enrtreprise.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import com.enrtreprise.api.dto.EmployeeDTO;
import com.enrtreprise.api.mapper.EmployeeMapper;
import com.enrtreprise.api.model.Employee;
import com.enrtreprise.api.service.EmployeeService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * Obtenir tous les employés
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<List<EmployeeDTO>> getEmployees() {
        List<Employee> employees = employeeService.getEmployees();

        List<EmployeeDTO> dtos = employees.stream()
                .map(EmployeeMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    /**
     * Ajouter un employé
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<EmployeeDTO> addEmployee(@RequestBody EmployeeDTO dto) {

        Employee employee = EmployeeMapper.toEntity(dto);
        Employee saved = employeeService.saveEmployee(employee);

        return ResponseEntity.ok(EmployeeMapper.toDTO(saved));
    }

    /**
     * Obtenir un employé par ID
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or @securityService.isSelfEmployee(#id)")
    public ResponseEntity<EmployeeDTO> employeeById(@PathVariable String id) {
        Employee employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(EmployeeMapper.toDTO(employee));
    }

    /**
     * Rechercher un employé par nom
     */
    @GetMapping("/name")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<EmployeeDTO> employeeByName(@RequestParam String firstname, @RequestParam String lastname) {
        Employee employee = employeeService.getEmployeeByName(firstname, lastname);
        return ResponseEntity.ok(EmployeeMapper.toDTO(employee));
    }

    /**
     * Supprimer un employé
     */
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<String> deleteEmployee(@PathVariable String id) {
        String result = employeeService.deleteEmployee(id);
        return ResponseEntity.ok(result);
    }

    /**
     * Mettre à jour un employé
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or @securityService.isSelfEmployee(#id)")
    public ResponseEntity<EmployeeDTO> updateEmployee(
            @PathVariable String id,
            @RequestBody EmployeeDTO dto) {

        Employee updated = employeeService.updateEmployee(id, EmployeeMapper.toEntity(dto));
        return ResponseEntity.ok(EmployeeMapper.toDTO(updated));
    }
    
    /**
     * Obtenir le nombre total d'employés
     */
    @GetMapping("/count")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Long> getTotalEmployees() {
        long count = employeeService.getTotalEmployees();
        return ResponseEntity.ok(count);
    }
}