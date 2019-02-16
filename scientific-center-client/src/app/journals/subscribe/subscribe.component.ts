import { Component, OnInit } from '@angular/core';
import { Journal } from 'src/app/shared/model/journal.model';
import { JournalService } from 'src/app/core/http/journal.service';
import { ToastrService } from 'ngx-toastr';
import { ActivatedRoute } from '@angular/router';
import { PaymentService } from 'src/app/core/http/payment.service';

@Component({
  selector: 'app-subscribe',
  templateUrl: './subscribe.component.html',
  styleUrls: ['./subscribe.component.css']
})
export class SubscribeComponent implements OnInit {

  journal: Journal;
  taskId: string;

  journals = new Array<Journal>();

  constructor(private journalService: JournalService, private toastService: ToastrService,
    private route: ActivatedRoute,
    private paymentService: PaymentService) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.taskId = params['taskId'];
      this.getJournal();
    });
  }
  getJournal(): any {
    this.journalService.findByTaskId(this.taskId).subscribe(res => {
      this.journal = res;
    })
  }

  subscribe() {
    const address = window.location.origin + "/#/payments/callback";
    this.journalService.subscribe(this.taskId).subscribe(res => {
      this.paymentService.subscribeToJournal(this.journal.id, `${address}/success`, `${address}/error`).subscribe(res => {
        window.location.href = res;
      })
    })
  }
}
