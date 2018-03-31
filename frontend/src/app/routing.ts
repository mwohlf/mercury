import {ModuleWithProviders} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ErrorHandler} from '@angular/router/src/router';

import {LabelComponent} from './components/label/label.component';
import {LoginComponent} from './components/login/login.component';
import {RegisterComponent} from './components/register/register.component';
import {SettingsComponent} from './components/settings/settings.component';
import {AdminUserListComponent} from './components/admin/admin-user-list.component';
import {AdminUserDetailsComponent} from './components/admin/admin-user-details.component';
import {LogoutComponent} from './components/logout/logout.component';


const APP_ROUTES: Routes = [
    { path: "label", component: LabelComponent },
    { path: "login", component: LoginComponent },
    { path: "register", component: RegisterComponent },
    { path: "settings", component: SettingsComponent },
    { path: "admin/users", component: AdminUserListComponent },
    { path: "admin/users/:uid", component: AdminUserDetailsComponent },
    { path: "logout", component: LogoutComponent },

    { path: '**', component: LabelComponent },
];

export const errorHandler: ErrorHandler = (error: any) => {
    console.error("routing failed, error");
    console.error(error);
};

export const Routing: ModuleWithProviders = RouterModule.forRoot(
    APP_ROUTES,
    {
        enableTracing: false,   // <-- debugging purposes only
        //  errorHandler: errorHandler
    }
);

