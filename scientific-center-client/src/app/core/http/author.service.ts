import { Injectable } from '@angular/core';
import { RestService } from './rest.service';
import { HttpClient } from '@angular/common/http';
import { ToastrService } from 'ngx-toastr';
import { TokenUtilsService } from '../util/token-utils.service';
import { TaskForm } from 'src/app/shared/model/task-form.model';
import { catchError } from 'rxjs/operators';
import { FormField } from 'src/app/shared/model/form-field.model';

@Injectable({
  providedIn: 'root'
})
export class AuthorService extends RestService {

  constructor(protected http: HttpClient,
    toastr: ToastrService) {
    super(http, '/api/authors', toastr);
  }

  getSignupForm() {
    return this.http.get<TaskForm>(this.baseUrl + "/form")
      .pipe(catchError(this.handleError<TaskForm>()));
  }

  signup(taskId: string, fields: FormField[]) {
    return this.http.post<FormField[]>(`${this.baseUrl}/${taskId}`, fields)
      .pipe(catchError(this.handleError<FormField[]>()));
  }
}
