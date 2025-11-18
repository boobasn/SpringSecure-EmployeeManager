package com.enrtreprise.api.service;

import java.util.List;
import java.util.Optional ; //import de la classe Optional de Java pour gérer les valeurs pouvant être nulles.
import org.springframework.beans.factory.annotation.Autowired; //import de l'annotation Autowired de Spring pour l'injection de dépendances.
import org.springframework.stereotype.Service; //import l'annotation Service de Spring pour indiquer que cette classe est un service métier.
import com.enrtreprise.api.model.Contrat; //import de la classe Employee pour manipuler les objets employés.
import com.enrtreprise.api.model.Employee;
import com.enrtreprise.api.repository.ContratRepository; //import de l'interface EmployeeRepository pour accéder aux opérations CRUD sur les employés.
import com.enrtreprise.api.exception.BadRequestException;
import com.enrtreprise.api.exception.ResourceNotFoundException;

import lombok.Data; //import de l'annotation lombok pour générer les getters et setters automatiquement.

@Data // Génère les getters et setters pour les champs de la classe automatiquement.
@Service // Indique que cette classe est un service métier dans le contexte Spring.
public class ContratService {
    @Autowired
    ContratRepository contratRepository;

    // creer un contrat 
    public Contrat saveContrat(Contrat contrat)
    {  
        try {
            Employee emp = contrat.getEmployee();
            if (emp == null) {
                throw new BadRequestException("L'employé associé au contrat ne peut pas être nul.");
            }
            // Vous pouvez ajouter d'autres validations ici si nécessaire
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
        Contrat savedContrat =  contratRepository.save(contrat);  
        return savedContrat;    
    }

    // recuperer tout contrat 
    public List<Contrat> getAllContrats()
    {
        try {
        return (List<Contrat>) contratRepository.findAll();
        } catch (Exception e) {
            throw e;
        }
    }
    //recuperer un contract par son id
    public Contrat getContratById(String id) 
    {
        try {
            return contratRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Contrat non trouvé avec l'ID : " + id));
        } catch (ResourceNotFoundException e){
            throw e ;
        }catch (Exception e){
            throw e;
        }
    }

    // supprimer un contrat

    public String deleteContrat(String id)
    {
        try {
            contratRepository.deleteById(id);
        } catch (Exception e) {
            throw e;
        }
        return "contrat supprimer avec succes" ;
    }

    
    
}
