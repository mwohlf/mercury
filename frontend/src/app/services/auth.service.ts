import {Injectable, OnDestroy} from '@angular/core';

import {Observable} from 'rxjs/Rx';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {SecurityControllerService} from "../../generated/api/securityController.service";
import {AuthenticationRequest} from "../../generated/model/authenticationRequest";
import {Subject} from "rxjs/Subject";
import {BehaviorSubject} from "rxjs/BehaviorSubject";

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

    private subject = new BehaviorSubject<Principal>(Principal.NULL); // start with null subject


    private static readonly STORE_TOKEN_KEY = "token";

    private token: any; // decoded and parsed json values with all the auth data if available

    public static readonly CLAIM_KEY_USERID = "uid";
    public static readonly CLAIM_KEY_SUBJECT = "sub";
    public static readonly CLAIM_KEY_AUTHORITIES = "auth";
    public static readonly CLAIM_KEY_ISSUED_AT = "iat";


    constructor(private securityControllerService: SecurityControllerService) {
        this.init();
    }

    ngOnDestroy(): void {
        this.subject.complete();
    }

    getPrincipal(): Observable<Principal> {
        return this.subject.asObservable();
    }


    private init() {
        // try sessionStorage
        var tokenString = sessionStorage.getItem(AuthService.STORE_TOKEN_KEY);
        if (!tokenString) {
            // try localStorage
            tokenString = localStorage.getItem(AuthService.STORE_TOKEN_KEY);
            if (tokenString) {
                // move to sessionstore
                sessionStorage.setItem(AuthService.STORE_TOKEN_KEY, tokenString);
            }
        }
        try {
            this.updateTokenAndSubject(JSON.parse(tokenString));
        } catch(e) {
            this.updateTokenAndSubject();
        }
    }

    public isAuthenticated(): boolean {
        return Boolean(this.token);
    }


    // returns an observable that will return the claims json object
    public login(username: string, password: string): Observable<Principal> {
        var request: AuthenticationRequest = {
            username: username,
            password: password
        };

        return this.securityControllerService.authenticateUsingPOST(request)
            .map(
                tokenResponse => {
                    console.log("tokenResponse" + tokenResponse);
                    const nextTtoken = this.jwtParse(tokenResponse.token);
                    var tokenString = JSON.stringify(nextTtoken);
                    sessionStorage.setItem(AuthService.STORE_TOKEN_KEY, tokenString);
                    localStorage.setItem(AuthService.STORE_TOKEN_KEY, tokenString);
                    return this.updateTokenAndSubject(nextTtoken);
                })
            .catch(
                error => {
                    this.updateTokenAndSubject();
                    return Observable.throw(error);
                });
    }

    public logout(): void {
        sessionStorage.removeItem(AuthService.STORE_TOKEN_KEY);
        // TODO: when do we cleanup localStorage?
        this.updateTokenAndSubject();
    }

    private jwtParse(token: string): any {
        const base64Url = token.split('.')[1]; // second part of the token string
        const base64 = base64Url
            .replace('-', '+')
            .replace('_', '/');
        return JSON.parse(window.atob(base64));
    }

    public refreshToken(token: string): Observable<void | {}> {
        const requestParam = {token: this.token};

        /*
        return this.http.post(AuthService.REFRESH_TOKEN_URL, requestParam, this.generateOptions())
            .map((res: Response) => {
                this.saveToken(res);
            }).catch(err => {
                throw Error(err.json().message);
            });
            */
        return undefined;
    }

    public getUsername(): string | undefined {
        if (this.token) {
            return this.token[AuthService.CLAIM_KEY_SUBJECT];
        } else {
            return null;
        }
    }

    public getUserId(): number | undefined {
        if (this.token) {
            return this.token[AuthService.CLAIM_KEY_USERID];
        } else {
            return null;
        }
    }

    // call this when we have a new token value received
    private updateTokenAndSubject(nextToken?: any): Principal {
        this.token = nextToken;
        if (this.token) {
            var principal = new Principal();
            principal.userId = this.getUserId();
            principal.userName = this.getUsername();
            console.log(principal);
            this.subject.next(principal);
            return principal;
        } else {
            this.subject.next(Principal.NULL);
            return Principal.NULL;
        }
    }

}

// this is supposed to be more than the data from the token since we
// might need a second request to gather email, privileges etc.
export class Principal {

    public static readonly NULL = new Principal();

    userId: number;
    userName: string;
}

