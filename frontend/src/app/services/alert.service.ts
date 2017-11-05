import {Injectable, OnDestroy} from '@angular/core';
import {NavigationStart, Router} from '@angular/router';
import {Observable} from 'rxjs';
import {BehaviorSubject} from "rxjs/BehaviorSubject";
import {Subscription} from "rxjs/Subscription";

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

    dispose(alert: Alert | undefined): Alert {
        if (alert) {
            var index = this.alerts.indexOf(alert, 0);
            if (index > -1) {
                this.alerts.splice(index, 1);
            }
            this.subject.next(this.alerts);
        }
        return alert;
    }

    success(message: string): Alert {
        return this.message(message, AlertLevel.SUCCESS);
    }

    info(message: string): Alert {
        return this.message(message, AlertLevel.INFO);
    }

    warn(message: string): Alert {
        return this.message(message, AlertLevel.WARN);
    }

    error(message: string): Alert {
        return this.message(message, AlertLevel.ERROR);
    }

    show(alert: Alert): Alert {
        this.alerts.push(alert);
        this.subject.next(this.alerts);
        return alert;
    }

    message(message = "oops", level = AlertLevel.INFO): Alert {
        return new Alert(this, this.router, message, level);
    }

    clear() {
        this.alerts.forEach(alert => {alert.dispose()});
    }

    public handleError(error: any): void {
            switch (error.status) {
        case 404:
            this.error("User not found").timeout(5).show();
            break;
        default:
            this.error(JSON.stringify(error)).timeout(5).show();
        }
    }

}

export class Alert {

    private subscription: Subscription;

    public isCloseable = false;

    public hasDetails: string;

    constructor(
        private alertService: AlertService,
        private router: Router,
        public message: string,
        public level: AlertLevel) {
    }

    timeout(timeout: number): Alert {
        if (this.subscription) {
            this.subscription.unsubscribe();
            this.subscription = null;
        }
        this.subscription = Observable.timer(timeout * 1000)
            .subscribe(evemt => {
                this.dispose();
            }
        );
        return this;
    }

    closeable(): Alert {
        this.isCloseable = true;
        return this;
    }

    // dispose on page changes
    transient(): Alert {
        if (this.subscription) {
            this.subscription.unsubscribe();
            this.subscription = null;
        }
        this.subscription = this.router.events
            .filter(event => event instanceof NavigationStart)
            .subscribe((event: NavigationStart) => {
                this.dispose();
            });
        return this;
    }

    details(hasDetails: string): Alert {
        this.hasDetails = hasDetails;
        return this;
    }

    show(): Alert {
        if (this.alertService) {
            this.alertService.show(this);
        }
        return this;
    }

    dispose(): void {
        if (this.subscription) {
            this.subscription.unsubscribe();
            this.subscription = null;
        }
        if (this.alertService) {
            this.alertService.dispose(this);
        }
    }

}

export enum AlertLevel {
    SUCCESS,
    INFO,
    WARN,
    ERROR,
}
