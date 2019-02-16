import { Component, OnInit } from '@angular/core';
import { ReviewerService } from 'src/app/core/http/reviewer.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormField } from 'src/app/shared/model/form-field.model';
import { VariableValue } from 'src/app/shared/model/variable-value.model';

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
    private router: Router) { }

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
    this.reviewerService.postReview(this.taskId, formfields).subscribe(res => { });
  }

  createFormField(key: string, value: any): FormField {
    const formField = new FormField();
    formField.name = key;
    formField.value = new VariableValue();
    formField.value.value = value;

    return formField;
  }

}
