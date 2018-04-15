import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';


@Injectable()
export class AuthenticationInterceptor implements HttpInterceptor {

    constructor() {
    }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        // decorate the request, not needed atm because the cookie is added by the browser
        // TODO: uc.setRequestProperty("Authorization","Bearer " + jwt);
        return next.handle(request);
    }

}
