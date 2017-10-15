import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';
import {FlexLayoutModule} from '@angular/flex-layout';

// Angular Material

import {BrowserAnimationsModule} from '@angular/platform-browser/animations';


import {AppComponent} from './components/app.component';
import {LoginComponent} from './components/login/login.component';
import {AuthService} from './services/auth.service';
import {LabelComponent} from "./components/label/label.component";
import {FooterComponent} from "./components/footer/footer.component";
import {SidebarComponent} from "./components/sidebar/sidebar.component";
import {NavbarComponent} from "./components/navbar/navbar.component";
import {RegisterComponent} from "./components/register/register.component";
import {HeaderComponent} from "./components/header/header.component";
import {SettingsComponent} from "./components/settings/settings.component";
import {AdminTableUserComponent} from "./components/admin/admin-table-user.component";

import {Routing} from "./routing.module";
import {UserControllerService} from "../generated/api/userController.service";
import {HttpClientModule} from "@angular/common/http";

import {CdkTableModule} from "@angular/cdk/table";
import {
    MatButtonModule, MatCardModule, MatChipsModule, MatInputModule, MatListModule, MatNativeDateModule,
    MatPaginatorModule, MatSidenavModule, MatSortModule, MatTableModule, MatTabsModule, MatToolbarModule,
    MatTooltipModule
} from "@angular/material";
import {BASE_PATH} from "../generated/variables";
import {SecurityControllerService} from "../generated/api/securityController.service";
import {AlertService} from "./services/alert.service";
import {AlertComponent} from "./components/alert/alert.component";

@NgModule({
    declarations: [
        AppComponent,
        LoginComponent,
        RegisterComponent,
        LabelComponent,
        FooterComponent,
        HeaderComponent,
        SidebarComponent,
        NavbarComponent,
        SettingsComponent,
        AdminTableUserComponent,
        AlertComponent,
    ],
    imports: [
        HttpModule,
        HttpClientModule,
        Routing,
        BrowserModule,
        BrowserAnimationsModule,
        FormsModule,
        ReactiveFormsModule,
        FlexLayoutModule,

        MatSidenavModule,
        MatButtonModule,
        MatChipsModule,
        MatListModule,
        MatTableModule,
        CdkTableModule,
        MatNativeDateModule,
        MatInputModule,
        MatPaginatorModule,
        MatSortModule,
        MatTabsModule,
        MatToolbarModule,
        MatTooltipModule,
        MatCardModule,
    ],
    providers: [
        AuthService,
        AlertService,
        UserControllerService,
        SecurityControllerService,
        { provide: BASE_PATH, useValue: ' ' } // use current url as basePath in all API controlers
    ],
    bootstrap: [
        AppComponent,
    ]
})
export class AppModule { }
