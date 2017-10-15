import {Component, OnDestroy, OnInit} from '@angular/core';
import {Alert, AlertService} from "../../services/alert.service";
import {Subscription} from "rxjs/Subscription";

@Component({
    selector: 'mc-alert',
    templateUrl: './alert.component.html'
})

export class AlertComponent implements OnInit, OnDestroy {

    private alertSubscription: Subscription;

    private visibleAlerts: Alert[] = [];


    constructor(private alertService: AlertService) { }

    ngOnInit() {
        this.alertSubscription = this.alertService.getAlerts().subscribe((alerts: Alert[]) => {
            // TODO: we need some sort of animation here
            this.visibleAlerts = alerts;
            console.log("updated alerts, count is " + this.visibleAlerts.length);
        });
    }

    ngOnDestroy(): void {
        this.alertSubscription.unsubscribe();
    }

    removeAlert(alert: Alert) {
        this.alertService.removeAlert(alert);
    }
}