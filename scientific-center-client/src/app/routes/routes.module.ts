import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SigninComponent } from '../auth/signin/signin.component';
import { PaperListComponent } from '../papers/paper-list/paper-list.component';
import { NewPaperComponent } from '../papers/new-paper/new-paper.component';
import { RegisterPaymentComponent } from '../payments/register-payment/register-payment.component';
import { PaperDetailsComponent } from '../papers/paper-details/paper-details.component';
import { CallbackComponent } from '../payments/callback/callback.component';

const routes: Routes = [
  { path: '', redirectTo: '/papers', pathMatch: 'full' },

  { path: 'signin', component: SigninComponent },

  { path: 'papers', component: PaperListComponent },
  { path: 'papers/new', component: NewPaperComponent },
  { path: 'papers/details/:id', component: PaperDetailsComponent },

  { path: 'payments/register', component: RegisterPaymentComponent },
  { path: 'payments/callback/:status', component: CallbackComponent }

];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class RoutesModule { }
