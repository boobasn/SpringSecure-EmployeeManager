package com.enrtreprise.api.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional ; //import de la classe Optional de Java pour gérer les valeurs pouvant être nulles.
import com.enrtreprise.api.exception.BadRequestException;
import com.enrtreprise.api.exception.ResourceNotFoundException;

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
    public Department saveDepartment(Department department)
    {
        if (departmentExists(department.getName())) {
            throw new BadRequestException("Le département avec le nom '" + department.getName() + "' existe déjà.");
        }
        
        try {
            return departmentRepository.save(department);
        } catch (Exception e) {
            throw e;
        } 
    }
    // verifier si le departement existe
    public boolean departmentExists(String name)
    {
        try {
            Optional<Department> department = departmentRepository.findByName(name);
            if (department.isPresent()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw e;
        } 
    }

    // recuperer tout contrat 
    public List<Department> getAllDepartments()
    {
        try {
        return (List<Department>) departmentRepository.findAll();
        } catch (Exception e) {
            throw e;
        }
    }
    //recuperer un contract par son id
    public Department getDepartmentById(String id) 
    {
     try{       
        return departmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Département non trouvé avec l'ID : " + id));
        
        } catch (ResourceNotFoundException e){
            throw e ;
        }catch (Exception e){
            throw e;
        }
    }

    // supprimer un contrat
 

    public String delatedepartment(String id)
    {
        departmentRepository.deleteById(id);

        return "contrat supprimer avec succes" ;
    }

    public List<Employee> getAllEmployee(String  departmentId)
    {
        return (List<Employee>) departmentRepository.findAllEmployee(departmentId);

        
    }
    public Department updateDepartment(String id, Department updateDepartment)
    {
        Optional<Department> opt  =  departmentRepository.findById(id);
        if (!opt.isPresent()) {
            throw new ResourceNotFoundException("Département non trouvé avec l'ID : " + id);
        }

        try {
            Department existingDepartment = opt.get();
            if (updateDepartment.getName() != null) {
                existingDepartment.setName(updateDepartment.getName());
            }
            if (updateDepartment.getDescription() != null) {
                existingDepartment.setDescription(updateDepartment.getDescription());
            }
            return departmentRepository.save(existingDepartment);
            

        } catch (Exception e) {
            throw e;
        } 
    }
}
