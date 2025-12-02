import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from './auth';


@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) {}

  canActivate(): boolean {

    // Evite l'erreur SSR ------------
    if (typeof window === 'undefined') {
      return false;
    }

    // Vérifie le token --------------
    if (this.authService.isAuthenticated()) {
      return true;
    }

    // Si pas connecté → redirection
    this.router.navigate(['/login']);
    return false;
  }
}
