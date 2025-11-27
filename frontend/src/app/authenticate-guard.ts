import { CanActivateFn } from '@angular/router';
import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router, UrlTree } from '@angular/router';
import { AuthService } from './Services/auth';

@Injectable({
   providedIn: 'root'
})
export class AuthenticateGuard implements CanActivate {
   constructor(private authService: AuthService, private router: Router) {}

   canActivate(
      next: ActivatedRouteSnapshot,
      state: RouterStateSnapshot): boolean | UrlTree {
      let url: string = state.url;
      return this.checkLogin(url);
   }

   checkLogin(url: string): true | UrlTree {
      console.log("Url: " + url);

         // Check if sessionStorage is available (only in the browser)
         if (typeof window !== 'undefined' && window.sessionStorage) {
            // Prefer to check a stored auth token (backend-issued JWT)
            const token = sessionStorage.getItem('auth_token');

            // If token exists consider the user authenticated
            if (token && token.length > 0) {
           // If the user is already logged in and trying to access the login page, redirect to /expenses
           if (url === "/login") {
              return this.router.parseUrl('/dashboard');
           } else {
              return true; // User is allowed to proceed
           }
        } else {
           // If the user is not logged in, redirect to /login
           return this.router.parseUrl('/login');
        }
      }

      // In case sessionStorage isn't available (for SSR)
      return this.router.parseUrl('/login');
   }
}