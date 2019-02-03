import { Injectable } from '@angular/core';
import { RestService } from './rest.service';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Paper } from 'src/app/shared/model/paper.model';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class PaperService extends RestService {

  constructor(http: HttpClient, toastr: ToastrService) {
    super(http, '/api/papers', toastr);
  }

  createPaper(paper: Paper, file: File): Observable<Paper> {
    let formData = new FormData();
    formData.append('file', file);
    formData.append('data', new Blob([JSON.stringify(paper)], { type: 'application/json' }));

    return this.http.post<Paper>(this.baseUrl, formData)
      .pipe(catchError(this.handleError<Paper>()));
  }
}
