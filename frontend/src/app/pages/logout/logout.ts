import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../Services/auth';
import { Router } from '@angular/router';

@Component({
  selector: 'app-logout',
  imports: [],
  templateUrl: './logout.html',
  styleUrl: './logout.css',
})
export class Logout implements OnInit {
   constructor(private authService : AuthService, private router: Router) { }

  ngOnInit() {
     this.authService.logout();
     this.router.navigate(['/']);
  }
}
