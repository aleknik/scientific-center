import { Component, OnInit } from '@angular/core';
import { PaperService } from 'src/app/core/http/paper.service';
import { ToastrService } from 'ngx-toastr';
import { ActivatedRoute, Router } from '@angular/router';
import { Review } from 'src/app/shared/model/review.model';

@Component({
  selector: 'app-revision',
  templateUrl: './revision.component.html',
  styleUrls: ['./revision.component.css']
})
export class RevisionComponent implements OnInit {

  file: File;
  taskId: string;
  reviews = new Array<Review>();
  message: string;

  constructor(private paperService: PaperService,
    private toastr: ToastrService,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.taskId = params['taskId'];
      this.getReviews();
    });
  }

  fileChange(event) {
    this.file = event.target.files[0];
  }

  submit() {
    this.paperService.resubmit(this.taskId, this.file, this.reviews).subscribe(result => {
      this.toastr.success('Revision submitted');
      this.router.navigate(['tasks']);
    });
  }

  getReviews(): any {
    this.paperService.getReviews(this.taskId).subscribe(res => {
      this.reviews = res;
    })
  }

}
