import { Component, OnInit } from '@angular/core';
import { PaperService } from 'src/app/core/http/paper.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FormField } from 'src/app/shared/model/form-field.model';
import { VariableValue } from 'src/app/shared/model/variable-value.model';

@Component({
  selector: 'app-paper-relevant',
  templateUrl: './paper-relevant.component.html',
  styleUrls: ['./paper-relevant.component.css']
})
export class PaperRelevantComponent implements OnInit {

  taskId: string;
  isRelevant = false;
  isFormated = false;
  message: string;

  constructor(private paperService: PaperService,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.taskId = params['taskId'];
    });
  }

  submit() {
    const formfields = new Array<FormField>();
    formfields.push(this.createFormField('paperRelevant', this.isRelevant));
    formfields.push(this.createFormField('properlyFormatted', this.isFormated));
    formfields.push(this.createFormField('message', this.message));
    this.paperService.postRelevant(this.taskId, formfields).subscribe(res => { });
  }

  createFormField(key: string, value: any): FormField {
    const formField = new FormField();
    formField.name = key;
    formField.value = new VariableValue();
    formField.value.value = value;

    return formField;
  }

}
