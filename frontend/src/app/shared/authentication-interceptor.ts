import {Injectable} from "@angular/core";
import {AuthService} from "../services/auth.service";
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs/Observable";


@Injectable()
export class AuthenticationInterceptor implements HttpInterceptor {

    constructor() {
    }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        var rawToken = AuthService.getRawToken();
        console.log("another request to " + request.url);
        console.log("token: " + rawToken);

        if (rawToken) {
            request = request.clone({
                setHeaders: {
                    Authorization: `Bearer ${rawToken}`
                }
            });
        }

        return next.handle(request);
    }

}