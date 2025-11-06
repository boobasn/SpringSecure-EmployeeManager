package com.enrtreprise.api.service;

import java.util.Optional ; //import de la classe Optional de Java pour gérer les valeurs pouvant être nulles.
import org.springframework.beans.factory.annotation.Autowired; //import de l'annotation Autowired de Spring pour l'injection de dépendances.
import org.springframework.stereotype.Service; //import l'annotation Service de Spring pour indiquer que cette classe est un service métier.
import com.enrtreprise.api.model.Employee; //import de la classe Employee pour manipuler les objets employés.

import com.enrtreprise.api.repository.EmployeeRepository; //import de l'interface EmployeeRepository pour accéder aux opérations CRUD sur les employés.

import com.enrtreprise.api.repository.DepartmentRepository;
import com.enrtreprise.api.repository.ContratRepository;
import lombok.Data; //import de l'annotation lombok pour générer les getters et setters automatiquement.

@Data // Génère les getters et setters pour les champs de la classe automatiquement.
@Service // Indique que cette classe est un service métier dans le contexte Spring.
public class EmployeeService {
    @Autowired // Injection automatique de l'instance d'EmployeeRepository par Spring.
    private EmployeeRepository employeeRepository;
     
    @Autowired
    private ContratRepository contratRepository ;

    @Autowired
    private DepartmentRepository departmentRepository;
    // Méthode pour récupérer un employé par son identifiant.
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }
    public Employee  getEmployeeByName(String  firstname ,String lastname ){

        return employeeRepository.findByFirstname( firstname,lastname);
    }

    public Iterable<Employee> getEmployees() {
        return employeeRepository.findAll();
    }
    public Employee saveEmployee(Employee employee) {
        Employee savedEmployee = employeeRepository.save(employee);
        return savedEmployee ;
    }
    public String deleteEmployee(Long id){
        
        employeeRepository.deleteById(id);
        return "employee supprime avec success" ;
    }
    public Employee updateEmployee(Long id, Employee updatedEmployee){
        Optional<Employee> opt  =  employeeRepository.findById(id);
        if (!opt.isPresent()){
            throw new  RuntimeException("Employee not found with id: " + id);
        }
        Employee employee = opt.get();
        if (updatedEmployee.getFirstName() != null) employee.setFirstName(updatedEmployee.getFirstName());
        if (updatedEmployee.getLastName() != null) employee.setLastName(updatedEmployee.getLastName());
        if (updatedEmployee.getEmail() != null) employee.setEmail(updatedEmployee.getEmail());
        if (updatedEmployee.getPassword() != null) employee.setPassword(updatedEmployee.getPassword());
        return employeeRepository.save(employee);
    }
    
    
}
