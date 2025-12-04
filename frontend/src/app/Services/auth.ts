import { Injectable } from '@angular/core';
import { Observable, BehaviorSubject, of } from 'rxjs';
import { map, catchError, tap } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { API_BASE } from '../config';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private isUserLoggedInSubject = new BehaviorSubject<boolean>(false);

  constructor(private http: HttpClient) {
    if (typeof window !== 'undefined' && localStorage.getItem('token')) {
      this.isUserLoggedInSubject.next(true);
    }
  }

  login(userName: string, password: string): Observable<{ success: boolean; message?: string }> {
    return this.http.post<{ token?: string; message?: string; role?: string; userId?: string }>(
      `${API_BASE}/auth/login`,
      { username: userName, password }
    ).pipe(
      map(res => {

        const success = !!res?.token;

        if (success) {
          console.log('TOKEN REÇU:', res.token);
          console.log('ROLE:', res.role);
          console.log('USER_ID:', res.userId);

          localStorage.setItem('token', res.token!);
          localStorage.setItem('role',  res.role!);
          localStorage.setItem('userId', res.userId!);

          this.isUserLoggedInSubject.next(true);
        }

        return { success, message: success ? undefined : res?.message || 'Login failed' };

      }),
      catchError(err => {
        this.isUserLoggedInSubject.next(false);
        return of({ success: false, message: err.error?.message ?? 'Erreur de connexion' });
      })
    );
  }

  logout(): void {
    localStorage.clear();
    this.isUserLoggedInSubject.next(false);
  }

  get isUserLoggedIn$(): Observable<boolean> {
    return this.isUserLoggedInSubject.asObservable();
  }

  isAuthenticated(): boolean {
    const token = localStorage.getItem('token');
    console.log('AuthService - token:', token);
    return !!token;
  }

  // ✅ GETTERS PRO

  getRole(): string {
    return localStorage.getItem('role') || '';
  }

  getUserId(): string {
    return localStorage.getItem('userId') || '';
  }

  isAdmin(): boolean {
    return this.getRole() === 'ADMIN' || this.getRole() === 'RH';
  }

  isFinance(): boolean {
    return this.getRole() === 'FINANCE';
  }

  isEmploye(): boolean {
    return this.getRole() === 'EMPLOYE';
  }
  hasRole(...roles: string[]): boolean {
    const currentRole = this.getRole();
    return roles.includes(currentRole);
  }

}
