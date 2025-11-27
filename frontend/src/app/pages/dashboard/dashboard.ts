import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DataService } from '../../Services/data.service';
import { EmployeeService } from '../../Services/employee';
@Component({
  selector: 'app-dashboard',
  imports: [CommonModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard implements OnInit {
  employees = 0;
  contracts = 0;
  leaves = 0;

  constructor(private data: DataService, private employeeService: EmployeeService) {}


  ngOnInit(): void {
    this.employeeService.getEmployees().subscribe((e: any[]) => (this.employees = e?.length ?? 0));
    this.data.getContracts().subscribe((c: any[]) => (this.contracts = c?.length ?? 0));
    this.data.getLeaves().subscribe((l: any[]) => (this.leaves = l?.length ?? 0));
  }

}
