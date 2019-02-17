import { Component, OnInit } from '@angular/core';
import { ReviewerService } from 'src/app/core/http/reviewer.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormField } from 'src/app/shared/model/form-field.model';
import { VariableValue } from 'src/app/shared/model/variable-value.model';
import { PaperService } from 'src/app/core/http/paper.service';
import * as FileSaver from "file-saver"

@Component({
  selector: 'app-editor-review',
  templateUrl: './editor-review.component.html',
  styleUrls: ['./editor-review.component.css']
})
export class EditorReviewComponent implements OnInit {

  taskId: string;
  decision: string;
  needNewReviewers = false;

  suggestions = ['minorFixes', 'largerFixes', 'accept', 'reject']

  constructor(private reviewerService: ReviewerService,
    private route: ActivatedRoute,
    private router: Router,
    private paperService: PaperService) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.taskId = params['taskId'];
    });
  }

  submit() {
    const formfields = new Array<FormField>();
    formfields.push(this.createFormField('decision', this.decision));
    formfields.push(this.createFormField('needNewReviewers', this.needNewReviewers));
    this.reviewerService.editorReview(this.taskId, formfields).subscribe(res => { });
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
