import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { TokenUtilsService } from '../util/token-utils.service';
import { RestService } from './rest.service';
import { HttpClient } from '@angular/common/http';
import { User } from 'src/app/shared/model/user.model';
import { Authentication } from 'src/app/shared/model/authentication.model';
import { Observable } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';

const authenticatedUserKey = 'authenticatedUser';

@Injectable({
  providedIn: 'root'
})
export class AuthService extends RestService {

  constructor(protected http: HttpClient,
    toastr: ToastrService,
    private tokenUtils: TokenUtilsService) {
    super(http, '/api/auth', toastr);
  }

  authenticate(body: User): Observable<Authentication> {
    return this.http.post<Authentication>(this.baseUrl, body).pipe(
      tap(res => {
        localStorage.setItem(authenticatedUserKey, JSON.stringify({
          id: res.id,
          permissions: this.tokenUtils.getPermissions(res.token),
          token: res.token
        }));
      }),
      catchError(this.handleError<Authentication>())
    );
  }

  getAuthenticatedUser() {
    return JSON.parse(localStorage.getItem(authenticatedUserKey));
  }

  getAuthenticatedUserId() {
    return this.getAuthenticatedUser().id;
  }

  getToken(): String {
    const authenticatedUser = this.getAuthenticatedUser();
    const token = authenticatedUser && authenticatedUser.token;
    return token ? token : '';
  }

  getPermissions(): Array<String> {
    const authenticatedUser = this.getAuthenticatedUser();
    const permissions = authenticatedUser && authenticatedUser.permissions;
    return permissions ? permissions : new Array<String>();
  }

  checkPermission(permission: string): boolean {
    return this.getPermissions().includes(permission);
  }

  isAuthenticated(): boolean {
    return this.getToken() !== '';
  }

  signout(): void {
    localStorage.removeItem(authenticatedUserKey);
  }
}
