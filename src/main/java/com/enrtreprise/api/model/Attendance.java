package com.enrtreprise.api.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "presence")
public class Attendance {
    public enum Etat {
    PRESENT,
    ABSENT,
    RETARD,
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id ;

    private LocalDate date ;
    private LocalTime heureArrive ;
    private LocalTime heureDepart;
    private Etat etat ;

}
