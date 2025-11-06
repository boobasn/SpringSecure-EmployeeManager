package com.enrtreprise.api.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.List ;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import com.enrtreprise.api.model.User;
import jakarta.persistence.Table;
import lombok.Data;

@Data // Génère les getters, setters, toString, equals et hashCode automatiquement
@Entity // Indique que cette classe est une entité JPA
@Table(name = "employees") // Spécifie le nom de la table dans la base de données
public class Employee {


    @Id // correspond à la clé primaire de la table
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-incrémentation de l'id
    private Long id; // identifiant unique de l'employé
    @Column(name  = "first_name") // nom de la colonne dans la table
    private String firstName;
    @Column(name  = "last_name")
    private String lastName;

    private String email; //Je n’ai pas eu besoin de mettre cette annotation pour mail et password, car le nom du champ de la table est identique.
    private String password;
    private String telephone;
    private String adresse;
    private String sexe;
    private LocalDate dateNaissance;
    private LocalDate dateEmbauche;
    private double salaire;
    @ManyToOne
    @JoinColumn(name = "departement_id")
    private Department departement;

    @OneToOne
    @JoinColumn(name = "contrat_id", referencedColumnName = "id")
    Contrat contrat;

    @ManyToOne
    @JoinColumn(name = "poste_id")
    Poste poste ;

    @OneToMany(mappedBy = "employee")
    @JsonManagedReference
    private List<Leave> leaves;
    
}
