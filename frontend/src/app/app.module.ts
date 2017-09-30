import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { RouterModule } from '@angular/router';

import { AppComponent } from './components/app/app.component';
import { LoginComponent } from './components/app/login/login.component';
import { AuthService } from './services/auth.service';
import { LabelComponent } from "./components/app/label/label.component";

import {MessageService} from "primeng/components/common/messageservice";
import {MessagesModule} from 'primeng/primeng';
import {PasswordModule} from 'primeng/primeng';
import {InputTextModule} from 'primeng/primeng';

@NgModule({
    declarations: [
        AppComponent,
        LoginComponent,
        LabelComponent,
    ],
    imports: [
        BrowserModule,
        PasswordModule,
        InputTextModule,
        FormsModule,
        HttpModule,
        MessagesModule,
        RouterModule.forRoot([
                { path: "label", component: LabelComponent },
                { path: "login", component: LoginComponent },
            ],
            { enableTracing: false } // <-- debugging purposes only
        ),
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
