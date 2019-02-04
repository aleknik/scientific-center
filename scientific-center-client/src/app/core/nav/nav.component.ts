import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../http/auth.service';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})
export class NavComponent implements OnInit {

  isCollapsed = true;

  constructor(private authService: AuthService,
    private router: Router) { }


  ngOnInit() {
  }

  isAuthenticated(): boolean {
    return this.authService.isAuthenticated();
  }


  signout() {
    this.authService.signout();
    this.router.navigateByUrl('signin');
  }

}
