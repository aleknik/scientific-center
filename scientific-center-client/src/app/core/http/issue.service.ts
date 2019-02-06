import { Injectable } from '@angular/core';
import { Issue } from 'src/app/shared/model/issue.model';
import { RestService } from './rest.service';
import { HttpClient } from '@angular/common/http';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class IssueService extends RestService {

  constructor(http: HttpClient, toastr: ToastrService) {
    super(http, '/api/issues', toastr);
  }

  findById(id: number): Observable<Issue> {
    return this.http.get<Issue>(`${this.baseUrl}/${id}`)
      .pipe(catchError(this.handleError<Issue>()));
  }
}
