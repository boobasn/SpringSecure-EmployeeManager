import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { EmployeeService } from '../../Services/employee';
import { Employee } from '../../model/employee';
import { User } from '../../model/user';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-employee',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './employee.html',
  styleUrl: './employee.css',
})
export class EmployeeComponent implements OnInit {

  employees: Employee[] = [];
  user: User[] = [];
  search = '';
  showModal = false;

  newEmployee: any = {
          firstName:'',
          lastName:'',
          email:'',
          telephone:'',
          adresse:'',
          sexe:'',
          dateNaissance:'',
          dateEmbauche:'',
          salaire: 0,

          user: {
            username:'',
            password:'',
            role:'EMPLOYE'
          }
  };


  constructor(
    private employeeService: EmployeeService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.employeeService
      .getEmployees()
      .subscribe(res => this.employees = res);
  }



  openAddModal(){
    this.showModal = true;
  }

  closeModal(){
    this.showModal = false;
  }
  step = 1;

/* navigation */
nextStep() {
  if (this.step < 3) {
    this.step++;
  }
}

prevStep() {
  if (this.step > 1) {
    this.step--;
  }
}


  saveEmployee(){

    this.employeeService.addEmployee(this.newEmployee)
      .subscribe({
        next: (createdEmployee) => {

          this.employees.unshift(createdEmployee);

          this.newEmployee = {
            firstName:'',
            lastName:'',
            email:'',
            telephone:'',
            adresse:'',
            sexe:'',
            dateNaissance:'',
            dateEmbauche:'',
            salaire: 0,

            user: {
              username:'',
              password:'',
              role:'EMPLOYE'
            }

          };

          this.closeModal();
        },

        error: (err) => {
          console.error('Erreur création employé:', err);
          alert("Erreur lors de l'ajout de l'employé");
        }
      });
  }

  /* =======================
   DETAIL ACTIONS
=======================*/

selectedEmployee: Employee | null = null;
showDetails = false;

/** Ouvrir le panneau détails */
openProfile(emp: Employee) {
  this.selectedEmployee = emp;
  this.showDetails = true;
}

/** Fermer panneau */
closeDetails() {
  this.showDetails = false;
  this.selectedEmployee = null;
}

/** Bouton Modifier */
editEmployee() {
  if (!this.selectedEmployee) return;

  console.log('EDIT:', this.selectedEmployee);
  alert(`Modifier : ${this.selectedEmployee.firstName} ${this.selectedEmployee.lastName}`);
}

/** Bouton Supprimer */
deleteEmployee() {
  if (!this.selectedEmployee) return;

  const confirmDelete = confirm(
    `Supprimer ${this.selectedEmployee.firstName} ${this.selectedEmployee.lastName} ?`
  );

  if (!confirmDelete) return;

  this.employeeService
    .deleteEmployee(this.selectedEmployee.id!)
    .subscribe({
      next: () => {
        // remove from list
        this.employees = this.employees.filter(
          e => e.id !== this.selectedEmployee?.id
        );

        this.closeDetails();
      },
      error: err => {
        console.error('Erreur suppression', err);
        alert("Erreur lors de la suppression");
      }
    });
}

}