import { Component, OnInit } from '@angular/core';
import { PaperSearchResult } from 'src/app/shared/model/paper-search-result.model';
import { PaperService } from 'src/app/core/http/paper.service';
import { PapersModule } from '../papers.module';
import { PaperQuery } from 'src/app/shared/model/paper-query.model';
import { query } from '@angular/animations';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-paper-list',
  templateUrl: './paper-list.component.html',
  styleUrls: ['./paper-list.component.css']
})
export class PaperListComponent implements OnInit {

  papers = new Array<PaperSearchResult>();

  queries = new Array<PaperQuery>();
  operators = ['AND', 'OR'];
  fields = ['title', 'journal', 'keywords', 'authorNames', 'content', 'scienceField'];

  constructor(private paperService: PaperService, private toastService: ToastrService) { }

  ngOnInit() {
    this.queries.push(new PaperQuery("", false, "AND", null))
    // this.getPapers();
  }

  search() {
    this.paperService.searchPapers(this.queries).subscribe(result => {
      this.papers = result;
      this.toastService.success("Seach finished")
    })
  }

  addQuery() {
    this.queries.push(new PaperQuery("", false, "AND", null))
  }

  removeQuery(query: PaperQuery) {
    const index: number = this.queries.indexOf(query);
    if (index !== -1) {
      this.queries.splice(index, 1);
    }
  }

}
