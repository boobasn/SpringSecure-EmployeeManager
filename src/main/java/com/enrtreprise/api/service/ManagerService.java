package com.enrtreprise.api.service;
import java.util.Optional ; //import de la classe Optional de Java pour gérer les valeurs pouvant être nulles.
import org.springframework.beans.factory.annotation.Autowired; //import de l'annotation Autowired de Spring pour l'injection de dépendances.
import org.springframework.stereotype.Service; //import l'annotation Service de Spring pour indiquer que cette classe est un service métier.
import com.enrtreprise.api.model.Manager; //import de la classe Employee pour manipuler les objets employés.
import org.springframework.security.crypto.password.PasswordEncoder;
import com.enrtreprise.api.repository.ManagerRepository; //import de l'interface EmployeeRepository pour accéder aux opérations CRUD sur les employés.

import lombok.Data; //import de l'annotation lombok pour générer les getters et setters automatiquement.

@Data // Génère les getters et setters pour les champs de la classe automatiquement.
@Service // Indique que cette classe est un service métier dans le contexte Spring.
public class ManagerService {
    
    @Autowired
    private ManagerRepository managerRepository;

    @Autowired 
    private final PasswordEncoder passwordEncoder;

    // creer un manager
    public Manager saveManager(Manager manager)
    {
        if (manager.getUser() != null) {
            // On chiffre le mot de passe AVANT l’enregistrement
            String encodedPassword = passwordEncoder.encode(manager.getUser().getPassword());
            manager.getUser().setPassword(encodedPassword);
        }

        // Maintenant on peut sauvegarder
        return managerRepository.save(manager);
    }

    // recuperer tout les managers
    public Iterable<Manager> getManagers() {
        return managerRepository.findAll();
    }

    // recuperer un manager par son id

    public Optional<Manager> getManagerById(String id) {
        return managerRepository.findById(id);
    }

    // modifer un manager
    public Manager updateManager(String id , Manager manager){
        Optional<Manager> opt  =  managerRepository.findById(id);
        if (!opt.isPresent()){
            throw new  RuntimeException("Manager not found with id: " + id);
        }
        Manager existingManager = opt.get();
        if (manager.getFirstName() != null) existingManager.setFirstName(manager.getFirstName());
        if (manager.getLastName() != null) existingManager.setLastName(manager.getLastName());
        if (manager.getEmail() != null) existingManager.setEmail(manager.getEmail());
        if (manager.getTelephone() != null) existingManager.setTelephone(manager.getTelephone());
        // Ajouter d'autres champs selon les besoins

        return managerRepository.save(existingManager);

    }
    // supprimer un manager
    public String deleteManager(String id){
        managerRepository.deleteById(id);
        return "Manager supprime avec success" ;
    }

}
