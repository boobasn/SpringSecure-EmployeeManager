package com.enrtreprise.api.service;

import com.enrtreprise.api.model.User;
import com.enrtreprise.api.model.Employee;
import com.enrtreprise.api.model.Manager;
import com.enrtreprise.api.repository.UserRepository;
import com.enrtreprise.api.repository.EmployeeRepository;
import com.enrtreprise.api.repository.ManagerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("securityService") // important pour @PreAuthorize
public class SecurityService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ManagerRepository managerRepository;

    /**
     * Vérifie si l'utilisateur authentifié correspond à l'ID du User.
     * Utilisé pour @PreAuthorize("hasRole('ADMIN') or @securityService.isSelf(#id)")
     */
    public boolean isSelf(Long userId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) return false;

        String currentUsername = auth.getName();
        User user = userRepository.findById(userId).orElse(null);
        return user != null && user.getUsername().equals(currentUsername);
    }

    /**
     * Vérifie si l'utilisateur authentifié est lié à l'Employee avec cet ID
     */
    public boolean isSelfEmployee(Long employeeId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) return false;

        String currentUsername = auth.getName();
        Employee employee = employeeRepository.findById(employeeId).orElse(null);

        return employee != null &&
               employee.getUser() != null &&
               employee.getUser().getUsername().equals(currentUsername);
    }

    /**
     * Vérifie si l'utilisateur authentifié est lié au Manager avec cet ID
     */
    public boolean isSelfManager(Long managerId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) return false;

        String currentUsername = auth.getName();
        Manager manager = managerRepository.findById(managerId).orElse(null);

        return manager != null &&
               manager.getUser() != null &&
               manager.getUser().getUsername().equals(currentUsername);
    }

    // Ici tu peux ajouter d'autres méthodes similaires pour d'autres entités si nécessaire
}
