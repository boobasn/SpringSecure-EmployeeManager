package com.enrtreprise.api.service;

import java.util.Optional ; //import de la classe Optional de Java pour gérer les valeurs pouvant être nulles.
import org.springframework.beans.factory.annotation.Autowired; //import de l'annotation Autowired de Spring pour l'injection de dépendances.
import org.springframework.stereotype.Service; //import l'annotation Service de Spring pour indiquer que cette classe est un service métier.
import com.enrtreprise.api.model.Contrat; //import de la classe Employee pour manipuler les objets employés.
import com.enrtreprise.api.repository.ContratRepository; //import de l'interface EmployeeRepository pour accéder aux opérations CRUD sur les employés.


import lombok.Data; //import de l'annotation lombok pour générer les getters et setters automatiquement.

@Data // Génère les getters et setters pour les champs de la classe automatiquement.
@Service // Indique que cette classe est un service métier dans le contexte Spring.
public class ContratService {
    @Autowired
    ContratRepository contratRepository;

    // creer un contrat 
    public Contrat saveContrat(Contrat contrat)
    {
        Contrat savedContrat =  contratRepository.save(contrat);  
        return savedContrat;    
    }

    // recuperer tout contrat 
    public Iterable<Contrat> getAllContrats()
    {
        return contratRepository.findAll();
    }
    //recuperer un contract par son id
    public Optional<Contrat> getContratById(Long id) 
    {
        return contratRepository.findById(id);
    }

    // supprimer un contrat

    public String deleteContrat(Long id)
    {
        contratRepository.deleteById(id);

        return "contrat supprimer avec succes" ;
    }

    
    
}
