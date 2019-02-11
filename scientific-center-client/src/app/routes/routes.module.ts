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

const routes: Routes = [
  { path: '', redirectTo: '/papers', pathMatch: 'full' },

  { path: 'signin', component: SigninComponent },

  { path: 'papers', component: PaperListComponent },
  { path: 'papers/new', component: NewPaperComponent },
  { path: 'papers/:id', component: PaperDetailsComponent },

  { path: 'papers/:id/reviewers', component: ReviewerListComponent },

  { path: 'payments/register', component: RegisterPaymentComponent },
  { path: 'payments/callback/:status', component: CallbackComponent },

  { path: 'journals', component: JournalListComponent },
  { path: 'journals/:id', component: JournalDetailsComponent },
  { path: 'journals/:journalId/issues/:issueId', component: IssueDetailsComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class RoutesModule { }
