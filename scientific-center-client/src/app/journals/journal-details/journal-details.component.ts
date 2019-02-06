import { Component, OnInit } from '@angular/core';
import { Journal } from 'src/app/shared/model/journal.model';
import { JournalService } from 'src/app/core/http/journal.service';
import { ActivatedRoute } from '@angular/router';
import { PaymentService } from 'src/app/core/http/payment.service';

@Component({
  selector: 'app-journal-details',
  templateUrl: './journal-details.component.html',
  styleUrls: ['./journal-details.component.css']
})
export class JournalDetailsComponent implements OnInit {

  journal = new Journal();

  id: number;

  status: string;

  constructor(private journalService: JournalService,
    private route: ActivatedRoute,
    private paymentService: PaymentService) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.id = params['id'];
      this.getJournal();
      this.getStatus();
    });
  }

  getJournal() {
    this.journalService.findById(this.id).subscribe(res => {
      this.journal = res;
    });
  }

  buySubscription() {
    const address = window.location.origin + "/#/payments/callback";
    this.paymentService.subscribeToJournal(this.id, `${address}/success`, `${address}/error`).subscribe(res => {
      window.location.href = res;
    })
  }

  getStatus() {
    this.paymentService.checkSubcriptionStatus(this.id).subscribe(res => {
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

  showSuccess(): boolean {
    return this.status == "SUCCESS";
  }

}
