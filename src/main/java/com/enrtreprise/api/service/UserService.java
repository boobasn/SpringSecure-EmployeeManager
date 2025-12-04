package com.enrtreprise.api.service;
import com.enrtreprise.api.exception.ResourceNotFoundException;
import com.enrtreprise.api.model.User;
import com.enrtreprise.api.repository.UserRepository;
import lombok.Data; //import de l'annotation lombok pour générer les getters et setters automatiquement.

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; 

@Data // Génère les getters et setters pour les champs de la classe automatiquement.
@Service // Indique que cette classe est un service métier dans le contexte Spring.
public class UserService {
    @Autowired // Injection automatique de l'instance d'EmployeeRepository par Spring.
    private UserRepository userRepository;   


        public User getUserByUsername(String username) {
        try {
            User user = userRepository.findByUsername(username);

            if (user == null) {
                throw new ResourceNotFoundException(
                    "user  non trouvé avec le username : " + username
                );
            }

            return user;

        } catch (ResourceNotFoundException e) {
            throw e; 

        } catch (Exception e) {
            throw new RuntimeException(
                "Erreur inattendue lors de la recherche du user " + username,
                e
            );
        }
    }
}
