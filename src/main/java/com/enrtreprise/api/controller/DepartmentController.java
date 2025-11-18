package com.enrtreprise.api.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.enrtreprise.api.dto.DepartementDTO;
import com.enrtreprise.api.mapper.DepartementMapper;
import com.enrtreprise.api.mapper.EmployeeMapper;
import com.enrtreprise.api.model.Department;
import com.enrtreprise.api.model.Employee;
import com.enrtreprise.api.service.DepartmentService;

import java.util.List;
import java.util.stream.Collectors;

import com.enrtreprise.api.dto.EmployeeDTO;

import io.micrometer.core.ipc.http.HttpSender;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/departments") // Regroupe toutes les routes sous /departments
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    /**
     * Accessible uniquement aux ADMIN
     * Affiche tous les départements
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<DepartementDTO>> getAllDepartments() {
        List <Department> departments = departmentService.getAllDepartments();
        List<DepartementDTO> departmentDTOs = departments.stream()
                .map(DepartementMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(departmentDTOs);
    }

    /**
     * ADMIN ou MANAGER peuvent consulter un département
     * Les employés n’y ont pas accès directement
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<DepartementDTO> getDepartmentById(@PathVariable String id) {
        Department department = departmentService.getDepartmentById(id);
        DepartementDTO departmentDTO = DepartementMapper.toDTO(department);
        return ResponseEntity.ok(departmentDTO);
    }

    /**
     * Seul un ADMIN peut créer un département
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DepartementDTO> saveDepartment(@RequestBody DepartementDTO dto) {
        Department department = DepartementMapper.toEntity(dto);
        Department savedDepartment = departmentService.saveDepartment(department);
    return ResponseEntity.ok(DepartementMapper.toDTO(savedDepartment));

    }

    /**
     * ADMIN et MANAGER peuvent consulter les employés d’un département
     */
    @GetMapping("/{id}/employees")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployeeByDepartment(@PathVariable String id) {
        List<Employee> employees = (List<Employee>) departmentService.getAllEmployee(id);
        List<EmployeeDTO> employeeDTOs = employees.stream()
                .map(EmployeeMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(employeeDTOs);
    }

    /**
     * Seul un ADMIN peut supprimer un département
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteDepartment(@PathVariable String id) {
        return ResponseEntity.ok(departmentService.delatedepartment(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<DepartementDTO> updateDepartment(@PathVariable String id, @RequestBody DepartementDTO dto)
    {
        Department updateDepartment = DepartementMapper.toEntity(dto);
        Department updatedDepartment = departmentService .updateDepartment( id, updateDepartment);
        return ResponseEntity.ok(DepartementMapper.toDTO(updatedDepartment));
    }

}
