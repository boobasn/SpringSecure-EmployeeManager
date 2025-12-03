package com.enrtreprise.api.model;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "salary_config")
public class SalaryConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false, unique = true)
    private com.enrtreprise.api.model.Employee employee;

    private Double baseSalary = 0.0;
    private Double transportAllowance = 0.0;
    private Double housingAllowance = 0.0;
    private Double performanceBonus = 0.0;
}

    

