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
    private String id;
    private Contrat.TypeContrat typeContrat;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Double salaire;
    private String statut;
    private String employeeId;
}

