import { Component, OnInit } from '@angular/core';
import { Issue } from 'src/app/shared/model/issue.model';
import { IssueService } from 'src/app/core/http/issue.service';
import { ActivatedRoute } from '@angular/router';
import { PaymentService } from 'src/app/core/http/payment.service';

@Component({
  selector: 'app-issue-details',
  templateUrl: './issue-details.component.html',
  styleUrls: ['./issue-details.component.css']
})
export class IssueDetailsComponent implements OnInit {

  issue = new Issue();

  id: number;

  status: string;

  constructor(private issueService: IssueService,
    private route: ActivatedRoute,
    private paymentService: PaymentService) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.id = params['issueId'];
      this.getIssue();
      this.getStatus();
    });
  }

  getIssue() {
    this.issueService.findById(this.id).subscribe(res => {
      this.issue = res;
    });
  }

  buyIssue() {
    const address = window.location.origin + "/#/payments/callback";
    this.paymentService.buyIssue(this.id, `${address}/success`, `${address}/error`).subscribe(res => {
      window.location.href = res;
    })
  }

  getStatus() {
    this.paymentService.checkIssueStatus(this.id).subscribe(res => {
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
