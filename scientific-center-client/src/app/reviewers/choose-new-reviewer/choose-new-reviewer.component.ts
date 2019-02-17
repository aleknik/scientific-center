import { Component, OnInit } from '@angular/core';
import { ReviewerSearchResult } from 'src/app/shared/model/reviewer-search-result.model';
import { ReviewerQuery } from 'src/app/shared/model/reviewer-query.model';
import { ReviewerService } from 'src/app/core/http/reviewer.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { PaperService } from 'src/app/core/http/paper.service';

@Component({
  selector: 'app-choose-new-reviewer',
  templateUrl: './choose-new-reviewer.component.html',
  styleUrls: ['./choose-new-reviewer.component.css']
})
export class ChooseNewReviewerComponent implements OnInit {

  reviewers = new Array<ReviewerSearchResult>();

  query = new ReviewerQuery();

  choosenReviewers = new Array<ReviewerSearchResult>();
  date: string;

  taskId: string;

  constructor(private reviewerService: ReviewerService,
    private route: ActivatedRoute,
    private toastrService: ToastrService,
    private paperService: PaperService,
    private router: Router) { }

  ngOnInit() {
    this.query.includeScienceField = true;
    this.route.params.subscribe(params => {
      this.taskId = params['taskId'];
      this.getPaper();
    });
  }
  getPaper(): any {
    this.paperService.findByTaskId(this.taskId).subscribe(res => {
      this.query.paperId = res.id;
    })
  }

  search() {
    this.reviewerService.searchPapers(this.query).subscribe(res => {
      this.reviewers = res;
      this.toastrService.success("Search completed");
    });
  }

  addReviewer(reviewer: ReviewerSearchResult) {
    if (!this.choosenReviewers.some(r => r.id === reviewer.id) && this.choosenReviewers.length === 0) {
      this.choosenReviewers.push(reviewer);
    }
  }

  removeReviewer(reviewer: ReviewerSearchResult) {
    const index: number = this.choosenReviewers.indexOf(reviewer);
    if (index !== -1) {
      this.choosenReviewers.splice(index, 1);
    }
  }

  submitReviewers() {
    this.paperService.addReviewer(this.taskId, this.choosenReviewers[0], `PT${this.date}S`).subscribe(res => {
      this.toastrService.success("Reviewer submitted");
      this.router.navigate(['tasks']);
    });
  }
}
