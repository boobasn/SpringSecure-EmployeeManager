import { Component } from '@angular/core';
import { Navbar } from './navbar';
import { Slidebar } from './slidebar';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive, RouterOutlet, Navbar, Slidebar],
  templateUrl: './layout.html',
  styleUrl: './layout.css',

})
export class Layout {
  collapsed = false;

  toggleSidebar() {
    this.collapsed = !this.collapsed;
  }
}
