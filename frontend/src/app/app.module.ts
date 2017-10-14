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


import {AppComponent} from './components/app/app.component';
import {LoginComponent} from './components/app/login/login.component';
import {AuthService} from './services/auth.service';
import {LabelComponent} from "./components/app/label/label.component";
import {FooterComponent} from "./components/app/footer/footer.component";
import {SidebarComponent} from "./components/app/sidebar/sidebar.component";
import {NavbarComponent} from "./components/app/navbar/navbar.component";
import {RegisterComponent} from "./components/app/register/register.component";
import {SettingsComponent} from "./components/app/settings/settings.component";
import {AdminTableUserComponent} from "./components/app/admin/admin-table-user.component";

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
