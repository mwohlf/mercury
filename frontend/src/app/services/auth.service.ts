import {Injectable} from '@angular/core';

import {Observable} from 'rxjs/Rx';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {SecurityControllerService} from "../../generated/api/securityController.service";
import {AuthenticationRequest} from "../../generated/model/authenticationRequest";

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
export class AuthService {

    private static readonly STORE_TOKEN_KEY = "token";

    private token: any; // decoded and parsed json values with all the auth data if available

    public static readonly CLAIM_KEY_USERID = "uid";
    public static readonly CLAIM_KEY_SUBJECT = "sub";
    public static readonly CLAIM_KEY_AUTHORITIES = "auth";
    public static readonly CLAIM_KEY_ISSUED_AT = "iat";


    constructor(private securityControllerService: SecurityControllerService) {
        this.init();
    }


    private init() {
        this.token = sessionStorage.getItem(AuthService.STORE_TOKEN_KEY);
        if (!this.token) {
            this.token = localStorage.getItem(AuthService.STORE_TOKEN_KEY);
            if (this.token) {
                sessionStorage.setItem(AuthService.STORE_TOKEN_KEY, JSON.stringify(this.token));
            }
        }
    }

    public isAuthenticated(): boolean {
        return Boolean(this.token);
    }


    // returns an observable that will return the claims json object
    public login(username: string, password: string): Observable<any> {
        var request: AuthenticationRequest = {
            username: username,
            password: password
        };

        return this.securityControllerService.authenticateUsingPOST(request).map(
            tokenResponse => {
                this.token = this.parse(tokenResponse.token);
                var tokenString = JSON.stringify(this.token);
                sessionStorage.setItem(AuthService.STORE_TOKEN_KEY, tokenString);
                localStorage.setItem(AuthService.STORE_TOKEN_KEY, tokenString);
            },
            errorResponse => {
                console.log(errorResponse);
            }
        )
    }

    public logout(): void {
        sessionStorage.removeItem(AuthService.STORE_TOKEN_KEY);
        this.token = null;
    }

    private parse(token: string): any {
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
            return;
        }
    }

    public getUserId(): number | undefined {
        if (this.token) {
            return this.token[AuthService.CLAIM_KEY_USERID];
        } else {
            return;
        }
    }
}
