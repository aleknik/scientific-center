import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../http/auth.service';
import { AUTHOR, EDITOR } from '../util/constants';
import { PaperService } from '../http/paper.service';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})
export class NavComponent implements OnInit {

  isCollapsed = true;

  constructor(private authService: AuthService,
    private router: Router,
    private paperService: PaperService) { }


  ngOnInit() {
  }

  isAuthenticated(): boolean {
    return this.authService.isAuthenticated();
  }

  isAuthor(): boolean {
    return this.authService.checkPermission(AUTHOR);
  }

  isEditor(): boolean {
    return this.authService.checkPermission(EDITOR);
  }

  submitPaper() {
    this.paperService.startPaperProcess().subscribe(res => {
      this.router.navigate(['tasks']);
    });
  }

  signout() {
    this.authService.signout();
    this.router.navigateByUrl('signin');
  }

}
