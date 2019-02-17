import { Component, OnInit } from '@angular/core';
import { PaperService } from 'src/app/core/http/paper.service';
import { ToastrService } from 'ngx-toastr';
import { ActivatedRoute, Router } from '@angular/router';
import { Paper } from 'src/app/shared/model/paper.model';

@Component({
  selector: 'app-format',
  templateUrl: './format.component.html',
  styleUrls: ['./format.component.css']
})
export class FormatComponent implements OnInit {

  file: File;
  taskId: string;
  paper: Paper;
  message: string;

  constructor(private paperService: PaperService,
    private toastr: ToastrService,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.taskId = params['taskId'];
      this.getData();
    });
  }
  getData(): any {
    this.paperService.getFormatData(this.taskId).subscribe(res => {
      this.paper = res.paper;
      this.message = res.message;
    });
  }

  fileChange(event) {
    this.file = event.target.files[0];
  }

  submit() {
    this.paperService.resubmit(this.taskId, this.file).subscribe(result => {
      this.toastr.success('Formated paper submitted');
    });
  }
}
