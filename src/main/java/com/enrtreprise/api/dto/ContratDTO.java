package com.enrtreprise.api.dto;
import java.time.LocalDate;

import com.enrtreprise.api.model.Contrat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor 
@NoArgsConstructor
public class ContratDTO {
    private Long id;
    private Contrat.TypeContrat typeContrat;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Double salaire;
    private String statut;
    // Champs de l'employé lié au contrat
    private String employeeFirstName;
    private String employeeLastName;
    private String employeeEmail;
}

