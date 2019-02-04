import { Injectable, Injector } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class TokenInterceptorService {

  constructor(private inj: Injector) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const authService: AuthService = this.inj.get(AuthService);
    request = request.clone({
      setHeaders: {
        'X-Auth-Token': `${authService.getToken()}`
      }
    });

    return next.handle(request);
  }
}
