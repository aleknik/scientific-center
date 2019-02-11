import { Component, OnInit } from '@angular/core';
import { ReviewerSearchResult } from 'src/app/shared/model/reviewer-search-resutl.model';
import { ReviewerService } from 'src/app/core/http/reviewer.service';
import { ReviewerQuery } from 'src/app/shared/model/reviewer-query.model';
import { ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-reviewer-list',
  templateUrl: './reviewer-list.component.html',
  styleUrls: ['./reviewer-list.component.css']
})
export class ReviewerListComponent implements OnInit {

  reviewers = new Array<ReviewerSearchResult>();

  query = new ReviewerQuery();

  constructor(private reviewerService: ReviewerService,
    private route: ActivatedRoute,
    private toastrService: ToastrService) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.query.paperId = params['id'];
    });
  }

  search() {
    this.reviewerService.searchPapers(this.query).subscribe(res => {
      this.reviewers = res;
      this.toastrService.success("Search completed");
    });
  }

}
