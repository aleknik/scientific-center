import { Component, OnInit } from '@angular/core';
import { ReviewerService } from 'src/app/core/http/reviewer.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormField } from 'src/app/shared/model/form-field.model';
import { VariableValue } from 'src/app/shared/model/variable-value.model';
import { PaperService } from 'src/app/core/http/paper.service';
import * as FileSaver from "file-saver"
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-editor-revision-review',
  templateUrl: './editor-revision-review.component.html',
  styleUrls: ['./editor-revision-review.component.css']
})
export class EditorRevisionReviewComponent implements OnInit {

  taskId: string;
  changesCorrect = false;

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
    formfields.push(this.createFormField('changesCorrect', this.changesCorrect));
    this.reviewerService.editorRevisionReview(this.taskId, formfields).subscribe(res => {
      this.toastr.success('Revision submitted');
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
