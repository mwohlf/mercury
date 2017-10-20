import {Directive, Input, OnDestroy, OnInit, TemplateRef, ViewContainerRef} from '@angular/core';
import {AuthService, Principal} from '../services/auth.service';
import {Subscription} from "rxjs/Subscription";

@Directive({ selector: '[showAuthed]' })
export class ShowAuthedDirective implements OnInit, OnDestroy {

    private subscription: Subscription;

    constructor(private templateRef: TemplateRef<any>,
                private authService: AuthService,
                private viewContainer: ViewContainerRef) {
    }

    condition: boolean;

    @Input()
    set showAuthed(condition: boolean) {
        this.condition = condition;
    }

    ngOnInit() {
        this.subscription = this.authService.getPrincipal().subscribe(
            (principal) => {
                const authenticated = !(principal == Principal.NULL);
                if (authenticated == this.condition) {
                    if (this.viewContainer.length == 0) {
                        this.viewContainer.createEmbeddedView(this.templateRef);
                    }
                } else {
                    this.viewContainer.clear();
                }
            }
        )
    }

    ngOnDestroy(): void {
        this.subscription.unsubscribe();
    }

}