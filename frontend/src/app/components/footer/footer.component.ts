import {Component, OnDestroy} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {Subscription} from 'rxjs/Subscription';

@Component({
    selector: 'mrc-footer',
    templateUrl: './footer.component.html',
    styleUrls: ['./footer.component.scss']
})
export class FooterComponent implements OnDestroy {

    private subscription: Subscription;
    private userId: number | undefined;
    private username: string | undefined;

    constructor(private authService: AuthService) {
        this.subscription = authService.getPrincipal().subscribe(
            principal => {
                if (principal !== AuthService.NULL_PRINCIPAL) {
                    this.userId = principal.userId;
                    this.username = principal.username;
                } else {
                    this.userId = undefined;
                    this.username = undefined;
                }
            }
        );
    }

    ngOnDestroy(): void {
        this.subscription.unsubscribe();
    }

}
