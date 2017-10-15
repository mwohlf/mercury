import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import {AuthService, Principal} from "../../services/auth.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Alert, AlertService} from "../../services/alert.service";

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

    loginForm: FormGroup;

    lastAlert: Alert = Alert.NULL;

    returnUrl: string;

    constructor(private route: ActivatedRoute,
                private router: Router,
                private authService: AuthService,
                private alertService: AlertService) {
    }

    ngOnInit() {
        this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
        this.createForm();
    }

    private createForm() {
        this.loginForm = new FormGroup({
            username: new FormControl('', Validators.required),
            password: new FormControl('', Validators.required)
        });
    }

    public login() {
        this.authService.login(
            this.loginForm.controls['username'].value,
            this.loginForm.controls['password'].value)
            .subscribe(
                success => {
                    this.onSuccess(success);
                },
                error => {
                    this.onError(error);
                });
    }

    private onError(error: any): void {
        this.lastAlert.dismiss();
        switch (error.status) {
            case 404:
                this.lastAlert = this.alertService.error("User not found");
                break;
            default:
                this.lastAlert = this.alertService.error(JSON.stringify(error));
        }
    }

    private onSuccess(principal: Principal): void {
        this.lastAlert.dismiss();
        this.lastAlert = this.alertService.success("Login for " + principal.userName);
        this.router.navigate([this.returnUrl]);
    }

}
