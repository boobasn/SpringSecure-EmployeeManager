import { Routes } from '@angular/router';
import { Login } from './pages/login/login';
import { Dashboard } from './pages/dashboard/dashboard';
import { Layout } from './pages/layout/layout';
import { Logout } from './pages/logout/logout';
import { Profile } from './pages/profile/profile';

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
        children: 
            [
                {
                    path: 'dashboard',
                    component: Dashboard
                },
                {
                    path: 'profil',
                    component: Profile
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
