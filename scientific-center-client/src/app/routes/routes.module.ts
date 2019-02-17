import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SigninComponent } from '../auth/signin/signin.component';
import { PaperListComponent } from '../papers/paper-list/paper-list.component';
import { NewPaperComponent } from '../papers/new-paper/new-paper.component';
import { RegisterPaymentComponent } from '../payments/register-payment/register-payment.component';
import { PaperDetailsComponent } from '../papers/paper-details/paper-details.component';
import { CallbackComponent } from '../payments/callback/callback.component';
import { JournalListComponent } from '../journals/journal-list/journal-list.component';
import { JournalDetailsComponent } from '../journals/journal-details/journal-details.component';
import { IssueDetailsComponent } from '../journals/issue-details/issue-details.component';
import { ReviewerListComponent } from '../reviewers/reviewer-list/reviewer-list.component';
import { SignupComponent } from '../auth/signup/signup.component';
import { ChooseJournalComponent } from '../journals/choose-journal/choose-journal.component';
import { TaskListComponent } from '../tasks/task-list/task-list.component';
import { PaperRelevantComponent } from '../papers/paper-relevant/paper-relevant.component';
import { ReviewPaperComponent } from '../reviewers/review-paper/review-paper.component';
import { EditorReviewComponent } from '../reviewers/editor-review/editor-review.component';
import { RevisionComponent } from '../papers/revision/revision.component';
import { EditorRevisionReviewComponent } from '../reviewers/editor-revision-review/editor-revision-review.component';
import { FormatComponent } from '../papers/format/format.component';
import { SubscribeComponent } from '../journals/subscribe/subscribe.component';
import { ChooseNewReviewerComponent } from '../reviewers/choose-new-reviewer/choose-new-reviewer.component';
import { IsAuthenticatedGuard } from './is-authenticated.guard';

const routes: Routes = [
  { path: '', redirectTo: '/papers', pathMatch: 'full' },

  { path: 'signin', component: SigninComponent },

  { path: 'signup', component: SignupComponent },

  { path: 'papers', component: PaperListComponent, canActivate: [IsAuthenticatedGuard] },
  { path: 'papers/new/:taskId', component: NewPaperComponent, canActivate: [IsAuthenticatedGuard] },
  { path: 'papers/:id', component: PaperDetailsComponent, canActivate: [IsAuthenticatedGuard] },
  { path: 'format/:taskId', component: FormatComponent, canActivate: [IsAuthenticatedGuard] },

  { path: 'paper-revision/:taskId', component: RevisionComponent, canActivate: [IsAuthenticatedGuard] },
  { path: 'reviewers/:taskId', component: ReviewerListComponent, canActivate: [IsAuthenticatedGuard] },
  { path: 'review-paper/:taskId', component: ReviewPaperComponent, canActivate: [IsAuthenticatedGuard] },
  { path: 'editor-review/:taskId', component: EditorReviewComponent, canActivate: [IsAuthenticatedGuard] },
  { path: 'editor-revision-review/:taskId', component: EditorRevisionReviewComponent, canActivate: [IsAuthenticatedGuard] },
  { path: 'new-review/:taskId', component: ChooseNewReviewerComponent, canActivate: [IsAuthenticatedGuard] },

  { path: 'payments/register', component: RegisterPaymentComponent, canActivate: [IsAuthenticatedGuard] },
  { path: 'payments/callback/:status', component: CallbackComponent, canActivate: [IsAuthenticatedGuard] },

  { path: 'journal-subscribe/:taskId', component: SubscribeComponent, canActivate: [IsAuthenticatedGuard] },
  { path: 'journals', component: JournalListComponent, canActivate: [IsAuthenticatedGuard] },
  { path: 'journals/choose/:taskId', component: ChooseJournalComponent, canActivate: [IsAuthenticatedGuard] },
  { path: 'journals/:id', component: JournalDetailsComponent, canActivate: [IsAuthenticatedGuard] },
  { path: 'journals/:journalId/issues/:issueId', component: IssueDetailsComponent, canActivate: [IsAuthenticatedGuard] },

  { path: 'tasks', component: TaskListComponent, canActivate: [IsAuthenticatedGuard] },

  { path: 'paper-relevant/:taskId', component: PaperRelevantComponent, canActivate: [IsAuthenticatedGuard] },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class RoutesModule { }
