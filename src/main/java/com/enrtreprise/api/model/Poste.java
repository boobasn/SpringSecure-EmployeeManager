package com.enrtreprise.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Entity
@Table(name = "postes")

public class Poste {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id ;


    private String titre;
    private String description;

    @OneToMany(mappedBy = "poste")
    private List<Employee> employes;
}
