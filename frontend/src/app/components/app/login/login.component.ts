import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import {AuthService} from "../../../services/auth.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

    loginForm: FormGroup;

    returnUrl: string;

    constructor(private route: ActivatedRoute,
                private router: Router,
                private authService: AuthService) {
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
                data => {
                    console.info(data);
                    // this.messageService.add({severity:'info', summary:'Info Message', detail:'login validated'})
                    this.router.navigate([this.returnUrl]);
                },
                error => {
                    console.info(error);
                    // this.messageService.add({severity:'error', summary:'Info Message', detail:'login invalid'})
                });
    }

}