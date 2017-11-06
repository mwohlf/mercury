import {Component, OnDestroy, OnInit} from '@angular/core';
import {Alert, AlertLevel, AlertService} from "../../services/alert.service";
import { trigger,style,transition,animate,keyframes,query,stagger } from '@angular/animations';
import {Subscription} from "rxjs/Subscription";


@Component({
    selector: 'mc-alert-container',
    templateUrl: './alertContainer.component.html',
    styleUrls: ['./alertContainer.component.scss'],
    animations: [

        trigger('alertAnimation', [
            transition('* => *', [

                query(':leave',
                    animate('0.5s ease-in-out', keyframes([
                        style({transform: 'translateX(0)', offset: 0.0}),
                        style({transform: 'translateX(calc(100vw))', offset: 1.0}),
                    ])), {optional: true}),

                query(':enter',
                    animate('0.5s ease-in-out', keyframes([
                        style({transform: 'translateY(calc(100vh))', offset: 0.0}),
                        style({transform: 'translateY(0)',     offset: 1.0}),
                    ])), {optional: true})
            ])
        ])

    ]
})

export class AlertContainerComponent implements OnInit, OnDestroy {

    private alertSubscription: Subscription;

    private visibleAlerts: Alert[] = [];

    public AlertLevel:  any = AlertLevel;

    constructor(private alertService: AlertService) { }

    ngOnInit() {
        this.alertSubscription = this.alertService.getAlerts().subscribe((alerts: Alert[]) => {
            this.visibleAlerts = alerts;
        });
    }

    ngOnDestroy(): void {
        this.alertSubscription.unsubscribe();
    }

    doShowDetails(details: String) {
        console.log(details);
    }
}