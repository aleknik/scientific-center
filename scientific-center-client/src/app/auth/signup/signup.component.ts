import { Component, OnInit } from '@angular/core';
import { AuthorService } from 'src/app/core/http/author.service';
import { FormField } from 'src/app/shared/model/form-field.model';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  formFields = new Array<FormField>();
  taskId: string;

  constructor(private authorService: AuthorService, private toastrService: ToastrService,
    private router: Router) { }

  ngOnInit() {
    this.authorService.getSignupForm().subscribe(res => {
      this.formFields = res.formFields;
      this.taskId = res.taskId;
    });
  }

  signup() {
    this.authorService.signup(this.taskId, this.formFields).subscribe(res => {
      this.toastrService.success("Registration request submitted");
      this.router.navigateByUrl('signin');
    });
  }

}
