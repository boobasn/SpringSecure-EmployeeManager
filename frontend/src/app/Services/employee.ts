import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Employee } from '../model/employee';
import { Observable, tap } from 'rxjs';
@Injectable({
  providedIn: 'root',
})
export class EmployeeService {
 private apiUrl = 'http://localhost:8080/employees';

  constructor(private http: HttpClient) {}

  // Récupérer tous les employés
  getEmployees(): Observable<Employee[]> {
    return this.http.get<Employee[]>(this.apiUrl).pipe(
      tap(res => console.log('Employees récupérés :', res))
    );
  }

  // Récupérer 1 employé par id
  getEmployeeById(id: string): Observable<Employee> {
    return this.http.get<Employee>(`${this.apiUrl}/${id}`);
  }
  getEmployeeByUserId(id: string): Observable<Employee> {
    return this.http.get<Employee>(`${this.apiUrl}/user/${id}`);
  }

  // Ajouter
  addEmployee(employee: Employee): Observable<Employee> {
    return this.http.post<Employee>(this.apiUrl, employee);
  }

  // Modifier
  updateEmployee(id: string, employee: Employee): Observable<Employee> {
    return this.http.put<Employee>(`${this.apiUrl}/${id}`, employee);
  }

  // Supprimer
  deleteEmployee(id: string): Observable<string> {
    return this.http.delete(`${this.apiUrl}/delete/${id}`, {
      responseType: 'text'
    });
  }

  // Compter
  getCount(): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/count`);
  }
  
}
