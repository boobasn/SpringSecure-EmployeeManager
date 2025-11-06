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

import com.enrtreprise.api.model.Contrat;
import com.enrtreprise.api.service.ContratService;

@RestController
public class ContratController {
    @Autowired
    ContratService contratService ;

    @GetMapping("/contrat")
    public Iterable<Contrat> getAllContrat() {
        return contratService.getAllContrats() ;
    }
    @GetMapping("/contrat/{id}")
    public Optional<Contrat>  getMethodName(@PathVariable Long id) {
        return contratService.getContratById(id);
    }
    @PostMapping("contrat")
    public Contrat createContrat(@RequestBody Contrat contrat) {
        
        return contratService.saveContrat(contrat) ;
        
    }
    @DeleteMapping("/contrat/{id}")
    public String deleteContrat(@RequestParam long id) {
        
        return contratService.deleteContrat(id) ;
        
    }
    
    
}
