package com.enrtreprise.api.repository;

import com.enrtreprise.api.model.SalaryConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SalaryConfigRepository extends JpaRepository<SalaryConfig, String> {
    Optional<SalaryConfig> findByEmployeeId(String employeeId);
}
