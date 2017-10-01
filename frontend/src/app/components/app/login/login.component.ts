import {Component, OnInit} from '@angular/core';

@Component({
    templateUrl: 'login.component.html'
})

export class LoginComponent implements OnInit {

    model: any = {};

    loading = false;

    returnUrl: string;

    constructor(/*private route: ActivatedRoute,
                private router: Router,
                private authService: AuthService,
                private messageService: MessageService*/) {
        console.log("<constructor>");
    }

    ngOnInit() {
        // reset login status
        // this.authenticationService.logout();

        // get return url from route parameters or default to '/'
       // this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
    }

    login() {
        /*
        this.loading = true;
        this.messageService.add({severity:'info', summary:'Info Message', detail:'PrimeNG rocks'})
        this.authService.login(this.model.username, this.model.password)
            .subscribe(
                data => {
                    this.loading = false;
                    this.router.navigate([this.returnUrl]);
                },
                error => {
                    // this.alertService.error(error);
                    this.loading = false;
                });
                */
    }
}
