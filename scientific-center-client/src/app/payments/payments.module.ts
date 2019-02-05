import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RegisterPaymentComponent } from './register-payment/register-payment.component';
import { CallbackComponent } from './callback/callback.component';

@NgModule({
  declarations: [RegisterPaymentComponent, CallbackComponent],
  imports: [
    CommonModule
  ]
})
export class PaymentsModule { }
