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
    if (typeof window !== 'undefined' && sessionStorage.getItem('auth_token')) {
        this.isUserLoggedInSubject.next(true);
    }
  }

  login(userName: string, password: string): Observable<{ success: boolean; message?: string }> {
    return this.http.post<{ token?: string; message?: string }>(`${API_BASE}/auth/login`, { username: userName, password })
      .pipe(
        map(res => {
          const success = !!res?.token;
          if (success) {
            console.log("TOKEN RECU:", res.token);
            sessionStorage.setItem('auth_token', res.token!);
            localStorage.setItem('token', res.token!); // ⭐ stockage ici
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
    sessionStorage.removeItem('auth_token');
    this.isUserLoggedInSubject.next(false);
  }

  get isUserLoggedIn$(): Observable<boolean> {
    return this.isUserLoggedInSubject.asObservable();
  }

isAuthenticated(): boolean {
  if (typeof window === 'undefined') return false;

  const token =localStorage.getItem('token'); // ⭐ recommandé
  console.log('AuthService - isAuthenticated - token:', token);
  return !!token;
}
}
