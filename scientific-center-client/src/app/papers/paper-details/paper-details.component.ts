import { Component, OnInit } from '@angular/core';
import { Paper } from 'src/app/shared/model/paper.model';
import { PaperService } from 'src/app/core/http/paper.service';
import { ActivatedRoute, Router } from '@angular/router';
import { PaymentService } from 'src/app/core/http/payment.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-paper-details',
  templateUrl: './paper-details.component.html',
  styleUrls: ['./paper-details.component.css']
})
export class PaperDetailsComponent implements OnInit {

  paper = new Paper();

  paperId: number;

  status: string;

  constructor(private paperService: PaperService,
    private route: ActivatedRoute,
    private paymentService: PaymentService,
    private location: Location) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.paperId = params['id'];
      this.getPaper();
      this.getStatus();
    });
  }

  getPaper() {
    this.paperService.findById(this.paperId).subscribe(res => {
      this.paper = res;
    });
  }

  buyPaper() {
    const address = window.location.origin + "/#/payments/callback";
    this.paymentService.buyPaper(this.paperId, `${address}/success`, `${address}/error`).subscribe(res => {
      window.location.href = res;
    })
  }

  getStatus() {
    this.paymentService.checkPaperStatus(this.paperId).subscribe(res => {
      this.status = res
    }, () => {
      this.status = null;
    })
  }

  showBuy(): boolean {
    return this.status == null || this.status == "CANCELED";
  }
  showProcessing(): boolean {
    return this.status == "PROCESSING" || this.status == "NEW";
  }

}
