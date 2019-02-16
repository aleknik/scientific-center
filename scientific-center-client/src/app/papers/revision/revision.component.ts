import { Component, OnInit } from '@angular/core';
import { PaperService } from 'src/app/core/http/paper.service';
import { ToastrService } from 'ngx-toastr';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-revision',
  templateUrl: './revision.component.html',
  styleUrls: ['./revision.component.css']
})
export class RevisionComponent implements OnInit {

  file: File;
  taskId: string;

  constructor(private paperService: PaperService,
    private toastr: ToastrService,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.taskId = params['taskId'];
    });
  }

  fileChange(event) {
    this.file = event.target.files[0];
  }

  submit() {
    this.paperService.resubmit(this.taskId, this.file).subscribe(result => {
      this.toastr.success('Revision submitted');
    });
  }

}
