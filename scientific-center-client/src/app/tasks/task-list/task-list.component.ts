import { Component, OnInit } from '@angular/core';
import { TaskService } from 'src/app/core/http/task.service';
import { Task } from 'src/app/shared/model/task.model';
import { TaskData } from '@angular/core/src/testability/testability';
import { Router } from '@angular/router';

@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.css']
})
export class TaskListComponent implements OnInit {

  tasks = new Array<Task>();

  constructor(private taskService: TaskService,
    private router: Router) { }

  ngOnInit() {
    this.getTasks();
  }
  getTasks(): any {
    this.taskService.getTasks().subscribe(res => {
      this.tasks = res;
    });
  }

  goToTask(task: Task) {
    switch (task.taskDefinitionKey) {
      case "SubmitPaper": {
        this.router.navigate(['/papers/new', task.id]);
        break;
      }
      case "PaperRelevant": {
        this.router.navigate(['/paper-relevant', task.id]);
        break;
      }
      case "ChooseReviewers": {
        this.router.navigate(['/reviewers', task.id]);
        break;
      }
      case "ReviewPaper": {
        this.router.navigate(['/review-paper', task.id]);
        break;
      }
      case "EditorReview": {
        this.router.navigate(['/editor-review', task.id]);
        break;
      }
      case "PaperRevision": {
        this.router.navigate(['/paper-revision', task.id]);
        break;
      }
      case "EditorRevisionReview": {
        this.router.navigate(['/editor-revision-review', task.id]);
        break;
      }
      case "SubmitFormatedPaper": {
        this.router.navigate(['/format', task.id]);
        break;
      }
      case "ChooseJournal": {
        this.router.navigate(['journals/choose', task.id]);
        break;
      }
      case "SubscribeJournal": {
        this.router.navigate(['journal-subscribe', task.id]);
        break;
      }

      default: {
        console.log("Invalid choice");
        break;
      }
    }
  }

}
