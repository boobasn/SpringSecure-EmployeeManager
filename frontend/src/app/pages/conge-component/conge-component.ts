import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CongeService } from '../../Services/conge/conge';
import { Leave, TypeConge, Status } from '../../model/leave';
import { AuthService } from '../../Services/auth';  
import { ActivatedRoute } from '@angular/router';
@Component({
  selector: 'app-conge-component',

  imports: [CommonModule, FormsModule],
  templateUrl: './conge-component.html',
  styleUrls: ['./conge-component.css'],
})
export class CongeComponent implements OnInit {
  leaves: Leave[] = [];
  filtered: Leave[] = [];

  filterStatus: Status | 'ALL' = 'ALL';

  constructor(
    private service: CongeService,
    public auth: AuthService
  ) {}

  ngOnInit(): void {
    this.loadAll();
  }

  loadAll() {
    this.service.getAll().subscribe(list => {
      this.leaves = list.sort((a, b) =>
        new Date(b.startDate).getTime() -
        new Date(a.startDate).getTime()
      );
      this.applyFilters();
    });
  }

  applyFilters() {
    if (this.filterStatus === 'ALL') {
      this.filtered = [...this.leaves];
    } else {
      this.filtered = this.leaves.filter(l => l.status === this.filterStatus);
    }
  }

  approve(leave: Leave) {
     if (!confirm("Confirmer l'approbation ?")) return;
    this.service.approve(leave.id as string).subscribe({
      next: () => this.loadAll(),
      error: e => console.error(e)
    });
  }

  reject(leave: Leave) {
    if (!confirm("Confirmer le refus ?")) return;
    if (!confirm("Confirmer le refus ?")) return;

    const commentaire = prompt('Commentaire obligatoire pour le refus :');

    if (!commentaire || commentaire.trim().length === 0) {
      alert('Le commentaire est obligatoire.');
      return;
    }

    this.service.reject(
      leave.id as string,
      commentaire.trim()
    ).subscribe({
      next: () => this.loadAll(),
      error: e => console.error(e)
    });
  }

}
