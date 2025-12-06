package com.enrtreprise.api.service;

import java.util.Optional ; //import de la classe Optional de Java pour gérer les valeurs pouvant être nulles.
import org.springframework.beans.factory.annotation.Autowired; //import de l'annotation Autowired de Spring pour l'injection de dépendances.
import org.springframework.stereotype.Service; //import l'annotation Service de Spring pour indiquer que cette classe est un service métier.

import com.enrtreprise.api.model.Department;
import com.enrtreprise.api.model.Employee; //import de la classe Employee pour manipuler les objets employés.
import com.enrtreprise.api.model.Poste;

import org.springframework.security.crypto.password.PasswordEncoder;
import com.enrtreprise.api.repository.EmployeeRepository; //import de l'interface EmployeeRepository pour accéder aux opérations CRUD sur les employés.
import com.enrtreprise.api.exception.BadRequestException;
import com.enrtreprise.api.exception.ResourceAlreadyExistsException;
import com.enrtreprise.api.exception.ResourceAlreadyExistsException;
import com.enrtreprise.api.exception.ResourceNotFoundException;
import com.enrtreprise.api.repository.DepartmentRepository;
import com.enrtreprise.api.model.User; 
import com.enrtreprise.api.repository.ContratRepository;

import java.security.SecureRandom;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;


import com.enrtreprise.api.repository.LeaveRepository;
import com.enrtreprise.api.repository.PosteRepository;

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
    private PosteRepository posteRepository;

    @Autowired
    private LeaveRepository leaveRepository;
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

    // get employee by UserID
    public Employee getEmployeeByUserId(String userId) {
        try{
        return employeeRepository.findByUser_Id(userId);
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

    private String generatePassword() {

    String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@!#$%";
    SecureRandom random = new SecureRandom();

    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < 10; i++) {
        sb.append(chars.charAt(random.nextInt(chars.length())));
    }

    return sb.toString();
    }



    public Employee saveEmployee(Employee employee) {

        if (employee == null) {
            throw new BadRequestException("L'employé ne peut pas être nul.");
        }

        if (employee.getEmail() == null || employee.getEmail().isEmpty()) {
            throw new BadRequestException("L'email est obligatoire.");
        }

        if (employee.getFirstName() == null || employee.getLastName() == null) {
            throw new BadRequestException("Le prénom et le nom sont obligatoires.");
        }

        // ✅ Vérifier email unique
        if (employeeRepository.existsByEmail(employee.getEmail())) {
            throw new ResourceAlreadyExistsException(
                "Un employé existe déjà avec cet email : " + employee.getEmail()
            );
        }


        // ✅ Supprimer poste vide
        if(employee.getPoste() != null && employee.getPoste().getId() == null){
            employee.setPoste(null);
        }

        // ✅ Supprimer departement vide
        if(employee.getDepartement() != null && employee.getDepartement().getId() == null){
            employee.setDepartement(null);
        }



            // ✅ Fix POSTE
            if(employee.getPoste() != null && employee.getPoste().getId() != null){

                Poste poste = posteRepository.findById(employee.getPoste().getId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                        "Poste introuvable: " + employee.getPoste().getId()
                    ));

                employee.setPoste(poste);
            }

            // ✅ Fix DEPARTEMENT
            if(employee.getDepartement() != null && employee.getDepartement().getId() != null){

                Department dep = departmentRepository.findById(
                        employee.getDepartement().getId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                        "Département introuvable: " + employee.getDepartement().getId()
                    ));

                employee.setDepartement(dep);
            }
        try {

            // =======================
            // ✅ CREATION AUTOMATIQUE USER
            // =======================

            // Username = lastName + première lettre du firstName
            String username = 
                employee.getLastName().toLowerCase() +
                employee.getFirstName().substring(0,1).toLowerCase();

            // Password random sécurisé
            String rawPassword = generatePassword();

            User user = new User();
            user.setUsername(username);
            user.setRole(User.ROLE_EMPLOYE);

            // Hash password
            user.setPassword(passwordEncoder.encode(rawPassword));

            // Lien user -> employee
            employee.setUser(user);

            // =======================
            // ✅ SAVE EMPLOYEE
            // =======================
            Employee savedEmployee = employeeRepository.save(employee);

            // =======================
            // ✅ LOG POUR L’ADMIN
            // =======================
            System.out.println("USER CRÉÉ:");
            System.out.println("  Username : " + username);
            System.out.println("  Mot de passe : " + rawPassword);
            System.out.println("  Rôle : EMPLOYE");

            return savedEmployee;

        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public String deleteEmployee(String id){
        leaveRepository.deleteByEmployeeId(id);
        contratRepository.deleteByEmployeeId(id); 
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
        if (updatedEmployee.getFirstName() != null)
        employee.setFirstName(updatedEmployee.getFirstName());

        if (updatedEmployee.getEmail() != null)
            employee.setEmail(updatedEmployee.getEmail());
        if (updatedEmployee.getLastName() != null)
            employee.setLastName(updatedEmployee.getLastName());
        if (updatedEmployee.getTelephone() != null)
            employee.setTelephone(updatedEmployee.getTelephone());

        if (updatedEmployee.getAdresse() != null)
            employee.setAdresse(updatedEmployee.getAdresse());
        if (updatedEmployee.getMatricule() != null)
            employee.setMatricule(updatedEmployee.getMatricule());

        if (updatedEmployee.getSexe() != null)
            employee.setSexe(updatedEmployee.getSexe());
        if (updatedEmployee.getDateEmbauche() != null)
            employee.setDateEmbauche(updatedEmployee.getDateEmbauche());
        if (updatedEmployee.getContrat() != null)
            employee.setContrat(updatedEmployee.getContrat());
        if (updatedEmployee.getSalaire() != 0)
            employee.setSalaire(updatedEmployee.getSalaire());
        if (updatedEmployee.getSexe() != null)
            employee.setSexe(updatedEmployee.getSexe());
        if (updatedEmployee.getDepartement() != null)
            employee.setDepartement(updatedEmployee.getDepartement());
        if(updatedEmployee.getPoste() != null && updatedEmployee.getPoste().getId() != null){

        Poste poste = posteRepository.findById(
            updatedEmployee.getPoste().getId()
        ).orElseThrow(() ->
            new ResourceNotFoundException("Poste introuvable")
        );
        employee.setPoste(poste);
      
    }

    if(updatedEmployee.getDepartement() != null && updatedEmployee.getDepartement().getId() != null){

        Department dep = departmentRepository.findById(
           updatedEmployee.getDepartement().getId()
        ).orElseThrow(() ->
            new ResourceNotFoundException("Département introuvable")
        );

        employee.setDepartement(dep);
    }

        return employeeRepository.save(employee);
        
    }
    // reucperer le nombre total des employees
    public long getTotalEmployees(){
        return employeeRepository.count();
    }
    
    
}
