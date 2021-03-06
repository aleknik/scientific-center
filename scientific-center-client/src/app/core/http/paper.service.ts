import { Injectable } from '@angular/core';
import { RestService } from './rest.service';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Paper } from 'src/app/shared/model/paper.model';
import { catchError } from 'rxjs/operators';
import { PaperSearchResult } from 'src/app/shared/model/paper-search-result.model';
import { PaperQuery } from 'src/app/shared/model/paper-query.model';
import { TaskForm } from 'src/app/shared/model/task-form.model';
import { FormField } from 'src/app/shared/model/form-field.model';
import { ReviewerSearchResult } from 'src/app/shared/model/reviewer-search-result.model';
import { FormatPaper } from 'src/app/shared/model/format-paper.model';
import { Review } from 'src/app/shared/model/review.model';

@Injectable({
  providedIn: 'root'
})
export class PaperService extends RestService {

  constructor(http: HttpClient, toastr: ToastrService) {
    super(http, '/api/papers', toastr);
  }

  createPaper(taskId: string, paper: Paper, file: File): Observable<Paper> {
    let formData = new FormData();
    formData.append('file', file);
    formData.append('data', new Blob([JSON.stringify(paper)], { type: 'application/json' }));

    return this.http.post<Paper>(`${this.baseUrl}/${taskId}`, formData)
      .pipe(catchError(this.handleError<Paper>()));
  }

  resubmit(taskId: string, file: File, reviews: Review[]) {
    let formData = new FormData();
    formData.append('file', file);
    formData.append('data', new Blob([JSON.stringify(reviews)], { type: 'application/json' }));
    return this.http.post(`${this.baseUrl}/resubmit/${taskId}`, formData)
      .pipe(catchError(this.handleError<any>()));
  }

  startPaperProcess() {
    return this.http.post(`${this.baseUrl}/start-paper-process`, {})
      .pipe(catchError(this.handleError<any>()));
  }

  searchPapers(queries: PaperQuery[]): Observable<PaperSearchResult[]> {
    return this.http.post<PaperSearchResult[]>(`${this.baseUrl}/search`, queries)
      .pipe(catchError(this.handleError<PaperSearchResult[]>()));
  }

  findById(id: number): Observable<Paper> {
    return this.http.get<Paper>(`${this.baseUrl}/${id}`)
      .pipe(catchError(this.handleError<Paper>()));
  }

  downloadPaper(id: number) {
    return this.http.get(`${this.baseUrl}/download/${id}`, { responseType: 'blob' })
      .pipe(catchError(this.handleError<any>()));
  }

  downloadPaperByTask(taskId: string) {
    return this.http.get(`${this.baseUrl}/download/task/${taskId}`, { responseType: 'blob' })
      .pipe(catchError(this.handleError<any>()));
  }

  getRelevantForm(taskId: string) {
    return this.http.get<TaskForm>(`${this.baseUrl}/relevant/${taskId}/form`)
      .pipe(catchError(this.handleError<TaskForm>()));
  }

  postRelevant(taskId: string, fields: FormField[]) {
    return this.http.post<FormField[]>(`${this.baseUrl}/relevant/${taskId}`, fields)
      .pipe(catchError(this.handleError<FormField[]>()));
  }

  findByTaskId(taskId: string): Observable<Paper> {
    return this.http.get<Paper>(`${this.baseUrl}/task/${taskId}`)
      .pipe(catchError(this.handleError<Paper>()));
  }

  addReviewers(taskId: string, reviewers: ReviewerSearchResult[], date: string) {
    return this.http.post<ReviewerSearchResult[]>(`${this.baseUrl}/reviewers/${taskId}`, reviewers, { params: { 'date': date } })
      .pipe(catchError(this.handleError<ReviewerSearchResult[]>()));
  }

  addReviewer(taskId: string, reviewer: ReviewerSearchResult, date: string) {
    return this.http.post<ReviewerSearchResult>(`${this.baseUrl}/new-reviewer/${taskId}`, reviewer, { params: { 'date': date } })
      .pipe(catchError(this.handleError<ReviewerSearchResult>()));
  }

  getFormatData(taskId: string) {
    return this.http.get<FormatPaper>(`${this.baseUrl}/format-data/${taskId}`)
      .pipe(catchError(this.handleError<FormatPaper>()));
  }

  getReviews(taskId: string) {
    return this.http.get<Review[]>(`${this.baseUrl}/reviews/${taskId}`)
      .pipe(catchError(this.handleError<Review[]>()));
  }
}
