import { Component, OnInit } from '@angular/core';
import { Journal } from 'src/app/shared/model/journal.model';
import { JournalService } from 'src/app/core/http/journal.service';
import { ToastrService } from 'ngx-toastr';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-choose-journal',
  templateUrl: './choose-journal.component.html',
  styleUrls: ['./choose-journal.component.css']
})
export class ChooseJournalComponent implements OnInit {

  journal: Journal;
  taskId: string;

  journals = new Array<Journal>();

  constructor(private journalService: JournalService, private toastService: ToastrService,
    private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.taskId = params['taskId'];
      this.getJournals();
    });
  }
  getJournals(): any {
    this.journalService.findAll().subscribe(res => {
      this.journals = res;
    });
  }

  choose() {
    this.journalService.chooseJournal(this.taskId, this.journal).subscribe(res => {
      this.toastService.success("Journal choosen");
    });
  }

}
