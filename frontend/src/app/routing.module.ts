import {ModuleWithProviders} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {LabelComponent} from "./components/app/label/label.component";
import {LoginComponent} from "./components/app/login/login.component";
import {RegisterComponent} from "./components/app/register/register.component";


const APP_ROUTES: Routes = [
    { path: "label", component: LabelComponent },
    { path: "login", component: LoginComponent },
    { path: "register", component: RegisterComponent },
];

export const Routing: ModuleWithProviders = RouterModule.forRoot(
    APP_ROUTES,
    { enableTracing: false } // <-- debugging purposes only
);