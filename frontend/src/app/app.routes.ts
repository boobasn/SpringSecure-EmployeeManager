import { Routes } from '@angular/router';
import { Login } from './pages/login/login';
import { Dashboard } from './pages/dashboard/dashboard';
import { Layout } from './pages/layout/layout';
import { Logout } from './pages/logout/logout';
import { Profile } from './pages/profile/profile';
import { AuthGuard } from './Services/auth.guard';
import {Employee } from './pages/employee/employee';
export const routes: Routes = [
    {
        path: 'login',
        component: Login
    },

    {
        path: '',
        redirectTo: 'login',
        pathMatch: 'full'
    },

    {
        path: '',
        component: Layout,
        canActivate: [AuthGuard],
        children: 
            [
                {
                    path: 'dashboard',
                    component: Dashboard
                },
                {
                    path: 'profil',
                    component: Profile
                },
                {
                    path: 'employees',
                    component: Employee
                }
            ]
    },
    {
        path: '**',
        redirectTo: 'login'
    },
    {
        path: 'logout',
        component: Logout
    }
];
