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
                    animate('1s ease-in', keyframes([
                        style({opacity: 1, transform: 'translateY(0)',     offset: 0}),
                        style({opacity: 1, transform: 'translateY(300px)', offset: 1.0}),
                    ])), {optional: true}),

                query(':enter',
                    animate('1s ease-in', keyframes([
                        style({opacity: 1, transform: 'translateY(300px)', offset: 0}),
                        style({opacity: 1, transform: 'translateY(0)',     offset: 1.0}),
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
            // TODO: we need some sort of animation here
            this.visibleAlerts = alerts;
        });
    }

    ngOnDestroy(): void {
        this.alertSubscription.unsubscribe();
    }

}