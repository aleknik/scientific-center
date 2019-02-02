import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/shared/model/user.model';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css']
})
export class SigninComponent implements OnInit {

  user: User = new User();

  constructor(
    private router: Router,
    private toastr: ToastrService) { }

  ngOnInit() {
  }

  signin() {
    this.toastr.success(`Welcome ${this.user.username}`);
    console.log("adsas");

    this.router.navigateByUrl('');
  }

}
