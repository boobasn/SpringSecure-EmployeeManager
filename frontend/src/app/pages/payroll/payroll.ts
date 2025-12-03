import { Component, OnInit } from '@angular/core';
import { Payroll } from '../../model/payroll';
import { PayrollService } from '../../Services/payroll';
import { CommonModule } from '@angular/common'
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-payroll',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './payroll.html',
  styleUrls: ['./payroll.css'],
})
export class PayrollComponent implements OnInit {

  payrolls: Payroll[] = [];
  loading = true;
  errorMessage = '';

  selectedPayroll: Payroll | null = null;

  /** 🔎 champs recherche */
  employeeId = '';
  selectedMonth!: number;
  selectedYear!: number;

  months = [1,2,3,4,5,6,7,8,9,10,11,12];
  years = [2023, 2024, 2025, 2026];

  constructor(private payrollService: PayrollService) {}

  ngOnInit(): void {
    this.loadPayrolls();
  }

  /** Chargement général */
  loadPayrolls(): void {
    this.loading = true;

    this.payrollService.getAllPayrolls().subscribe({
      next: (data) => {
        this.payrolls = data;
        this.loading = false;
      },
      error: () => {
        this.errorMessage = 'Impossible de charger les données';
        this.loading = false;
      }
    });
  }

  /** 🔎 Recherche employé */
  searchByEmployee(): void {
    if (!this.employeeId.trim()) return;

    this.payrollService
      .getPayrollsByEmployee(this.employeeId)
      .subscribe((data) => {
        this.payrolls = data;
      });
  }

  /** 📅 Filtre mois */
  searchByMonth(): void {
    if (!this.selectedMonth || !this.selectedYear) return;

    this.payrollService
      .getByMonth(this.selectedMonth, this.selectedYear)
      .subscribe((data) => {
        this.payrolls = data;
      });
  }

  /** ⚙ Génération batch */
  generateBatch(): void {
    if (!this.selectedMonth || !this.selectedYear) return;

    this.payrollService
      .generateBatch(this.selectedMonth, this.selectedYear)
      .subscribe((data) => {
        this.payrolls = data;
      });
  }

  /** STATS */
  get paidCount(): number {
    return this.payrolls.filter(p => p.status === 'PAID').length;
  }

  get generatedCount(): number {
    return this.payrolls.filter(p => p.status === 'GENERATED').length;
  }

  /** Détails */
  viewDetails(p: Payroll): void {
    this.selectedPayroll = p;
  }

  /** PDF */
  download(p: Payroll): void {
    this.payrollService
      .downloadPayslip(p.id!)
      .subscribe((file) => {

        const url = window.URL.createObjectURL(file);

        const a = document.createElement('a');
        a.href = url;
        a.download = `payslip_${p.employeeFirstName}.pdf`;
        a.click();
      });
  }

  /** Paiement */
  markPaid(p: Payroll): void {
    this.payrollService.markAsPaid(p.id!).subscribe(() => {
      p.status = 'PAID';
    });
  }

}
