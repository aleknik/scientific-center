import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/shared/model/user.model';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from 'src/app/core/http/auth.service';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css']
})
export class SigninComponent implements OnInit {

  user: User = new User();

  constructor(private authService: AuthService,
    private router: Router,
    private toastr: ToastrService) { }

  ngOnInit() {
  }

  signin() {
    this.authService.authenticate(this.user).subscribe(
      response => {
        this.toastr.success(`Welcome ${this.user.email}`);
        this.router.navigateByUrl('');
      },
      err => { });
  }

}
