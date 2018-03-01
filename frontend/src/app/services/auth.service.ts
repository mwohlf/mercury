import {Injectable, OnDestroy} from '@angular/core';

import {Observable} from 'rxjs/Rx';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {SecurityControllerService} from '../../generated/api/securityController.service';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';
import {LoginRequest} from '../../generated/model/loginRequest';
import {PrincipalResponse} from '../../generated/model/principalResponse';

/**
 * AuthService uses JSON-Web-Token authorization strategy.
 * Fetched token and user details are stored in sessionStorage.
 *
 * see: http://jasonwatmore.com/post/2016/09/29/angular-2-user-registration-and-login-example-tutorial#alert-service-ts
 *
 *
 * sessionstore: not hared between tabs
 *               cleared after browser or tab close
 *
 *  localstore; shared between tabs
 *
 */
@Injectable()
export class AuthService implements OnDestroy {

    public static readonly NULL_PRINCIPAL: PrincipalResponse = {};

    private subject = new BehaviorSubject<PrincipalResponse>(AuthService.NULL_PRINCIPAL); // start with null subject


    constructor(private securityControllerService: SecurityControllerService) {
        this.init();
    }

    private init() {
        return this.securityControllerService.loginUsingGET().subscribe(
            principal => {
                this.subject.next(principal);
            },
            error => {
                // no cookie or token, no big deal, we just didn"t login so far
                this.subject.next(AuthService.NULL_PRINCIPAL);
            }
        );
    }

    ngOnDestroy(): void {
        // cleanup subscriptions
        this.subject.complete();
    }

    getPrincipal(): Observable<PrincipalResponse> {
        return this.subject.asObservable();
    }

    public isAuthenticated(): boolean {
        return Boolean(this.subject.value !== AuthService.NULL_PRINCIPAL);
    }

    // returns an observable that will return the claims json object
    public login(username: string, password: string): Observable<PrincipalResponse> {
        var request: LoginRequest = {
            username: username,
            password: password
        };

        console.log("<login> in auth.service");
        return this.securityControllerService.loginUsingPOST(request)
            .map(
                principalResponse => {
                    console.log("principalResponse" + principalResponse);
                    this.subject.next(principalResponse);
                    return principalResponse;
                })
            .catch(
                error => {
                    return Observable.throw(error);
                });
    }

    public logout(): void {
        this.securityControllerService.logoutUsingGET().subscribe(
            principal => {
                this.subject.next(AuthService.NULL_PRINCIPAL);
            }
        )
    }

    public getUsername(): string | undefined {
        if (this.isAuthenticated()) {
            return this.subject.value.username;
        } else {
            return null;
        }
    }

    public getUserId(): number | undefined {
        if (this.isAuthenticated()) {
            return this.subject.value.userId;
        } else {
            return null;
        }
    }

}

