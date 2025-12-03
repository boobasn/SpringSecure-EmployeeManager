package com.enrtreprise.api.service;


import com.enrtreprise.api.model.Payroll;
import com.enrtreprise.api.model.SalaryConfig;
import com.enrtreprise.api.repository.PayrollRepository;
import com.enrtreprise.api.repository.SalaryConfigRepository;
import com.enrtreprise.api.exception.ResourceNotFoundException;
import com.enrtreprise.api.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PayrollService {

    private final PayrollRepository payrollRepository;
    private final SalaryConfigRepository salaryConfigRepository;
    private final com.enrtreprise.api.repository.EmployeeRepository employeeRepository; // existing repo
    // get all payrolls
    public List<Payroll> getAllPayrolls(){
        try {
        return (List<Payroll>) payrollRepository.findAll();
        } catch (Exception e) {
            throw new BadRequestException("Erreur lors de la récupération des paies: " + e.getMessage());
        }
    }
    /**
     * Générer la paie pour un employé (ou créer manuellement)
     */
    public Payroll createPayrollForEmployee(String employeeId, int month, int year, Double bonuses, Double allowances, Double deductions){
        var emp = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employé introuvable id=" + employeeId));

        // Si une paie existe déjà pour ce mois => erreur
        if (payrollRepository.findByEmployeeIdAndMonthAndYear(employeeId, month, year).isPresent()){
            throw new BadRequestException("Paie déjà générée pour cet employé sur ce mois/année.");
        }

        // Récupérer base salary depuis salaryConfig sinon fallback 0
        Double base = salaryConfigRepository.findByEmployeeId(employeeId)
                .map(SalaryConfig::getBaseSalary)
                .orElse(0.0);

        Payroll p = Payroll.builder()
                .employee(emp)
                .month(month)
                .year(year)
                .baseSalary(base)
                .bonuses(bonuses == null ? 0.0 : bonuses)
                .allowances(allowances == null ? 0.0 : allowances)
                .deductions(deductions == null ? 0.0 : deductions)
                .status(Payroll.Status.GENERATED)
                .build();

        p.setNetSalary(calculateNet(p));
        return payrollRepository.save(p);
    }

    private double calculateNet(Payroll p){
        return round(p.getBaseSalary() + p.getBonuses() + p.getAllowances() - p.getDeductions());
    }

    private double round(double d){
        return Math.round(d * 100.0) / 100.0;
    }

    public List<Payroll> getPayrollsForMonth(int month, int year){
        return payrollRepository.findByMonthAndYear(month, year);
    }

    public Payroll getPayrollById(String id){
        return payrollRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paie introuvable id=" + id));
    }

    public List<Payroll> getPayrollsByEmployee(String empId){
        return payrollRepository.findByEmployeeIdOrderByYearDescMonthDesc(empId);
    }

    public Payroll markAsPaid(String id){
        Payroll p = getPayrollById(id);
        if (Payroll.Status.PAID.equals(p.getStatus())) {
            throw new BadRequestException("Déjà payé.");
        }
        p.setStatus(Payroll.Status.PAID);
        // TODO: ici déclencher intégration paiement (ex: webhook, appel banque) si besoin
        return payrollRepository.save(p);
    }

    /**
     * Génère un lot de paies pour tous les employés à partir de la config salary_config.
     * Si overwriteExisting = false: sauter ceux déjà générés pour le mois.
     */
    public List<Payroll> generatePayrollForMonth(int month, int year, boolean overwriteExisting){
        // récupérer tous les employés
        Iterable<com.enrtreprise.api.model.Employee> employees = employeeRepository.findAll();
        var result = new java.util.ArrayList<Payroll>();
        for (var e : employees) {
            var exists = payrollRepository.findByEmployeeIdAndMonthAndYear(e.getId(), month, year);
            if (exists.isPresent() && !overwriteExisting) continue;
            // récupérer config
            SalaryConfig cfg = salaryConfigRepository.findByEmployeeId(e.getId()).orElse(null);
            double base = cfg != null ? cfg.getBaseSalary() : 0.0;
            double allowances = cfg != null ? cfg.getTransportAllowance() + cfg.getHousingAllowance() : 0.0;
            double bonuses = cfg != null ? cfg.getPerformanceBonus() : 0.0;
            Payroll p = Payroll.builder()
                    .employee(e)
                    .month(month)
                    .year(year)
                    .baseSalary(base)
                    .allowances(allowances)
                    .bonuses(bonuses)
                    .deductions(0.0)
                    .status(Payroll.Status.GENERATED)
                    .build();
            p.setNetSalary(calculateNet(p));
            result.add(payrollRepository.save(p));
        }
        return result;
    }

    // placeholder pour génération PDF - à implémenter
    public String generatePayslipPdf(String payrollId){
        Payroll p = getPayrollById(payrollId);
        // TODO: générer PDF -> stocker et retourner URL
        String dummyUrl = "/uploads/payslips/" + payrollId + ".pdf";
        p.setPayslipUrl(dummyUrl);
        payrollRepository.save(p);
        return dummyUrl;
    }
}
