package com.enrtreprise.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Data;

import java.lang.annotation.ElementType;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Data
@Entity
@Table(name = "contrats")
public class Contrat {
    public enum TypeContrat {
    CDI,
    CDD,
    STAGE,
    FREELANCE
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id ;
    @Enumerated(EnumType.STRING)

    @Column(name ="type")
    private TypeContrat typeContrat;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Double salaire;
    private String statut;

    @OneToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee ;

    

    
}
