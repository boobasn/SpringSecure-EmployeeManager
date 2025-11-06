package com.enrtreprise.api.service;

import org.springframework.beans.factory.annotation.Autowired; //import de l'annotation Autowired de Spring pour l'injection de dépendances.
import org.springframework.stereotype.Service; //import l'annotation Service de Spring pour indiquer que cette classe est un service métier.
import com.enrtreprise.api.repository.AttendanceRepository; //import de l'interface EmployeeRepository pour accéder aux opérations CRUD sur les employés.

import lombok.Data; //import de l'annotation lombok pour générer les getters et setters automatiquement.

@Data // Génère les getters et setters pour les champs de la classe automatiquement.
@Service // Indique que cette classe est un service métier dans le contexte Spring.
public class AttendanceService {
    @Autowired
    AttendanceRepository attendanceRepository;

    

    
}
