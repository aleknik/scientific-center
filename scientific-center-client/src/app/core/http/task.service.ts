import { Injectable } from '@angular/core';
import { RestService } from './rest.service';
import { ToastrService } from 'ngx-toastr';
import { HttpClient } from '@angular/common/http';
import { Task } from 'src/app/shared/model/task.model';
import { pipe, Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class TaskService extends RestService {

  constructor(http: HttpClient, toastr: ToastrService) {
    super(http, '/api/tasks', toastr);
  }

  getTasks(): Observable<Task[]> {
    return this.http.get<Task[]>(this.baseUrl)
      .pipe(catchError(this.handleError<Task[]>()));
  }

}
