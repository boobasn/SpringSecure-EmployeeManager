import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { EmployeeService } from '../../Services/employee';
import { Employee } from '../../model/employee';

import { Poste } from '../../model/poste';
import { Department } from '../../model/departement';
import { PosteService } from '../../Services/poste-service';
import { DepartementService } from '../../Services/departement-service'


import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-employee',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './employee.html',
  styleUrl: './employee.css',
})
export class EmployeeComponent implements OnInit {

  /** LIST */
  employees$!: Observable<Employee[]>;
  search = '';
  postes: Poste[] = [];
  departements: Department[] = [];
  /** MODAL */
  showModal = false;
  step = 1;

  /** DETAILS / EDIT */
  selectedEmployee: Employee | null = null;

  isEditMode = false;
  editEmployeeData!: Employee;

  /** ADD MODEL */
  newEmployee: any = {
    firstName: '',
    lastName: '',
    email: '',
    telephone: '',
    adresse: '',
    sexe: '',
    dateNaissance: '',
    dateEmbauche: '',
    salaire: 0,
    matricule: '',
    poste: { id: null },
    departement: { id: null }
  };

  constructor(
    private employeeService: EmployeeService,
    private posteService: PosteService,
    private departementService: DepartementService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadEmployees();
    this.loadPostes();
    this.loadDepartements();
  }

  loadEmployees(){
    this.employees$ = this.employeeService.getEmployees();
  }
  loadPostes(){
  this.posteService.getPostes().subscribe(res => this.postes = res);
 }
 loadDepartements(){
  this.departementService.getDepartements().subscribe(res => this.departements = res);
}

  /* ================= ADD MODAL ================= */

  openAddModal(){
    this.isEditMode = false;
    this.step = 1;
    this.showModal = true;

    this.newEmployee = {
      firstName: '',
      lastName: '',
      email: '',
      telephone: '',
      matricule: '',
      adresse: '',
      sexe: '',
      dateNaissance: '',
      dateEmbauche: '',
      salaire: 0,
      poste: { id: null },
      departement: { id: null }
    };
  }

  closeModal(){
    this.showModal = false;
    this.step = 1;
  }

  /* ✅ 2 steps only */
  nextStep(){
    if(this.step < 2) this.step++;
  }

  prevStep(){
    if(this.step > 1) this.step--;
  }

  saveEmployee(){
    this.employeeService.addEmployee(this.newEmployee).subscribe({
      next: () => {
        this.loadEmployees();
        this.closeModal();
      },
      error: err => {
        console.error(err);
        alert("Erreur lors de l'ajout");
      }
    });
  }

  /* ================= DETAILS ================= */

  openProfile(emp: Employee){
    this.selectedEmployee = emp;
    this.isEditMode = false;
  }

  closeDetails(){
    this.selectedEmployee = null;
    this.isEditMode = false;
  }

  /* ================= EDIT ================= */
  selectedPosteId: any = null;
  selectedDepartementId: any = null;
  openEditModal(emp: Employee) {

    this.openProfile(emp); 
    this.isEditMode = true;

    /** ✅ CLONE SANS USER */
    this.editEmployeeData = { ...emp ,

         // ✅ Sécurisation
        poste: emp.poste ?? { id: '' } as any,
        departement: emp.departement ?? { id: '' } as any,
     };

  this.selectedPosteId = this.editEmployeeData.poste?.id ?? null;
  this.selectedDepartementId = this.editEmployeeData.departement?.id ?? null;
  }
isSaving = false;


saveUpdate(){

  if(this.isSaving) return;

  if(!this.editEmployeeData?.id){
    return;
  }

  this.isSaving = true;

  this.employeeService
    .updateEmployee(this.editEmployeeData.id, this.editEmployeeData)
    .subscribe({

      next: () => {
        this.isSaving = false;
        this.isEditMode = false;
        this.loadEmployees();
        this.closeDetails();
      },

      error: err => {
        this.isSaving = false;
        console.error("Erreur update:", err);
        alert("Erreur lors de la modification");
      }

    });
}


  cancelEdit(){
    this.isEditMode = false;
  }


  /* ================= DELETE ================= */

  deleteEmployee(emp: Employee){

    if(!confirm(`Supprimer ${emp.firstName} ${emp.lastName} ?`))
      return;

    this.employeeService.deleteEmployee(emp.id!).subscribe({

      next: () => {
        this.loadEmployees();

        if(this.selectedEmployee?.id === emp.id){
          this.closeDetails();
        }
      },

      error: err => {
        console.error(err);
        alert("Erreur suppression");
      }

    });

  }
}
