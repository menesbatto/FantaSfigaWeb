import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthenticationService } from '../authentication.service';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-login',
  template:   `

    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <h2>Login</h2>
        <form name="form" (ngSubmit)="f.form.valid && login()" #f="ngForm" novalidate>
            <div class="form-group" [ngClass]="{ 'has-error': f.submitted && !username.valid }">
                <label for="username">Username</label>
                <input type="text" class="form-control" name="username" [(ngModel)]="model.username" #username="ngModel" required />
                <div *ngIf="f.submitted && !username.valid" class="help-block">Username is required</div>
            </div>
            <div class="form-group" [ngClass]="{ 'has-error': f.submitted && !password.valid }">
                <label for="password">Password</label>
                <input type="password" class="form-control" name="password" [(ngModel)]="model.password" #password="ngModel" required />
                <div *ngIf="f.submitted && !password.valid" class="help-block">Password is required</div>
            </div>
            <div class="form-group">
                <button [disabled]="loading" class="btn btn-primary">Login</button>
                <img *ngIf="loading" src="data:image/gif;base64,R0lGODlhEAAQAPIAAP///wAAAMLCwkJCQgAAAGJiYoKCgpKSkiH/C05FVFNDQVBFMi4wAwEAAAAh/hpDcmVhdGVkIHdpdGggYWpheGxvYWQuaW5mbwAh+QQJCgAAACwAAAAAEAAQAAADMwi63P4wyklrE2MIOggZnAdOmGYJRbExwroUmcG2LmDEwnHQLVsYOd2mBzkYDAdKa+dIAAAh+QQJCgAAACwAAAAAEAAQAAADNAi63P5OjCEgG4QMu7DmikRxQlFUYDEZIGBMRVsaqHwctXXf7WEYB4Ag1xjihkMZsiUkKhIAIfkECQoAAAAsAAAAABAAEAAAAzYIujIjK8pByJDMlFYvBoVjHA70GU7xSUJhmKtwHPAKzLO9HMaoKwJZ7Rf8AYPDDzKpZBqfvwQAIfkECQoAAAAsAAAAABAAEAAAAzMIumIlK8oyhpHsnFZfhYumCYUhDAQxRIdhHBGqRoKw0R8DYlJd8z0fMDgsGo/IpHI5TAAAIfkECQoAAAAsAAAAABAAEAAAAzIIunInK0rnZBTwGPNMgQwmdsNgXGJUlIWEuR5oWUIpz8pAEAMe6TwfwyYsGo/IpFKSAAAh+QQJCgAAACwAAAAAEAAQAAADMwi6IMKQORfjdOe82p4wGccc4CEuQradylesojEMBgsUc2G7sDX3lQGBMLAJibufbSlKAAAh+QQJCgAAACwAAAAAEAAQAAADMgi63P7wCRHZnFVdmgHu2nFwlWCI3WGc3TSWhUFGxTAUkGCbtgENBMJAEJsxgMLWzpEAACH5BAkKAAAALAAAAAAQABAAAAMyCLrc/jDKSatlQtScKdceCAjDII7HcQ4EMTCpyrCuUBjCYRgHVtqlAiB1YhiCnlsRkAAAOwAAAAAAAAAAAA==" />
                <a [routerLink]="['/registration']" class="btn btn-link">Registrazione</a>
            </div>
        </form>
        <div class="alert alert-danger" *ngIf="errorMessage">
            <strong>{{errorMessage}}</strong>
        </div>
    </div>
  
  `,
  providers: [AuthenticationService],
  styles: []
})
export class LoginComponent implements OnInit {

  model: any = {};
  loading = false;
  returnUrl: string;
  errorMessage = null;

  constructor(
      private route: ActivatedRoute,
      private router: Router,
      private authenticationService: AuthenticationService
    ) { }

  ngOnInit() {
      // reset login status
      this.authenticationService.logout();

      // get return url from route parameters or default to '/'
      this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }

  login() {
      this.loading = true;
      this.authenticationService.login(this.model.username, this.model.password)
          .subscribe(
              data => {
                  let isFirstTime = data.gazzettaUsername == null;
                  if (data.role=="ADMIN"){
                    this.router.navigate(["admin"]);
                  }
                  else if (isFirstTime){
                    this.router.navigate(["gazzettaCredentials"]);
                  }
                  else {
                    this.router.navigate(["leagues"]);
                  }
              },
              error => {
                  //this.alertService.error(error);
                  this.errorMessage = "Username o password errate";
                  this.loading = false;
              });
  }
}
