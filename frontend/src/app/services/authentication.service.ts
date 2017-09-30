import { Injectable } from '@angular/core';
import { Http, Headers, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map'



/*
see: http://jasonwatmore.com/post/2016/09/29/angular-2-user-registration-and-login-example-tutorial#alert-service-ts
 */
@Injectable()
export class AuthenticationService {
    constructor(private http: Http) { }


}
