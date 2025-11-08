package com.enrtreprise.api.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.enrtreprise.api.model.Department;
import com.enrtreprise.api.model.Employee;
import com.enrtreprise.api.service.DepartmentService;

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
    public Iterable<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    /**
     * ADMIN ou MANAGER peuvent consulter un département
     * Les employés n’y ont pas accès directement
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public Optional<Department> getDepartmentById(@PathVariable long id) {
        return departmentService.getDepartmentById(id);
    }

    /**
     * Seul un ADMIN peut créer un département
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveDepartment(@RequestBody Department department) {
        return departmentService.saveDepartment(department);
    }

    /**
     * ADMIN et MANAGER peuvent consulter les employés d’un département
     */
    @GetMapping("/{id}/employees")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public Iterable<Employee> getAllEmployeeByDepartment(@PathVariable long id) {
        return departmentService.getAllEmployee(id);
    }

    /**
     * Seul un ADMIN peut supprimer un département
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteDepartment(@PathVariable Long id) {
        return departmentService.delatedepartment(id);
    }

}
