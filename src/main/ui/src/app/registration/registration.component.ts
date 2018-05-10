import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthenticationService } from '../authentication.service';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-registration',
  template: `

  <div class="col-md-6 col-md-offset-3">
    <!--<h2>Registrazione</h2>-->
    <form name="form" (ngSubmit)="f.form.valid && register()" #f="ngForm" novalidate>
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
        <!--<div class="form-group" [ngClass]="{ 'has-error': f.submitted && !firstname.valid }">
            <label for="firstname">FirstName</label>
            <input type="text" class="form-control" name="firstname" [(ngModel)]="model.firstname" #firstname="ngModel" required />
            <div *ngIf="f.submitted && !firstname.valid" class="help-block">Firstname is required</div>
        </div>
        <div class="form-group" [ngClass]="{ 'has-error': f.submitted && !lastname.valid }">
            <label for="lastname">Lastname</label>
            <input type="text" class="form-control" name="lastname" [(ngModel)]="model.lastname" #lastname="ngModel" required />
            <div *ngIf="f.submitted && !lastname.valid" class="help-block">Lastname is required</div>
        </div>
        <div class="form-group" [ngClass]="{ 'has-error': f.submitted && !email.valid }">
            <label for="email">Email</label>
            <input type="email" class="form-control" name="email" [(ngModel)]="model.email" #email="ngModel" required>
            <div *ngIf="f.submitted && !email.valid" class="help-block">Email is required</div>
  
          
          
        </div>-->

       
       
        <div class="form-group">
            <button [disabled]="loading" class="btn btn-primary">Register</button>
        </div>

        <div class="alert alert-danger" *ngIf="errorMessage">
            <strong>{{errorMessage}}</strong>
        </div>

        <div class="alert alert-success" *ngIf="successMessage">
            <strong>{{successMessage}}</strong>
        </div>

        <button  *ngIf="successMessage" class="btn btn-primary btn-block" (click) = "login()"> Vai al login </button>

    </form>
  </div>
  

  `,
  providers: [AuthenticationService],
  
  styles: []
})

export class RegistrationComponent implements OnInit {
  model: any = {};
  loading = false;
  
  errorMessage = null;
  successMessage = null;

  constructor(
      private router: Router,
      private authenticationService: AuthenticationService
  ) { }

  ngOnInit() {

  }

  login(){
    this.router.navigate(['/login']);
  }

  register() {
      this.loading = true;
      this.model.username = this.model.username.toUpperCase();
      this.authenticationService.create(this.model.username, this.model.password,this.model.firstname, this.model.lastname, this.model.email )
          .subscribe(
              data => {
                  //this.alertService.success('Registration successful', true);
                  this.successMessage = "Registrazione eseguita";
                  this.errorMessage = null;
                
              },
              error => {
                  //this.alertService.error(error);
                  this.successMessage = null;
                  this.errorMessage = "Username gia' utilizzato";
                  this.loading = false;
              });
  }
}
