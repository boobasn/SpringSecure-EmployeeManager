package com.enrtreprise.api.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody ;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import com.enrtreprise.api.model.Employee;
import com.enrtreprise.api.service.EmployeeService;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
    * Read - Get all employees
    * @return - An Iterable object of Employee full filled
    */
    @GetMapping("/employees")
    public Iterable<Employee> getEmployees() {
        return employeeService.getEmployees();
    }
    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee employee)
    {
        employeeService.saveEmployee(employee) ;
        return employee ;
    }
    @GetMapping("/employees/{id}")
    public Optional<Employee> EmployeeById(@PathVariable Long id){
               
        return employeeService.getEmployeeById(id) ;
    }
    @GetMapping("/employees/name")
    public Employee EmployeeByName(@RequestParam String firstname, @RequestParam String lastname){
               
        return employeeService.getEmployeeByName(firstname, lastname);
    }
    @DeleteMapping("/employees/delete/{id}")
    public String deletEdemployee(@PathVariable Long id){
        return employeeService.deleteEmployee(id);
    }
    @PutMapping("/employees/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        return employeeService.updateEmployee(id, employee);
    }

}