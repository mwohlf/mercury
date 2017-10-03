import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent } from './components/app/app.component';
import { LoginComponent } from './components/app/login/login.component';
import { AuthService } from './services/auth.service';
import { LabelComponent } from "./components/app/label/label.component";
import { FooterComponent } from "./components/app/footer/footer.component";
import { MenuComponent } from "./components/app/menu/menu.component";
import { ToolbarComponent } from "./components/app/toolbar/toolbar.component";
import {RegisterComponent} from "./components/app/register/register.component";
import {SettingsComponent} from "./components/app/settings/settings.component";
import {AdminComponent} from "./components/app/admin/admin.component";

import {MessageService} from "primeng/components/common/messageservice";
import {MessagesModule} from 'primeng/primeng';
import {PasswordModule} from 'primeng/primeng';
import {InputTextModule} from 'primeng/primeng';
import {Routing} from "./routing.module";

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
        AdminComponent,
    ],
    imports: [
        BrowserModule,
        PasswordModule,
        InputTextModule,
        FormsModule,
        HttpModule,
        MessagesModule,
        Routing,
    ],
    providers: [
        AuthService,
        MessageService,
    ],
    bootstrap: [
        AppComponent,
    ]
})
export class AppModule { }
