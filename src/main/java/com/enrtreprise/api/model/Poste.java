package com.enrtreprise.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "postes")

public class Poste {

    public static Poste valueOf(String poste) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id ;


    private String titre;
    private String description;

    @OneToMany(mappedBy = "poste")
    private List<Employee> employes;
}
