import { Component, OnInit } from '@angular/core';
import { ReviewerService } from 'src/app/core/http/reviewer.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormField } from 'src/app/shared/model/form-field.model';
import { VariableValue } from 'src/app/shared/model/variable-value.model';
import { PaperService } from 'src/app/core/http/paper.service';
import * as FileSaver from "file-saver"
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-review-paper',
  templateUrl: './review-paper.component.html',
  styleUrls: ['./review-paper.component.css']
})
export class ReviewPaperComponent implements OnInit {

  taskId: string;
  comments: string;
  suggestion: string;
  staffComments: string;

  suggestions = ['minorFixes', 'largerFixes', 'accept', 'reject']

  constructor(private reviewerService: ReviewerService,
    private route: ActivatedRoute,
    private router: Router,
    private paperService: PaperService,
    private toastr: ToastrService) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.taskId = params['taskId'];
    });
  }

  submit() {
    const formfields = new Array<FormField>();
    formfields.push(this.createFormField('comments', this.comments));
    formfields.push(this.createFormField('suggestion', this.suggestion));
    formfields.push(this.createFormField('staffComments', this.staffComments));
    this.reviewerService.postReview(this.taskId, formfields).subscribe(res => {
      this.toastr.success("Reviewers submitted");
      this.router.navigate(['tasks']);
    });
  }

  createFormField(key: string, value: any): FormField {
    const formField = new FormField();
    formField.name = key;
    formField.value = new VariableValue();
    formField.value.value = value;

    return formField;
  }

  download() {
    this.paperService.downloadPaperByTask(this.taskId).subscribe(res => this.downloadFile(res));
  }

  downloadFile(data) {
    const blob = new Blob([data], { type: 'application/octet-stream' });
    FileSaver.saveAs(blob, `${this.taskId}.pdf`);
  }


}
