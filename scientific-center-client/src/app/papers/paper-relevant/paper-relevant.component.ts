import { Component, OnInit } from '@angular/core';
import { PaperService } from 'src/app/core/http/paper.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FormField } from 'src/app/shared/model/form-field.model';
import { VariableValue } from 'src/app/shared/model/variable-value.model';
import * as FileSaver from "file-saver"
import { Paper } from 'src/app/shared/model/paper.model';
import { ToastrService } from 'ngx-toastr';

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
  date: string;

  paper = new Paper();

  constructor(private paperService: PaperService,
    private route: ActivatedRoute,
    private router: Router,
    private toastr: ToastrService) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.taskId = params['taskId'];
      this.getPaper();
    });
  }
  getPaper(): any {
    this.paperService.findByTaskId(this.taskId).subscribe(res => {
      this.paper = res;
    });
  }

  submit() {
    const isoDate = this.date ? new Date(this.date).toISOString() : null;
    const formfields = new Array<FormField>();
    formfields.push(this.createFormField('paperRelevant', this.isRelevant));
    formfields.push(this.createFormField('properlyFormatted', this.isFormated));
    formfields.push(this.createFormField('message', this.message));
    formfields.push(this.createFormField('date', isoDate));
    this.paperService.postRelevant(this.taskId, formfields).subscribe(res => {
      this.toastr.success('Paper submitted');
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
