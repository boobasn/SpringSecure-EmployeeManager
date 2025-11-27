import { Injectable } from '@angular/core';
import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // read token from sessionStorage (browser only) - SSR-safe check
    const token = typeof window !== 'undefined' ? sessionStorage.getItem('auth_token') : null;

    // if there is no token, pass the request through
    if (!token) return next.handle(req);

    // clone request with Authorization header
    const authReq = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`,
      },
    });

    return next.handle(authReq);
  }
}
