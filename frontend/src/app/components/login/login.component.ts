import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../services/auth.service';
import {ActivatedRoute, Router} from '@angular/router';
import {AlertService} from '../../services/alert.service';
import {AuthControllerService} from "../../../generated/api/authController.service";
import {AuthProviderInfo} from "../../../generated/model/authProviderInfo";

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

    loginForm: FormGroup;

    returnUrl: string;

    providerNames: string[];


    constructor(private route: ActivatedRoute,
                private router: Router,
                private authService: AuthService,
                private alertService: AlertService,
                private oauthService: AuthControllerService) {
    }

    ngOnInit() {
        this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
        // this.OAuthProviders = authService.get
        this.createForm();
        this.oauthService.providersUsingGET().subscribe((result: Array<AuthProviderInfo>) => {
            this.providerNames = new Array<string>();
            result.forEach( (info: AuthProviderInfo) => {
               this.providerNames.push(info.name);
            });
        })
    }

    private createForm() {
        this.loginForm = new FormGroup({
            username: new FormControl('', Validators.required),
            password: new FormControl('', Validators.required)
        });
    }

    public login() {
        console.log("<login> in login.component");
        this.authService.login(
            this.loginForm.controls['username'].value,
            this.loginForm.controls['password'].value)
            .subscribe(
                principal => {
                    this.alertService.success("Login for " + principal.username).timeout(5).show();
                    this.navigate(this.returnUrl);
                },
                error => {
                    this.alertService.handleError(error.error);
                });
    }

    public navigate(url: string) {
        this.router.navigate([url]);
    }

}
