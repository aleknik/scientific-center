import { Injectable } from '@angular/core';
import { RestService } from './rest.service';
import { ToastrService } from 'ngx-toastr';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RegisteredMethod } from 'src/app/shared/model/registered-method.Model';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class PaymentService extends RestService {

  constructor(http: HttpClient, toastr: ToastrService) {
    super(http, '/api/payments', toastr);
  }

  getMethods(queryParams = {}): Observable<RegisteredMethod[]> {
    return this.http
      .get<RegisteredMethod[]>(`${this.baseUrl}/register-urls`, { params: queryParams })
      .pipe(catchError(this.handleError<RegisteredMethod[]>()));
  }
}
