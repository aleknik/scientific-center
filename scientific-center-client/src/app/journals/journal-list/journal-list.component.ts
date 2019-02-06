import { Component, OnInit } from '@angular/core';
import { JournalService } from 'src/app/core/http/journal.service';
import { Journal } from 'src/app/shared/model/journal.model';

@Component({
  selector: 'app-journal-list',
  templateUrl: './journal-list.component.html',
  styleUrls: ['./journal-list.component.css']
})
export class JournalListComponent implements OnInit {

  journals = new Array<Journal>();

  constructor(private journalService: JournalService) { }

  ngOnInit() {
    this.getJournals();
  }
  getJournals(): any {
    this.journalService.findAll().subscribe(res => {
      this.journals = res;
    });
  }

}
