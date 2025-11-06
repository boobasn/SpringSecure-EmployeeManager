package com.enrtreprise.api.controller;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody ;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import com.enrtreprise.api.model.Department;
import com.enrtreprise.api.model.Employee;
import com.enrtreprise.api.service.DepartmentService;

@RestController
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/departments")
    public Iterable<Department> getallDepartments() {
        return departmentService.getAllDepartments();
    }
    @GetMapping("/departments/{id}")
    public Optional<Department> getMethodName(@PathVariable long id) {
        return departmentService.getDepartmentById(id);
    }
    @PostMapping("/departments")
    public ResponseEntity<?> saveDepartement(@RequestBody Department department) {
               
        return departmentService.saveDepartment(department);
    }
    @GetMapping("/departments/{id}/employees")
    
    public Iterable<Employee> GetallEmployeeByDepartement(@PathVariable long  id) {
        return departmentService.getAllEmployee(id);
    }

    @DeleteMapping("/departments/{id}")
    public String deleteDepartment(@PathVariable Long id){
        return departmentService.delatedepartment(id);
    }
    }
    
    
    
    
    

