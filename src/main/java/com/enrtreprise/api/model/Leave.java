package com.enrtreprise.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "conge")

public class Leave {
    
    public enum TypeConge {
    MALADIE,
    ANNUELLE,
    AUTRE,
    
    }
    public enum Status {
    ATTENTE,
    APPROUVE,
    REFUSE,
    
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id ;
    
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TypeConge typeConge ;
    
    @Column(name = "date_debut")
    private LocalDate dateDebut ;
    @Column(name = "date_fin")
    private LocalDate dateFin ;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status ;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @JsonBackReference
    private Employee employee;
  


    
}
