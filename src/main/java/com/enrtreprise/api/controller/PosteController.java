package com.enrtreprise.api.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.enrtreprise.api.dto.DepartementDTO;
import com.enrtreprise.api.dto.EmployeeDTO;
import com.enrtreprise.api.dto.PosteDTO;
import com.enrtreprise.api.mapper.DepartementMapper;
import com.enrtreprise.api.mapper.EmployeeMapper;
import com.enrtreprise.api.mapper.PosteMapper;
import com.enrtreprise.api.model.Department;
import com.enrtreprise.api.model.Employee;
import com.enrtreprise.api.model.Poste;
import com.enrtreprise.api.service.DepartmentService;
import com.enrtreprise.api.service.PosteService;

import java.util.List;
import java.util.stream.Collectors;
import io.micrometer.core.ipc.http.HttpSender;

@RestController
@RequestMapping("/postes") // Regroupe toutes les routes sous /departments
public class PosteController {

    @Autowired
    private PosteService posteService;

    /**
     * Accessible uniquement aux ADMIN
     * Affiche tous les départements
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PosteDTO>> getAllPostes() {
        List <Poste> postes = posteService.getAllPostes();
        List<PosteDTO> postesDTOs = postes.stream()
                .map(PosteMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(postesDTOs);
    }

    /**
     * ADMIN ou MANAGER peuvent consulter un département
     * Les employés n’y ont pas accès directement
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<PosteDTO> getPosteById(@PathVariable String id) {
        Poste poste = posteService.getPosteById(id);
        PosteDTO posteDTO = PosteMapper.toDTO(poste);
        return ResponseEntity.ok(posteDTO);
    }

    /**
     * Seul un ADMIN peut créer un département
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PosteDTO> savePoste(@RequestBody PosteDTO dto) {
        Poste poste = PosteMapper.toEntity(dto);
        Poste savedPoste = posteService.savePoste(poste);
    return ResponseEntity.ok(PosteMapper.toDTO(savedPoste));

    }

    /**
     * Seul un ADMIN peut supprimer un poste
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deletePoste(@PathVariable String id) {
        return ResponseEntity.ok(posteService.deletePoste(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<PosteDTO> updatePoste(@PathVariable String id, @RequestBody PosteDTO dto)
    {
        Poste updatePoste = PosteMapper.toEntity(dto);
        Poste updatedPoste = posteService.updatePoste( id, updatePoste);
        return ResponseEntity.ok(PosteMapper.toDTO(updatedPoste));
    }    

}
