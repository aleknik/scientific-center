import { Component, OnInit } from '@angular/core';
import { Paper } from 'src/app/shared/model/paper.model';
import { PaperService } from 'src/app/core/http/paper.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { Author } from 'src/app/shared/model/author.model';

@Component({
  selector: 'app-new-paper',
  templateUrl: './new-paper.component.html',
  styleUrls: ['./new-paper.component.css']
})
export class NewPaperComponent implements OnInit {

  paper: Paper = new Paper()
  file: File;
  scienceFields = ['Pizza', 'Pasta', 'Parmesan'];
  coauthor = new Author();

  constructor(private paperService: PaperService,
    private toastr: ToastrService,
    private router: Router) { }

  ngOnInit() {
    this.paper.coauthors = new Array<Author>()
  }

  fileChange(event) {
    this.file = event.target.files[0]
  }


  create() {
    this.paperService.createPaper(this.paper, this.file).subscribe(result => {
      this.toastr.success('Paper created');
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

}
