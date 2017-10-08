import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';

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
import {MenuComponent} from "./components/app/menu/menu.component";
import {ToolbarComponent} from "./components/app/toolbar/toolbar.component";
import {RegisterComponent} from "./components/app/register/register.component";
import {SettingsComponent} from "./components/app/settings/settings.component";
import {AdminTableUserComponent} from "./components/app/admin/admin-table-user.component";

import {Routing} from "./routing.module";
import {CdkTableModule} from "@angular/cdk/table";
import {AdminControllerService} from "../generated/api/adminController.service";
import {HttpClientModule} from "@angular/common/http";

@NgModule({
    declarations: [
        AppComponent,
        LoginComponent,
        RegisterComponent,
        LabelComponent,
        FooterComponent,
        MenuComponent,
        ToolbarComponent,
        SettingsComponent,
        AdminTableUserComponent,
    ],
    imports: [
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
        BrowserModule,
        BrowserAnimationsModule,
        FormsModule,
        ReactiveFormsModule,
        HttpModule,
        HttpClientModule,
        Routing,
    ],
    providers: [
        AuthService,
        AdminControllerService,
    ],
    bootstrap: [
        AppComponent,
    ]
})
export class AppModule { }
