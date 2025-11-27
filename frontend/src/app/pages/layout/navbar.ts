import { Component, Output, EventEmitter } from '@angular/core';
import { AuthService } from '../../Services/auth';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
})
export class Navbar {
  @Output() toggleSidebar = new EventEmitter<void>();

  constructor(private auth: AuthService, private router: Router) {}

  logout(): void {
    try {
      this.auth.logout();
    } catch (e) {
      console.warn('Logout (navbar) failed:', e);
    }
    this.router.navigate(['/logout']);
  }
}
