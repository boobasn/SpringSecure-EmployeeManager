package com.enrtreprise.api.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import com.enrtreprise.api.model.Employee;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "payroll")
public class Payroll {

    public enum Status {
    GENERATED,
    PAID,    
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    // Référence à l'entité Employee existante
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(nullable = false)
    private Integer month; // 1..12

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private Double baseSalary = 0.0;

    @Column(nullable = false)
    private Double bonuses = 0.0;

    @Column(nullable = false)
    private Double allowances = 0.0;

    @Column(nullable = false)
    private Double deductions = 0.0;

    @Column(nullable = false)
    private Double netSalary = 0.0;

    @Column(nullable = false, length = 20)
    private Status status = Status.GENERATED; // GENERATED | PAID

    private String payslipUrl;

    private LocalDateTime createdAt = LocalDateTime.now();
}
