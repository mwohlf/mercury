import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {ActivatedRoute, Router} from '@angular/router';
import {AlertService} from '../../services/alert.service';

@Component({
    templateUrl: './logout.component.html',
})
export class LogoutComponent implements OnInit {

    constructor(private route: ActivatedRoute,
                private router: Router,
                private authService: AuthService,
                private alertService: AlertService) {
    }

    ngOnInit() {
        this.authService.logout();
        this.alertService.clear();
        this.alertService.success("usr logged out")
    }

}
