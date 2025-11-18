package com.enrtreprise.api.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Data
@Entity
@Table(name = "managers")
public class Manager {
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
