import { Injectable } from '@angular/core';
import { RestService } from './rest.service';
import { HttpClient } from '@angular/common/http';
import { ToastrService } from 'ngx-toastr';
import { ScienceField } from 'src/app/shared/model/science-field.model';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ScienceFieldService extends RestService {

  constructor(http: HttpClient, toastr: ToastrService) {
    super(http, '/api/science-fields', toastr);
  }

  findAll(): Observable<ScienceField[]> {
    return this.http.get<ScienceField[]>(this.baseUrl)
      .pipe(catchError(this.handleError<ScienceField[]>()));
  }
}
