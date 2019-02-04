import { Component, OnInit } from '@angular/core';
import { RegisteredMethod } from 'src/app/shared/model/registered-method.Model';
import { PaymentService } from 'src/app/core/http/payment.service';

@Component({
  selector: 'app-register-payment',
  templateUrl: './register-payment.component.html',
  styleUrls: ['./register-payment.component.css']
})
export class RegisterPaymentComponent implements OnInit {

  methods = new Array<RegisteredMethod>();

  constructor(private paymentService: PaymentService) { }

  ngOnInit() {
    this.getMethods();
  }

  getMethods() {
    this.paymentService.getMethods().subscribe(result => {
      this.methods = result;
    });
  }

  redirect(method: RegisteredMethod) {
    window.location.href = method.url;
  }

}
