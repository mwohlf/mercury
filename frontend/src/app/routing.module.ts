import {ModuleWithProviders} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {ErrorHandler} from "@angular/router/src/router";

import {LabelComponent} from "./components/label/label.component";
import {LoginComponent} from "./components/login/login.component";
import {RegisterComponent} from "./components/register/register.component";
import {SettingsComponent} from "./components/settings/settings.component";
import {AdminTableUserComponent} from "./components/admin/admin-table-user.component";



const APP_ROUTES: Routes = [
    { path: "label", component: LabelComponent },
    { path: "login", component: LoginComponent },
    { path: "register", component: RegisterComponent },
    { path: "settings", component: SettingsComponent },
    { path: "admin", component: AdminTableUserComponent },

    { path: '**', component: LabelComponent },
];

export const errorHandler: ErrorHandler = (error: any) => {
    console.error("routing failed");
    console.error(error);
};

export const Routing: ModuleWithProviders = RouterModule.forRoot(
    APP_ROUTES,
    {
        enableTracing: true   // <-- debugging purposes only
       // errorHandler: errorHandler
    }
);

