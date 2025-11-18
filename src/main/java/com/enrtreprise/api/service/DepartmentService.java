package com.enrtreprise.api.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Optional ; //import de la classe Optional de Java pour gérer les valeurs pouvant être nulles.
import org.springframework.beans.factory.annotation.Autowired; //import de l'annotation Autowired de Spring pour l'injection de dépendances.
import org.springframework.stereotype.Service; //import l'annotation Service de Spring pour indiquer que cette classe est un service métier.
import com.enrtreprise.api.model.Department; //import de la classe Employee pour manipuler les objets employés.
import com.enrtreprise.api.repository.DepartmentRepository; //import de l'interface EmployeeRepository pour accéder aux opérations CRUD sur les employés.
import com.enrtreprise.api.model.Employee; 
import lombok.Data; //import de l'annotation lombok pour générer les getters et setters automatiquement.

@Data // Génère les getters et setters pour les champs de la classe automatiquement.
@Service // Indique que cette classe est un service métier dans le contexte Spring.
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;

    // creer un contrat 
    public ResponseEntity<?> saveDepartment(Department department)
    {
        Optional<Department> existingDepartment = departmentRepository.findByName(department.getName());
        if (existingDepartment.isPresent()) {
            // Si le département existe déjà → on le renvoie simplement
            System.out.println(" Département déjà existant : " + department.getName());
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Le département existe déjà : " + department.getName());
        
        }
        Department savedepartment=  departmentRepository.save(department);  
        System.out.println(" Département créé : " + savedepartment.getName());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedepartment);    
    }

    // recuperer tout contrat 
    public Iterable<Department> getAllDepartments()
    {
        return departmentRepository.findAll();
    }
    //recuperer un contract par son id
    public Optional<Department> getDepartmentById(String id) 
    {
        return departmentRepository.findById(id);
    }

    // supprimer un contrat
 

    public String delatedepartment(String id)
    {
        departmentRepository.deleteById(id);

        return "contrat supprimer avec succes" ;
    }

    public Iterable<Employee> getAllEmployee(String  departmentId)
    {
        return departmentRepository.findAllEmployee(departmentId);

        
    }

}
