package com.enrtreprise.api.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize; // Pour sécuriser les méthodes
import org.springframework.web.bind.annotation.*;

import com.enrtreprise.api.model.Employee;
import com.enrtreprise.api.service.EmployeeService;

@RestController
@RequestMapping("/employees") // Préfixe commun pour tous les endpoints
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * Get all employees
     * Seul un ADMIN peut lister tous les employés
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public Iterable<Employee> getEmployees() {
        return employeeService.getEmployees();
    }

    /**
     * Ajouter un nouvel employé
     * ADMIN ou MANAGER peuvent ajouter des employés
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public Employee addEmployee(@RequestBody Employee employee) {
        employeeService.saveEmployee(employee);
        return employee;
    }

    /**
     * Obtenir un employé par son ID
     * ADMIN, MANAGER ou l’employé lui-même peuvent accéder à ses données
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or @securityService.isSelfEmployee(#id)")
    public Optional<Employee> employeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    /**
     * Obtenir un employé par son nom
     * ADMIN et MANAGER seulement
     */
    @GetMapping("/name")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public Employee employeeByName(@RequestParam String firstname, @RequestParam String lastname) {
        return employeeService.getEmployeeByName(firstname, lastname);
    }

    /**Supprimer un employé
     * Seul ADMIN peut supprimer
     * 
     */
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public String deleteEmployee(@PathVariable Long id) {
        return employeeService.deleteEmployee(id);
    }

    /**
     * Mettre à jour un employé
     * ADMIN et MANAGER peuvent mettre à jour ; un employé peut mettre à jour ses infos personnelles
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or @securityService.isSelfEmployee(#id)")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        return employeeService.updateEmployee(id, employee);
    }
}
