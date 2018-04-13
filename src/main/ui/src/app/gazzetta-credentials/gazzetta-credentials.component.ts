import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthenticationService } from '../authentication.service';
import { NgForm } from '@angular/forms';
import { LeaguesService } from '../leagues.service';

@Component({
  selector: 'app-gazzetta-credentials',
  template: `

  <div class="col-md-6 col-md-offset-3">
    <h2>Credenziali FantaGazzetta</h2>
    <form name="form" (ngSubmit)="f.form.valid && saveGazzettaCredentials()" #f="ngForm" novalidate>
        <div class="form-group" [ngClass]="{ 'has-error': f.submitted && !gazzettaUsername.valid }">
            <label for="gazzettaUsername">Username Fantagazzetta</label>
            <input type="text" class="form-control" name="gazzettaUsername" [(ngModel)]="model.gazzettaUsername" #gazzettaUsername="ngModel" required />
            <div *ngIf="f.submitted && !gazzettaUsername.valid" class="help-block">Devi inserire lo Username di Fantagazzetta</div>
        </div>
        <div class="form-group" [ngClass]="{ 'has-error': f.submitted && !gazzettaPassword.valid }">
            <label for="gazzettaPassword">Password Fantagazzetta</label>
            <input type="password" class="form-control" name="gazzettaPassword" [(ngModel)]="model.gazzettaPassword" #gazzettaPassword="ngModel" required />
            <div *ngIf="f.submitted && !gazzettaPassword.valid" class="help-block">Devi inserire la Password di Fantagazzetta</div>
        </div>
        <div class="form-group">
            <button [disabled]="loading" class="btn btn-primary">Salva</button>
        </div>

        <div class="alert alert-danger" *ngIf="errorMessage">
            <strong>{{errorMessage}}</strong>
        </div>

        <div class="alert alert-success" *ngIf="loadingMessage">
            <strong>{{loadingMessage}}</strong>
        </div>

        <div class="alert alert-success" *ngIf="successMessageLeague">
            <strong>{{successMessageLeague}}</strong>
        </div>

        <button  *ngIf="successMessageLeague" class="btn btn-primary btn-block" (click) = "goToLeagues()"> VAI ALLE TUE LEGHE </button>

    </form>
  </div>
  

  `,
  providers: [AuthenticationService],
  
  styles: []
})

export class GazzettaCredentialsComponent implements OnInit {
  model: any = {};
  loading = false;

  errorMessage = null;
  loadingMessage = null;  
  successMessageLeague = null;


  constructor(
      private router: Router,
      private authenticationService: AuthenticationService,
      private leagueService: LeaguesService
  ) { }

  ngOnInit() {

  }

  goToLeagues(){
    this.router.navigate(['/leagues']);
  }

  saveGazzettaCredentials() {
      this.loading = true;
      this.authenticationService.saveGazzettaCredentials(this.model.gazzettaUsername, this.model.gazzettaPassword )
          .subscribe(
              data => {
               
                this.saveGazzettaCredentialsCallback();


              },
              error => {
                  //this.alertService.error(error);
                  //this.loading = false;
                  this.loadingMessage = null;
                  this.errorMessage = this.errorMessage = "Problemi di comunicazione col server";
              })
              
              
          ;



    }

    saveGazzettaCredentialsCallback(){   
      this.errorMessage = null;
      this.loadingMessage = "Stiamo scaricando le tue leghe...";
      this.leagueService.downloadLeagues()
      .subscribe(
        data => {
          this.loadingMessage = null;
          this.successMessageLeague = "Le tue leghe sono state scaricate"


        },
        error => {
            //this.alertService.error(error);
            this.loading = false;
            this.loadingMessage = null;
            this.successMessageLeague = null;
            this.errorMessage = "Non siamo riusciti ad eseguire il login su Fantagazzetta con le credenziali fornite. Riprova l'inserimento";
        })
        
        
      ;
    }
}


