import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { EmployeeService } from '../../Services/employee';
import { Employee } from '../../model/employee';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-employee-profile',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './profile.html',
  styleUrl: './profile.css',
})
export class EmployeeProfileComponent implements OnInit {

  employee?: Employee;
  activeTab: string = 'personal';



  constructor(
    private route: ActivatedRoute,
    private employeeService: EmployeeService
  ) {}
  setTab(tab: string) {
    this.activeTab = tab;
  }
ngOnInit(): void {
  this.route.paramMap.subscribe(params => {

    let id = params.get('id');

    if (!id) {
      id = localStorage.getItem('userId');
    }

    if (id) {
      this.employeeService
        .getEmployeeByUserId(id)
        .subscribe({
          next: res => this.employee = res,
          error: err => console.error('Erreur chargement profil:', err)
        });
    }
  });
}


}
