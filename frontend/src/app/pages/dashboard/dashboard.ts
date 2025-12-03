import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DataService } from '../../Services/data.service';
import { EmployeeService } from '../../Services/employee';
@Component({
  selector: 'app-dashboard',
  imports: [CommonModule],
  templateUrl: './dashboard.html',
  standalone: true,
  styleUrl: './dashboard.css',
})
export class Dashboard implements OnInit {
  employees = 0;
  contracts = 0;
  leaves = 0;

  constructor(private data: DataService, private employeeService: EmployeeService) {}


ngOnInit(): void {
  this.employeeService.getCount().subscribe({
    next: (count: number) => {
      console.log('📌 Employees received:', count);
      this.employees = count;
    },
    error: (err) => {
      console.error('❌ Error fetching employees:', err);
    }
  });

  this.data.getContracts().subscribe({
    next: (c: any[]) => {
      console.log('📌 Contracts received:', c);
      this.contracts = c?.length ?? 0;
    },
    error: (err) => {
      console.error('❌ Error fetching contracts:', err);
    }
  });

  this.data.getLeaves().subscribe({
    next: (l: any[]) => {
      console.log('📌 Leaves received:', l);
      this.leaves = l?.length ?? 0;
    },
    error: (err) => {
      console.error('❌ Error fetching leaves:', err);
    }
  });
}

}
