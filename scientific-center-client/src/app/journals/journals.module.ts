import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { JournalListComponent } from './journal-list/journal-list.component';
import { SharedModule } from '../shared/shared.module';
import { JournalDetailsComponent } from './journal-details/journal-details.component';
import { IssueDetailsComponent } from './issue-details/issue-details.component';

@NgModule({
  declarations: [JournalListComponent, JournalDetailsComponent, IssueDetailsComponent],
  imports: [
    CommonModule,
    SharedModule
  ]
})
export class JournalsModule { }
