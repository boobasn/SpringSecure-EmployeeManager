import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../Services/auth';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile',
  imports: [CommonModule],
  templateUrl: './profile.html',
  styleUrl: './profile.css',
})
export class Profile implements OnInit {
  username = 'Utilisateur';

  constructor(private auth: AuthService, private router: Router) {}

  ngOnInit(): void {
    // If you later add user profile in AuthService, populate it here.
  }

  logout() {
    this.auth.logout();
    this.router.navigate(['/logout']);
  }
}
