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

  // ===== PUBLIC =====
  { path: 'login', component: Login },

  { path: '', redirectTo: 'login', pathMatch: 'full' },

  // ===== PRIVE =====
  {
    path: '',
    component: Layout,
    canActivate: [AuthGuard],
    children: [

      {
        path: 'dashboard',
        component: Dashboard,
        data: { roles: ['ADMIN','FINANCE'] }
      },

      {
        path: 'profil',
        component: EmployeeProfileComponent,
        data: { roles: ['EMPLOYE'] }
      },


      {
        path: 'employees',
        component: EmployeeComponent,
        data: { roles: ['ADMIN','RH'] }
      },

      {
        path: 'payroll',
        component: PayrollComponent,
        data: { roles: ['FINANCE','ADMIN'] }
      },

      {
        path: 'logout',
        component: Logout
      },
    ]
  },

  // ===== FALLBACK =====
  { path: '**', redirectTo: 'login' }

];

