import { Component, OnInit } from '@angular/core';
import { ReviewerService } from 'src/app/core/http/reviewer.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormField } from 'src/app/shared/model/form-field.model';
import { VariableValue } from 'src/app/shared/model/variable-value.model';

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
    private router: Router) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.taskId = params['taskId'];
    });
  }

  submit() {
    const formfields = new Array<FormField>();
    formfields.push(this.createFormField('changesCorrect', this.changesCorrect));
    this.reviewerService.editorRevisionReview(this.taskId, formfields).subscribe(res => { });
  }

  createFormField(key: string, value: any): FormField {
    const formField = new FormField();
    formField.name = key;
    formField.value = new VariableValue();
    formField.value.value = value;

    return formField;
  }

}
