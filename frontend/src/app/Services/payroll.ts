import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Payroll } from '../model/payroll';
import { tap } from 'rxjs/operators';
@Injectable({
  providedIn: 'root',
})
export class PayrollService {

  private apiUrl = 'http://127.0.0.1:8080/payroll';

  constructor(private http: HttpClient) {}

  /** Liste de toutes les paies */
getAllPayrolls(): Observable<Payroll[]> {
  return this.http.get<Payroll[]>(this.apiUrl).pipe(
    tap((payrolls) => {
      console.log('Payrolls récupérés :', payrolls);
    })
  );
}

  /** Détails d'une paie */
  getPayrollById(id: number): Observable<Payroll> {
    return this.http.get<Payroll>(`${this.apiUrl}/${id}`);
  }

  /** Génération manuelle : création d'une paie */
  generatePayroll(data: Payroll): Observable<Payroll> {
    return this.http.post<Payroll>(this.apiUrl, data);
  }

  /** Génération batch par mois */
  generateBatch(month: number, year: number): Observable<Payroll[]> {
    return this.http.post<Payroll[]>(
      `${this.apiUrl}/generate/${month}/${year}`,
      {}
    );
  }

  /** Paiement */
  markAsPaid(id: number): Observable<any> {
    return this.http.post(`${this.apiUrl}/pay/${id}`, {});
  }

  /** PDF bulletin */
  downloadPayslip(id: number): Observable<Blob> {
    return this.http.post(
      `${this.apiUrl}/payslip/${id}`,
      {},
      { responseType: 'blob' }
    );
  }

  /** 🔎 Historique par employé */
  getPayrollsByEmployee(employeeId: string): Observable<Payroll[]> {
    return this.http.get<Payroll[]>(
      `${this.apiUrl}/employee/${employeeId}`
    );
  }

  /** 📅 Filtres mois / année */
  getByMonth(month: number, year: number): Observable<Payroll[]> {
    return this.http.get<Payroll[]>(
      `${this.apiUrl}/month/${month}/${year}`
    );
  }

}
