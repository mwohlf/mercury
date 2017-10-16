import {Injectable, OnDestroy} from '@angular/core';
import {NavigationStart, Router} from '@angular/router';
import {Observable} from 'rxjs';
import {BehaviorSubject} from "rxjs/BehaviorSubject";

@Injectable()
export class AlertService implements OnDestroy {

    private subject = new BehaviorSubject<Alert[]>([]);

    private alerts: Alert[] = [];


    constructor(private router: Router) {
        // clear alert message on route change
        router.events.subscribe(event => {
            if (event instanceof NavigationStart) {
                // cleanout old alerts
            }
        });
    }

    ngOnDestroy(): void {
        this.subject.complete();
    }

    getAlerts(): Observable<Alert[]> {
        return this.subject.asObservable();
    }

    dismiss(alert: Alert | undefined): void {
        if (alert) {
            var index = this.alerts.indexOf(alert, 0);
            if (index > -1) {
                this.alerts.splice(index, 1);
            }
            this.subject.next(this.alerts);
        }
    }

    success(message: string): Alert {
       return this.message(message, Level.SUCCESS);
    }

    error(message: string): Alert {
       return this.message(message, Level.SUCCESS);
    }


    message(message = "oops", level = Level.INFO, behavior = Behavior.PAGE_LOCAL): Alert {
        var nextAlert = new Alert();
        nextAlert.message = message;
        nextAlert.level = level;
        nextAlert.behavior = behavior;
        nextAlert.alertService = this;
        this.alerts.push(nextAlert);
        this.subject.next(this.alerts);
        return nextAlert;
    }

    clear() {
        this.alerts.forEach(alert => {alert.dismiss()});
    }
}

export class Alert {

    public static readonly NULL: Alert = new Alert()

    level: Level;
    behavior: Behavior;
    message: string;

    alertService: AlertService;

    dismiss() {
        if (this.alertService) {
            this.alertService.dismiss(this);
        }
    }
}

export enum Level {
    SUCCESS,
    INFO,
    WARN,
    ERROR,
}

export enum Behavior {
    PAGE_LOCAL,
    STICKY,
    TIMEOUT,
}