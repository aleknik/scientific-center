import { Injectable } from '@angular/core';
import { RestService } from './rest.service';
import { ToastrService } from 'ngx-toastr';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RegisteredMethod } from 'src/app/shared/model/registered-method.Model';
import { catchError } from 'rxjs/operators';
import { PaymentStatusRequest } from 'src/app/shared/model/payment-status-request.model';
import { PaymentStatusResponse } from 'src/app/shared/model/payment-status-response.model';

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

  buyPaper(id: number, successUrl: string, errorUrl: string): Observable<string> {
    return this.http.post(`${this.baseUrl}/buy-paper`,
      {
        id,
        successUrl,
        errorUrl
      },
      { responseType: 'text' })
      .pipe(catchError(this.handleError<string>()));
  }

  subscribeToJournal(id: number, successUrl: string, errorUrl: string): Observable<string> {
    return this.http.post(`${this.baseUrl}/subscribe-journal`,
      {
        id,
        successUrl,
        errorUrl
      },
      { responseType: 'text' })
      .pipe(catchError(this.handleError<string>()));
  }

  checkSubcriptionStatus(id: number): Observable<string> {
    return this.http.get(`${this.baseUrl}/status/journals/${id}`,
      { responseType: 'text' });
  }

  checkPaperStatus(id: number): Observable<string> {
    return this.http.get(`${this.baseUrl}/status/papers/${id}`,
      { responseType: 'text' });
  }
}
