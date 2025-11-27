import { Component, Output, EventEmitter } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [RouterLink, RouterLinkActive, RouterOutlet],
  templateUrl: './slidebar.html',
  styleUrl: './slidebar.css',
})
export class Slidebar {
  @Output() toggle = new EventEmitter<void>();

  isCollapsed: boolean = false;

  onToggle(): void {
    this.toggle.emit();
    this.isCollapsed = !this.isCollapsed;
  }

}
