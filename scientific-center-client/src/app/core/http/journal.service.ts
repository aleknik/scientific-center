import { Injectable } from '@angular/core';
import { RestService } from './rest.service';
import { HttpClient } from '@angular/common/http';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';
import { Journal } from 'src/app/shared/model/journal.model';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class JournalService extends RestService {

  constructor(http: HttpClient, toastr: ToastrService) {
    super(http, '/api/journals', toastr);
  }


  findAll(): Observable<Journal[]> {
    return this.http.get<Journal[]>(this.baseUrl)
      .pipe(catchError(this.handleError<Journal[]>()));
  }

  findById(id: number): Observable<Journal> {
    return this.http.get<Journal>(`${this.baseUrl}/${id}`)
      .pipe(catchError(this.handleError<Journal>()));
  }
}
