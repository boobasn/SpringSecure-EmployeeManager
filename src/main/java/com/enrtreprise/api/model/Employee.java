package com.enrtreprise.api.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Data
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name  = "first_name")
    private String firstName;

    @Column(name  = "last_name")
    private String lastName;

    private String email;
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
    private Contrat contrat;

    @ManyToOne
    @JoinColumn(name = "poste_id")
    private Poste poste;

    @OneToMany(mappedBy = "employee")
    @JsonManagedReference
    private List<Leave> leaves;

    // ✅ Lien avec l'utilisateur
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
