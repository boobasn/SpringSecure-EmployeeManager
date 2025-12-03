package com.enrtreprise.api.repository;

import com.enrtreprise.api.model.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PayrollRepository extends JpaRepository<Payroll, String> {
    List<Payroll> findByEmployeeIdOrderByYearDescMonthDesc(String employeeId);
    List<Payroll> findByMonthAndYear(int month, int year);
    Optional<Payroll> findByEmployeeIdAndMonthAndYear(String employeeId, int month, int year);
}