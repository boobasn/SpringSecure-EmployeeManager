import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { API_BASE } from '../config';

export type Employee = { id: number | string; departmentId?: number | string; firstName?: string; lastName?: string; name?: string; status?: string };
export type Department = { id: number | string; name: string; manager?: string };
export type Contract = { id: number | string; employeeId: number | string; type?: string; startDate?: string; endDate?: string | null; status?: string };
export type LeaveRequest = { id: number | string; employeeId: number | string; type?: string; startDate?: string; endDate?: string; status?: string };

@Injectable({ providedIn: 'root' })
export class DataService {
  constructor(private http?: HttpClient) {}

  // Each method attempts to call the backend when HttpClient is available. If not
  // available (SSR fallback or tests), returns a safe empty array.

  getEmployees(): Observable<Employee[]> {
    if (!this.http) return of([]);
    return this.http.get<Employee[]>(`${API_BASE}/employees`).pipe(catchError(() => of([])));
  }

  getDepartments(): Observable<Department[]> {
    if (!this.http) return of([]);
    return this.http.get<Department[]>(`${API_BASE}/departments`).pipe(catchError(() => of([])));
  }

  getContracts(): Observable<Contract[]> {
    if (!this.http) return of([]);
    return this.http.get<Contract[]>(`${API_BASE}/contrat`).pipe(catchError(() => of([])));
  }

  getLeaves(): Observable<LeaveRequest[]> {
    if (!this.http) return of([]);
    return this.http.get<LeaveRequest[]>(`${API_BASE}/leaves`).pipe(catchError(() => of([])));
  }

}
