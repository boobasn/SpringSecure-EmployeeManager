package com.enrtreprise.api.service;

import java.util.List;
import java.util.Optional ; //import de la classe Optional de Java pour gérer les valeurs pouvant être nulles.
import com.enrtreprise.api.exception.BadRequestException;
import com.enrtreprise.api.exception.ResourceNotFoundException;
import com.enrtreprise.api.model.Department;
import com.enrtreprise.api.model.Employee;
import com.enrtreprise.api.model.Poste ;
import org.springframework.stereotype.Service; //import l'annotation Service de Spring pour indiquer que cette classe est un service métier.
import com.enrtreprise.api.mapper.PosteMapper;
import com.enrtreprise.api.dto.PosteDTO;
import org.springframework.beans.factory.annotation.Autowired; //import de l'annotation Autowired de Spring pour l'injection de dépendances.

import com.enrtreprise.api.repository.DepartmentRepository;
import com.enrtreprise.api.repository.PosteRepository; 
import org.springframework.stereotype.Service; //import l'annotation Service de Spring pour indiquer que cette classe est un service métier.

import lombok.Data; //import de l'annotation lombok pour générer les getters et setters automatiquement.

@Data // Génère les getters et setters pour les champs de la classe automatiquement.
@Service // Indique que cette classe est un service métier dans le contexte Spring.
public class PosteService {
        @Autowired
    PosteRepository posteRepository;

    // creer un poste 
    public Poste savePoste(Poste poste)
    {
        if (posteExists(poste.getTitre())) {
            throw new BadRequestException("Le poste avec le titre '" + poste.getTitre() + "' existe déjà.");
        }
        
        try {
            return posteRepository.save(poste);
        } catch (Exception e) {
            throw e;
        } 
    }
    // verifier si le poste existe
    public boolean posteExists(String titre)
    {
        try {
            Optional<Poste> poste = posteRepository.findByTitre(titre);
            if (poste.isPresent()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw e;
        } 
    }

    // recuperer tout poste 
    public List<Poste> getAllPostes()
    {
        try {
        return (List<Poste>) posteRepository.findAll();
        } catch (Exception e) {
            throw e;
        }
    }
    //recuperer un contract par son id
    public Poste getPosteById(String id) 
    {
     try{       
        return posteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("poste  non trouvé avec l'ID : " + id));
        
        } catch (ResourceNotFoundException e){
            throw e ;
        }catch (Exception e){
            throw e;
        }
    }

    // supprimer un contrat
 

    public String deletePoste(String id)
    {
        posteRepository.deleteById(id);

        return "contrat supprimer avec succes" ;
    }

    public Poste updatePoste(String id, Poste updatePoste)
    {
        Optional<Poste> opt  =  posteRepository.findById(id);
        if (!opt.isPresent()) {
            throw new ResourceNotFoundException("poste non trouvé avec l'ID : " + id);
        }

        try {
            Poste existingPoste = opt.get();
            if (updatePoste.getTitre() != null) {
                existingPoste.setTitre(updatePoste.getTitre());
            }
            if (updatePoste.getDescription() != null) {
                existingPoste.setDescription(updatePoste.getDescription());
            }
            return posteRepository.save(existingPoste);
            

        } catch (Exception e) {
            throw e;
        } 
    }
}