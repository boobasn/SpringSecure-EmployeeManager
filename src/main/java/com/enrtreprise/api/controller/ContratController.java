package com.enrtreprise.api.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.enrtreprise.api.model.Contrat;
import com.enrtreprise.api.service.ContratService;

@RestController
@RequestMapping("/contrat") //  Regroupe toutes les routes sous /contrat
public class ContratController {

    @Autowired
    private ContratService contratService;

    /**
     *  ADMIN et MANAGER peuvent voir tous les contrats
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public Iterable<Contrat> getAllContrat() {
        return contratService.getAllContrats();
    }

    /**
     * MIN, MANAGER et EMPLOYEE peuvent voir un contrat spécifique
     *  Pour les employés, on peut plus tard restreindre à "leur propre contrat"
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE')")
    public Optional<Contrat> getContratById(@PathVariable Long id) {
        return contratService.getContratById(id);
    }

    /**
     * Seul un ADMIN peut créer un contrat
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Contrat createContrat(@RequestBody Contrat contrat) {
        return contratService.saveContrat(contrat);
    }

    /**
     * ADMIN et MANAGER peuvent mettre à jour un contrat
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public Contrat updateContrat(@PathVariable Long id, @RequestBody Contrat contrat) {
        contrat.setId(id);
        return contratService.saveContrat(contrat);
    }

    /**
     * Seul un ADMIN peut supprimer un contrat
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteContrat(@PathVariable long id) {
        return contratService.deleteContrat(id);
    }
}
