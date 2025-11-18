package com.enrtreprise.api.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.enrtreprise.api.dto.ContratDTO;
import com.enrtreprise.api.mapper.ContratMapper;
import com.enrtreprise.api.mapper.EmployeeMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
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
    public ResponseEntity<List<ContratDTO>> getAllContrat() {
        List<Contrat> contrats = contratService.getAllContrats();
        List<ContratDTO> contratDTOs = contrats.stream()
                .map(ContratMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(contratDTOs);
    }

    /**
     * MIN, MANAGER et EMPLOYEE peuvent voir un contrat spécifique
     *  Pour les employés, on peut plus tard restreindre à "leur propre contrat"
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE')")
    public ResponseEntity<ContratDTO> getContratById(@PathVariable String id) {
        Contrat contrat = contratService.getContratById(id);
        return ResponseEntity.ok(ContratMapper.toDTO(contrat));
    }

    /**
     * Seul un ADMIN peut créer un contrat
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ContratDTO> createContrat(@RequestBody Contrat contrat) {
        Contrat savedContrat = contratService.saveContrat(contrat);
        return ResponseEntity.ok(ContratMapper.toDTO(savedContrat));
    }

    /**
     * ADMIN et MANAGER peuvent mettre à jour un contrat
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<ContratDTO> updateContrat(@PathVariable String id, @RequestBody Contrat contrat) {
        contrat.setId(id);
        return ResponseEntity.ok(ContratMapper.toDTO(contratService.saveContrat(contrat)));
    }

    /**
     * Seul un ADMIN peut supprimer un contrat
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteContrat(@PathVariable String id) {
        String result = contratService.deleteContrat(id);
        return ResponseEntity.ok(result);
    }
}
