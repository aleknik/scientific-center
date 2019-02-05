import { Component, OnInit } from '@angular/core';
import { PaperSearchResult } from 'src/app/shared/model/paper-search-result.model';
import { PaperService } from 'src/app/core/http/paper.service';
import { PapersModule } from '../papers.module';

@Component({
  selector: 'app-paper-list',
  templateUrl: './paper-list.component.html',
  styleUrls: ['./paper-list.component.css']
})
export class PaperListComponent implements OnInit {

  papers = new Array<PaperSearchResult>();

  constructor(private paperService: PaperService) { }

  ngOnInit() {
    this.getPapers();
  }

  getPapers() {
    this.paperService.searchPapers().subscribe(result => {
      this.papers = result;
    })
  }

}
