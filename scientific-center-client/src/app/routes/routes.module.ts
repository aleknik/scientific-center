import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SigninComponent } from '../auth/signin/signin.component';
import { PaperListComponent } from '../papers/paper-list/paper-list.component';
import { NewPaperComponent } from '../papers/new-paper/new-paper.component';
import { RegisterPaymentComponent } from '../payments/register-payment/register-payment.component';

const routes: Routes = [
  { path: '', redirectTo: '/papers', pathMatch: 'full' },

  { path: 'signin', component: SigninComponent },

  { path: 'papers', component: PaperListComponent },
  { path: 'papers/new', component: NewPaperComponent },

  { path: 'payments/register', component: RegisterPaymentComponent },

];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class RoutesModule { }
