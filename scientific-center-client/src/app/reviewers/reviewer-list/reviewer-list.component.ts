import { Component, OnInit } from '@angular/core';
import { ReviewerSearchResult } from 'src/app/shared/model/reviewer-search-result.model';
import { ReviewerService } from 'src/app/core/http/reviewer.service';
import { ReviewerQuery } from 'src/app/shared/model/reviewer-query.model';
import { ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { PaperService } from 'src/app/core/http/paper.service';

@Component({
  selector: 'app-reviewer-list',
  templateUrl: './reviewer-list.component.html',
  styleUrls: ['./reviewer-list.component.css']
})
export class ReviewerListComponent implements OnInit {

  reviewers = new Array<ReviewerSearchResult>();

  query = new ReviewerQuery();

  choosenReviewers = new Array<ReviewerSearchResult>();

  taskId: string;

  constructor(private reviewerService: ReviewerService,
    private route: ActivatedRoute,
    private toastrService: ToastrService,
    private paperService: PaperService) { }

  ngOnInit() {
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
    if (!this.choosenReviewers.some(r => r.id === reviewer.id)) {
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
    this.paperService.addReviewers(this.taskId, this.choosenReviewers).subscribe(res => {
      this.toastrService.success("Reviewers submitted");
    });
  }
}
