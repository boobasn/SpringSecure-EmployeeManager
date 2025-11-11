package com.enrtreprise.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize; // Pour sécuriser les méthodes
import org.springframework.web.bind.annotation.*;
import com.enrtreprise.api.model.Manager;
import com.enrtreprise.api.service.ManagerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Optional;

@RestController
@RequestMapping("/managers")
public class ManagerController {
    
    @Autowired
    private ManagerService managerService;

        @GetMapping()
        @PreAuthorize("hasRole('ADMIN')")
        public Iterable<Manager> getAllManagers() {
            return managerService.getManagers();
        }

        @GetMapping("/{id}")
        @PreAuthorize("hasRole('ADMIN')or @securityService.isSelfManager(#id)")
        public Optional<Manager> getManagerById(@PathVariable Long id) {
            return managerService.getManagerById(id);
        }

        @PostMapping
        @PreAuthorize("hasRole('ADMIN')")
        public Manager createManager(@RequestBody Manager manager) {
            return managerService.saveManager(manager);
        }
        @PutMapping("/{id}")
        @PreAuthorize("hasRole('ADMIN') or @securityService.isSelfManager(#id)")
        public Manager updateManager(@PathVariable Long id, @RequestBody Manager manager) {
            return managerService.updateManager(id, manager);
        }

        @DeleteMapping("/{id}")
        @PreAuthorize("hasRole('ADMIN')")
        public void deleteManager(@PathVariable Long id) {
            managerService.deleteManager(id);
        }
}
