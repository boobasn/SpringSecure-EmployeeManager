import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { EmployeeService } from '../../Services/employee';
import { Employee } from '../../model/employee';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-employee',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './employee.html',
  styleUrl: './employee.css',
})
export class EmployeeComponent implements OnInit {

  employees: Employee[] = [];

  constructor(
    private employeeService: EmployeeService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.employeeService
      .getEmployees()
      .subscribe(res => this.employees = res);
  }

  openProfile(id?: string) {
    if (!id) return;

    this.router.navigate(['/employees', id]);
  }

}
