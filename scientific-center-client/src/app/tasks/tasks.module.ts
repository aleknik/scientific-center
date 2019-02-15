import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared/shared.module';
import { TaskListComponent } from './task-list/task-list.component';

@NgModule({
  declarations: [TaskListComponent],
  imports: [
    CommonModule,
    SharedModule
  ]
})
export class TasksModule { }
