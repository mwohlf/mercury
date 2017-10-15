import {Injectable, OnDestroy} from '@angular/core';
import {NavigationStart, Router} from '@angular/router';
import {Observable} from 'rxjs';
import {Subject} from 'rxjs/Subject';

@Injectable()
export class AlertService implements OnDestroy {

    private subject = new Subject<Alert[]>();

    private keepAfterNavigationChange = false;

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

    removeAlert(alert: Alert | undefined): void {
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
        this.alerts.push(nextAlert);
        this.subject.next(this.alerts);
        return nextAlert;
    }

}

export class Alert {
    level: Level;
    behavior: Behavior;
    message: string;
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