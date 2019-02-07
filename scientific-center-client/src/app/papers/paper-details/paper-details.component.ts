import { Component, OnInit } from '@angular/core';
import { Paper } from 'src/app/shared/model/paper.model';
import { PaperService } from 'src/app/core/http/paper.service';
import { ActivatedRoute, Router } from '@angular/router';
import { PaymentService } from 'src/app/core/http/payment.service';
import * as FileSaver from "file-saver"

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
    private paymentService: PaymentService) { }

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

  showDownload(): boolean {
    return this.status == "SUCCESS" || (this.paper && this.paper.journal && this.paper.journal.openAccess);
  }

  download() {
    this.paperService.downloadPaper(this.paperId).subscribe(res => this.downloadFile(res));
  }

  downloadFile(data) {
    const blob = new Blob([data], { type: 'application/octet-stream' });
    FileSaver.saveAs(blob, `${this.paper.title}.pdf`);
  }

}
