import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService, Principal} from "../../services/auth.service";
import {Subscription} from "rxjs/Subscription";

@Component({
    selector: 'mrc-footer',
    templateUrl: './footer.component.html',
    styleUrls: ['./footer.component.scss']

})
export class FooterComponent implements OnDestroy {

    private subscription: Subscription;
    private userId: number = 0;
    private userName: string = '';

    constructor(private authService: AuthService) {
        this.subscription = authService.getPrincipal().subscribe(
            principal => {
                console.log("principal");
                console.log(principal);
                console.log("principal is NULL: " + (Principal.NULL == principal));
                if (principal) {
                    this.userId = principal.userId;
                    this.userName = principal.userName;
                } else {
                    this.userId = 0;
                    this.userName = '';
                }
            }
        );
    }

    ngOnDestroy(): void {
        this.subscription.unsubscribe();
    }

}
