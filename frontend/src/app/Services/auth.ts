import { Injectable } from '@angular/core';
import { Observable, of, BehaviorSubject } from 'rxjs';
import { map, catchError, tap } from 'rxjs/operators';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { API_BASE } from '../config';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
   // Track login state with BehaviorSubject
   private isUserLoggedInSubject = new BehaviorSubject<boolean>(false);  

  constructor(private http?: HttpClient) {
     // Only initialize sessionStorage on the client-side (browser)
     if (typeof window !== 'undefined' && window.sessionStorage) {
       const storedLoginState = sessionStorage.getItem('isUserLoggedIn') === 'true';
       this.isUserLoggedInSubject.next(storedLoginState);
     }
   }

  login(userName: string, password: string): Observable<{ success: boolean; message?: string }> {
      // If HttpClient is available, call backend /auth/login, otherwise fallback to mock
      if (this.http) {
        const url = `${API_BASE}/auth/login`;
        return this.http.post<{ token?: string; message?: string }>(url, { username: userName, password }).pipe(
          map((res) => {
            const success = !!res?.token;
            if (success && typeof window !== 'undefined' && window.sessionStorage) {
              sessionStorage.setItem('isUserLoggedIn', 'true');
              sessionStorage.setItem('auth_token', res.token as string);
            }
            this.isUserLoggedInSubject.next(success);
            console.log('Login response token present:', success);
            return { success, message: success ? undefined : (res?.message ?? 'Login failed') };
          }),
          catchError((err) => {
            // Try to return structured error message if backend provides one
            const body = err?.error;
            const message = body?.message || (err?.statusText ? `${err.status} ${err.statusText}` : 'Unknown error');
            this.isUserLoggedInSubject.next(false);
            return of({ success: false, message });
          })
      );
      }

      // fallback (no HttpClient available — keep original behavior for tests/SSR)
      const isLoggedIn = userName === 'admin' && password === 'admin';
      if (typeof window !== 'undefined' && window.sessionStorage) {
        sessionStorage.setItem('isUserLoggedIn', isLoggedIn ? 'true' : 'false');
      }
      this.isUserLoggedInSubject.next(isLoggedIn);
        const message = isLoggedIn ? undefined : 'Invalid username/password';
        return of({ success: isLoggedIn, message }).pipe(
          tap(val => console.log('Is User Authentication successful (fallback): ' + val.success))
      );
   }

   logout(): void {
     if (typeof window !== 'undefined' && window.sessionStorage) {
       sessionStorage.removeItem('isUserLoggedIn');
       sessionStorage.removeItem('auth_token');
      }
      // Update the BehaviorSubject to false when logged out
      this.isUserLoggedInSubject.next(false);  
   }

   // Expose the login status as an observable
   get isUserLoggedIn$(): Observable<boolean> {
     return this.isUserLoggedInSubject.asObservable();
   }
}