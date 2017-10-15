import {Component, OnDestroy, OnInit} from '@angular/core';
import {Alert, AlertService} from "../../services/alert.service";
import {Subscription} from "rxjs/Subscription";

@Component({
    selector: 'mc-alert-container',
    templateUrl: './alertContainer.component.html'
})

export class AlertContainerComponent implements OnInit, OnDestroy {

    private alertSubscription: Subscription;

    private visibleAlerts: Alert[] = [];


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