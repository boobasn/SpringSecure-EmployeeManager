package com.enrtreprise.api.service;

import java.util.Optional ; //import de la classe Optional de Java pour gérer les valeurs pouvant être nulles.
import org.springframework.beans.factory.annotation.Autowired; //import de l'annotation Autowired de Spring pour l'injection de dépendances.
import org.springframework.stereotype.Service; //import l'annotation Service de Spring pour indiquer que cette classe est un service métier.
import com.enrtreprise.api.model.Employee; //import de la classe Employee pour manipuler les objets employés.
import org.springframework.security.crypto.password.PasswordEncoder;
import com.enrtreprise.api.repository.EmployeeRepository; //import de l'interface EmployeeRepository pour accéder aux opérations CRUD sur les employés.
import com.enrtreprise.api.exception.BadRequestException;
import com.enrtreprise.api.exception.ResourceAlreadyExistsException;
import com.enrtreprise.api.exception.ResourceAlreadyExistsException;
import com.enrtreprise.api.exception.ResourceNotFoundException;
import com.enrtreprise.api.repository.DepartmentRepository;
import java.util.List;
import com.enrtreprise.api.repository.ContratRepository;
import lombok.Data; //import de l'annotation lombok pour générer les getters et setters automatiquement.
@Data // Génère les getters et setters pour les champs de la classe automatiquement.
@Service // Indique que cette classe est un service métier dans le contexte Spring.
public class EmployeeService {
    @Autowired // Injection automatique de l'instance d'EmployeeRepository par Spring.
    private EmployeeRepository employeeRepository;
     
    @Autowired 
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private ContratRepository contratRepository ;

    @Autowired
    private DepartmentRepository departmentRepository;
    // Méthode pour récupérer un employé par son identifiant.
    public Employee getEmployeeById(String id) {
        try{
        return employeeRepository.findById(id).orElseThrow(() -> new  ResourceNotFoundException("Employé non trouvé avec l'ID : " + id));
        }catch ( ResourceNotFoundException e){
            throw  e;
        }catch (Exception e){
             throw e;
        }
    }
    public Employee getEmployeeByName(String firstname, String lastname) {
        try {
            Employee employee = employeeRepository.findByFirstname(firstname, lastname);

            if (employee == null) {
                throw new ResourceNotFoundException(
                    "Employé non trouvé avec le nom : " + firstname + " " + lastname
                );
            }

            return employee;

        } catch (ResourceNotFoundException e) {
            throw e; 

        } catch (Exception e) {
            throw new RuntimeException(
                "Erreur inattendue lors de la recherche de l'employé " + firstname + " " + lastname,
                e
            );
        }
    }

    public List<Employee> getEmployees() {
        try {
            return (List<Employee>) employeeRepository.findAll();
        } catch (Exception e) {
            throw e;
        }
    }
    public Employee saveEmployee(Employee employee) {

        // 1️⃣ Validation des champs obligatoires
        if (employee == null) {
            throw new BadRequestException("L'employé ne peut pas être nul.");
        }
        if (employee.getEmail() == null || employee.getEmail().isEmpty()) {
            throw new BadRequestException("L'email de l'employé ne peut pas être nul ou vide.");
        }

        try {
            // 2️⃣ Vérifier si l'email existe déjà
            if (employeeRepository.existsByEmail(employee.getEmail())) {
                throw new ResourceAlreadyExistsException("Un employé avec cet email existe déjà : " + employee.getEmail());
            }

            // 3️⃣ Vérifier et encoder le mot de passe si user présent
            if (employee.getUser() != null) {
                if (employee.getUser().getPassword() == null || employee.getUser().getPassword().isEmpty()) {
                    throw new BadRequestException("Le mot de passe de l'utilisateur ne peut pas être nul ou vide.");
                }
                String encodedPassword = passwordEncoder.encode(employee.getUser().getPassword());
                employee.getUser().setPassword(encodedPassword);
            }

            // 4️⃣ Enregistrer l'employé
            return employeeRepository.save(employee);

        } catch (Exception e) {
            throw e; // Renvoyée au GlobalExceptionHandler
        } 
    }

    public String deleteEmployee(String id){
        
        employeeRepository.deleteById(id);
        return "employee supprime avec success" ;
    }
    public Employee updateEmployee(String id, Employee updatedEmployee){
        
        Optional<Employee> opt  =  employeeRepository.findById(id);
        try{
        if (!opt.isPresent()){
            throw new  ResourceNotFoundException("Employee not found with id: " + id);
        }
        }catch (ResourceNotFoundException e){
            throw e ;
        }catch (Exception e){
            throw e;
        }
        try{
            if (updatedEmployee.getEmail() != null && !updatedEmployee.getEmail().isEmpty()) {
                // Vérifier si un autre employé utilise déjà cet email
                Optional<Employee> existingEmployee = employeeRepository.findById(id);
                if (existingEmployee.isPresent() && !existingEmployee.get().getId().equals(id)) {
                    throw new ResourceAlreadyExistsException("Un autre employé utilise déjà cet email : " + updatedEmployee.getEmail());
                }
            }
        } catch (ResourceAlreadyExistsException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
        Employee employee = opt.get();
        if (updatedEmployee.getFirstName() != null) employee.setFirstName(updatedEmployee.getFirstName());
        if (updatedEmployee.getLastName() != null) employee.setLastName(updatedEmployee.getLastName());
        if (updatedEmployee.getEmail() != null) employee.setEmail(updatedEmployee.getEmail());
        return employeeRepository.save(employee);
    }
    
    
}
