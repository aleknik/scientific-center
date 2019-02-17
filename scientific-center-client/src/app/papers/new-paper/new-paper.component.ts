import { Component, OnInit } from '@angular/core';
import { Paper } from 'src/app/shared/model/paper.model';
import { PaperService } from 'src/app/core/http/paper.service';
import { ToastrService } from 'ngx-toastr';
import { Router, ActivatedRoute } from '@angular/router';
import { Author } from 'src/app/shared/model/author.model';
import { ScienceFieldService } from 'src/app/core/http/science-field.service';
import { ScienceField } from 'src/app/shared/model/science-field.model';
import { ReviewerService } from 'src/app/core/http/reviewer.service';
import { Reviewer } from 'src/app/shared/model/reviewer.model';

@Component({
  selector: 'app-new-paper',
  templateUrl: './new-paper.component.html',
  styleUrls: ['./new-paper.component.css']
})
export class NewPaperComponent implements OnInit {

  paper: Paper = new Paper()
  file: File;
  scienceFields = new Array<ScienceField>();
  coauthor = new Author();

  taskId: string;

  constructor(private paperService: PaperService,
    private scienceFieldService: ScienceFieldService,
    private reviewerService: ReviewerService,
    private toastr: ToastrService,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit() {
    this.paper.coauthors = new Array<Author>()
    this.route.params.subscribe(params => {
      this.taskId = params['taskId'];
      this.getScienceFields();
      this.getReviewers();
    });
  }

  getReviewers() {
    this.reviewerService.findAll().subscribe(res => {
      this.paper.reviewers = res;
    });
  }

  getScienceFields() {
    this.scienceFieldService.findAll().subscribe(res => {
      this.scienceFields = res;
    });
  }

  fileChange(event) {
    this.file = event.target.files[0];
  }

  create() {
    this.paperService.createPaper(this.taskId, this.paper, this.file).subscribe(result => {
      this.toastr.success('Paper submitted');
      this.router.navigate(['tasks']);
    });
  }

  AddCoauthor() {
    this.paper.coauthors.push({ ...this.coauthor });
  }

  removeCoauthor(coauthor: Author) {
    const index: number = this.paper.coauthors.indexOf(coauthor);
    if (index !== -1) {
      this.paper.coauthors.splice(index, 1);
    }
  }

  removeReviewer(reviewer: Reviewer) {
    const index: number = this.paper.reviewers.indexOf(reviewer);
    if (index !== -1) {
      this.paper.reviewers.splice(index, 1);
    }
  }

}
