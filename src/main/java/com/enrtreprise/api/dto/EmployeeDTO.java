package com.enrtreprise.api.dto;

import lombok.Data;
import java.time.LocalDate;

/**
 * DTO pour représenter un employé sans exposer toute l'entité Employee.
 * Cela protège les données sensibles et permet un meilleur contrôle du format de sortie.
 */
@Data
public class EmployeeDTO {

    private String id;                  // Identifiant unique de l’employé
    private String firstName;         // Prénom
    private String lastName;          // Nom
    private String email;             // Email professionnel
    private String telephone;         // Numéro de téléphone
    private String poste;             // Poste occupé (ex: Technicien, Développeur)
    private LocalDate dateEmbauche;   // Date d'embauche
    private String sexe;              // Sexe (M/F)
    private String adresse;           // Adresse de l'employé
    private String userId;              // Référence vers l’utilisateur lié (pour l’authentification)
   // private Long managerId;           // Manager responsable (si applicable)
}
