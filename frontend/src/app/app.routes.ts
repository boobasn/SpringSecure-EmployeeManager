import { Routes } from '@angular/router';
import { Login } from './pages/login/login';
import { Dashboard } from './pages/dashboard/dashboard';
import { Layout } from './pages/layout/layout';
import { Logout } from './pages/logout/logout';
import { EmployeeProfileComponent } from './pages/profile/profile';
import { AuthGuard } from './Services/auth.guard';
import { EmployeeComponent } from './pages/employee/employee';
import { PayrollComponent } from './pages/payroll/payroll';

export const routes: Routes = [

  // ========== PUBLIC ==========
  {
    path: 'login',
    component: Login
  },

  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  },

  // ========== PRIVATE ==========
  {
    path: '',
    component: Layout,
    canActivate: [AuthGuard],
    children: [

      {
        path: 'dashboard',
        component: Dashboard
      },

      {
        // ✅ profil connecté
        path: 'profil',
        component: EmployeeProfileComponent
      },

      {
        // ✅ PROFIL D’UN EMPLOYÉ PAR ID
        path: 'employees/:id',
        component: EmployeeProfileComponent
      },

      {
        // ✅ LISTE DES EMPLOYÉS
        path: 'employees',
        component: EmployeeComponent
      },

      {
        path: 'payroll',
        component: PayrollComponent
      },

      {
        // ✅ LOGOUT doit être ICI
        path: 'logout',
        component: Logout
      },

    ]
  },

  // ⚠️ Wildcard TOUJOURS DERNIER
  {
    path: '**',
    redirectTo: 'login'
  }

];
