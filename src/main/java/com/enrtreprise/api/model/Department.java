package com.enrtreprise.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import java.util.List ;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "departments") 
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(name  = "name")
    private String name ;

    @Column(name  = "description")
    private String description ;

    @OneToMany(mappedBy = "departement")
    @JsonIgnore 
    private List<Employee> employes;

}
