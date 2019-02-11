import { Injectable } from '@angular/core';
import { RestService } from './rest.service';
import { HttpClient } from '@angular/common/http';
import { ToastrService } from 'ngx-toastr';
import { ReviewerQuery } from 'src/app/shared/model/reviewer-query.model';
import { Observable } from 'rxjs';
import { ReviewerSearchResult } from 'src/app/shared/model/reviewer-search-resutl.model';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ReviewerService extends RestService {

  constructor(http: HttpClient, toastr: ToastrService) {
    super(http, '/api/reviewers', toastr);
  }

  searchPapers(query: ReviewerQuery): Observable<ReviewerSearchResult[]> {
    return this.http.post<ReviewerSearchResult[]>(`${this.baseUrl}/search`, query)
      .pipe(catchError(this.handleError<ReviewerSearchResult[]>()));
  }
}
