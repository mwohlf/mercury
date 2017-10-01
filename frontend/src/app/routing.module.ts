import {ModuleWithProviders} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {LabelComponent} from "./components/app/label/label.component";
import {LoginComponent} from "./components/app/login/login.component";
import {RegisterComponent} from "./components/app/register/register.component";
import {SettingsComponent} from "./components/app/settings/settings.component";
import {ErrorHandler} from "@angular/router/src/router";


const APP_ROUTES: Routes = [
    { path: "label", component: LabelComponent },
    { path: "login", component: LoginComponent },
    { path: "register", component: RegisterComponent },
    { path: "settings", component: SettingsComponent },

    { path: '**', component: LabelComponent },
];

export const errorHandler: ErrorHandler = (error: any) => {
    console.error("routing failed");
    console.error(error);
};

export const Routing: ModuleWithProviders = RouterModule.forRoot(
    APP_ROUTES,
    {
        enableTracing: false   // <-- debugging purposes only
       // errorHandler: errorHandler
    }
);

