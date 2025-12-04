import { Component, Output, EventEmitter } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { AuthService } from '../../Services/auth';
import { CommonModule } from '@angular/common'; // ✅ OBLIGATOIRE
@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [RouterLink, RouterLinkActive, RouterOutlet,CommonModule],
  templateUrl: './slidebar.html',
  styleUrl: './slidebar.css',
})
export class Slidebar {
  constructor(public auth: AuthService) {}

  isOpen = false;

  toggle(){
    this.isOpen = !this.isOpen;
  }

}
