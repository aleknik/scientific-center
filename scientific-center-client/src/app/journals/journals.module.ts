import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { JournalListComponent } from './journal-list/journal-list.component';
import { SharedModule } from '../shared/shared.module';
import { JournalDetailsComponent } from './journal-details/journal-details.component';
import { IssueDetailsComponent } from './issue-details/issue-details.component';
import { ChooseJournalComponent } from './choose-journal/choose-journal.component';
import { SubscribeComponent } from './subscribe/subscribe.component';

@NgModule({
  declarations: [JournalListComponent, JournalDetailsComponent, IssueDetailsComponent, ChooseJournalComponent, SubscribeComponent],
  imports: [
    CommonModule,
    SharedModule
  ]
})
export class JournalsModule { }
