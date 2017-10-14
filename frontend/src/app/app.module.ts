import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';
import {FlexLayoutModule} from '@angular/flex-layout';

// Angular Material
import {
    MdCardModule, MdInputModule, MdSidenavModule, MdButtonModule, MdChipsModule, MdListModule, MdTooltipModule, MdToolbarModule, MdTabsModule, MdSortModule,
    MdPaginatorModule, MdNativeDateModule, MdTableModule
} from '@angular/material';
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
import {CdkTableModule} from "@angular/cdk/table";
import {UserControllerService} from "../generated/api/userController.service";
import {HttpClientModule} from "@angular/common/http";

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

        MdSidenavModule,
        MdButtonModule,
        MdChipsModule,
        MdListModule,
        MdTableModule,
        CdkTableModule,
        MdNativeDateModule,
        MdInputModule,
        MdPaginatorModule,
        MdSortModule,
        MdTabsModule,
        MdToolbarModule,
        MdTooltipModule,
        MdCardModule,

    ],
    providers: [
        AuthService,
        UserControllerService,
    ],
    bootstrap: [
        AppComponent,
    ]
})
export class AppModule { }
