import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {FlexLayoutModule} from '@angular/flex-layout';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {AppComponent} from './components/app.component';
import {LoginComponent} from './components/login/login.component';
import {AuthService} from './services/auth.service';
import {LabelComponent} from './components/label/label.component';
import {FooterComponent} from './components/footer/footer.component';
import {SidebarComponent} from './components/sidebar/sidebar.component';
import {NavbarComponent} from './components/navbar/navbar.component';
import {RegisterComponent} from './components/register/register.component';
import {HeaderComponent} from './components/header/header.component';
import {SettingsComponent} from './components/settings/settings.component';
import {AdminUserListComponent} from './components/admin/admin-user-list.component';
import {Routing} from './routing';
import {CdkTableModule} from '@angular/cdk/table';
import {
    MatButtonModule,
    MatCardModule,
    MatChipsModule,
    MatInputModule,
    MatListModule,
    MatNativeDateModule,
    MatPaginatorModule,
    MatSidenavModule,
    MatSortModule,
    MatTableModule,
    MatTabsModule,
    MatToolbarModule,
    MatTooltipModule
} from '@angular/material';
import {AlertService} from './services/alert.service';
import {AlertContainerComponent} from './components/alert/alertContainer.component';
import {LogoutComponent} from './components/logout/logout.component';
import {IfAuthenticatedDirective} from './shared/if-authenticated.directive';
import {KeepWidth} from './shared/keep-width';
import {ApiModule} from '../generated/api.module';
import {Configuration} from '../generated/configuration';
import {AuthenticationInterceptor} from './shared/authentication-interceptor';
import {UserDataSource} from './models/user-data-source';
import {AdminUserDetailsComponent} from "./components/admin/admin-user-details.component";

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
        AdminUserListComponent,
        AdminUserDetailsComponent,
        AlertContainerComponent,
        LogoutComponent,
        IfAuthenticatedDirective,
        KeepWidth,
    ],
    imports: [
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
        ApiModule.forRoot(apiConfig),
    ],
    providers: [
        AuthService,
        AlertService,
        UserDataSource,
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthenticationInterceptor,
            multi: true,
        },
    ],
    bootstrap: [
        AppComponent,
    ]

})

export class AppModule {}

export function apiConfig() {
    return new Configuration({
        basePath: ' '
    });
}
