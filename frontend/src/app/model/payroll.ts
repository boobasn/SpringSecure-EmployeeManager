export interface Payroll {
  id?: number;
  employeeId: number;
  employeeFirstName?: string;
  employeeLastName?: string;

  month: number;     // 1–12
  year: number;

  baseSalary: number;
  bonuses: number;
  allowances: number;
  deductions: number;

  netSalary?: number;  // calculé
  status?: 'GENERATED' | 'PAID';

  payslipUrl?: string;
  createdAt?: string;
}
