import { Component, OnInit } from '@angular/core';
import { AdminService } from '../admin.service';
import { AuthenticationService } from '../authentication.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin',
  template: `
<div>
    <div class="container nobor">
        <div class="row">
            <div class="col-lg-10 col-md-10 col-sm-10 col-xs-10">
                Scarica Voti
            </div>
            <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
                <button [disabled]="loading" class="btn btn-primary" (click) = "downloadVotes()"> Vai </button>
            </div>
        </div>
    </div>

    <div class="container nobor">
        <div class="row">
            <div class="col-lg-10 col-md-10 col-sm-10 col-xs-10">
                Ripulisci voti in seguito al completamento di una giornata dopo il recupero
            </div>
            <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
                <button [disabled]="loading" class="btn btn-primary" (click) = "cleanVotes()"> Vai </button>
            </div>
        </div>
    </div>

    <div class="container nobor">
        <div class="row">
            <div class="col-lg-8 col-md-8 col-sm-8 col-xs-8">
                Crea Permutazioni
            </div>
            <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
                <input placeholder="# Giocatori" [(ngModel)]="playerNumber" class="form-control chk" type="number">
            </div>
            <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
                <button [disabled]="loading" class="btn btn-primary" (click) = "createPermutations(playerNumber)"> Vai </button>
            </div>
        </div>
    </div>

    <div class="container nobor">
        <div class="row">
            <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                Inserisci Rinvio
            </div>
            <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
                <input placeholder="Season Day" [(ngModel)]="postponement.seasonDay" class="form-control chk" type="number" required>
            </div>
            <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
                <input placeholder="Home Team" [(ngModel)]="postponement.homeTeam" class="form-control chk" type="text" required>
            </div>
            <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
                <input placeholder="Away team" [(ngModel)]="postponement.awayTeam" class="form-control chk" type="text" required>
            </div>
            <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
                <button [disabled]="loading" class="btn btn-primary" (click) = "insertPostponement(postponement)"> Vai </button>
            </div>
        </div>
    </div>


    <div class="container nobor">
        <div class="row">
            <div class="col-lg-8 col-md-8 col-sm-8 col-xs-8">
                Recupera rinvii
            </div>
            <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
                <button [disabled]="loading" class="btn btn-primary" (click) = "retrievePostponements()"> Mostra </button>
            </div>
            <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
                <button [disabled]="loading" class="btn btn-primary" (click) = "postponementList = null"> Nascondi </button>
            </div>
        </div>
    </div>

    <div class="table-responsive nobor" *ngIf="postponementList">
        <table class="table">
            <thead>
                <tr>
                    <th> Giornata </th>
                    <th> In casa </th>
                    <th> Fuori casa </th>
                    <th> Giocata </th>
                    
                <tr>
            </thead>
            <tbody>
                <tr *ngFor = "let postponement of postponementList" >
                    <td> {{postponement.seasonDay}} </td>
                    <td> {{postponement.homeTeam}} </td>
                    <td> {{postponement.awayTeam}} </td>
                    <td> {{postponement.played}} </td>
                    <td> <button  class="btn btn-primary" [disabled]="loading || postponement.played"  (click)="setPostponementPlayed(postponement)"> Giocato </button>  </td>
                    <td> <button  class="btn btn-primary" (click)="removePostponement(postponement)"> Rimuovi </button>  </td>
                <tr>
            </tbody>

        </table>
    </div> 




    


    <div class="alert alert-danger nobor" *ngIf="errorMessage">
        <strong>{{errorMessage}}</strong>
    </div>

    <div class="alert alert-success nobor" *ngIf="successMessage">
        <strong>{{successMessage}}</strong>
    </div>
</div>
  `,
  styles: []
})
export class AdminComponent implements OnInit {

     constructor(
          private adminService: AdminService,
          private authService: AuthenticationService,
          private router : Router
    ) { }

    ngOnInit() {
        let isAdmin = this.authService.isAdmin();
        if (!isAdmin){
            this.router.navigate(['/leagues']);
        }
    }


    successMessage = null;
    errorMessage = null;

    playerNumber=null;

    postponement = {};
    postponementList = null;

    downloadVotes = function(){
        this.adminService.downloadVotes()
        .subscribe(
            data => {
                this.successMessage = "I voti sono stati scaricati";
                this.errorMessage = null;
            },
            error => this.errorCallback());
    }

    cleanVotes = function(){
        this.adminService.cleanVotes()
        .subscribe(
            data => {
                this.successMessage = "Sono stati scaricati i voti della giornata conclusa con l'ultire recupero";
                this.errorMessage = null;
            },
            error => this.errorCallback());
    }

    insertPostponement = function(postponement){
        if (!postponement.homeTeam || !postponement.awayTeam || !postponement.seasonDay){
            this.errorMessage = "Inserisci i campi";
            return;
        }
        this.adminService.insertPostponement(postponement)
        .subscribe(
            data => {
                this.successMessage = "Rinvio inserito";
                this.errorMessage = null;
                this.postponement = {};
            },
            error => this.errorCallback());
    }

    removePostponement = function(postponement){
       this.adminService.removePostponement(postponement)
        .subscribe(
            data => {
                this.successMessage = "Rinvio eliminato";
                this.errorMessage = null;
                this.postponement = {};
                this.retrievePostponements();
            },
            error => this.errorCallback());
    }

    setPostponementPlayed = function(postponement){
        postponement.played = true;
        this.insertPostponement();
        this.retrievePostponements(); // c'è un errore non sfrutto le callback
    }
    

    retrievePostponements = function(){
        this.adminService.retrievePostponements()
        .subscribe(
            data => {
                this.postponementList = data;
                this.successMessage = "Rinvii recuperati";
                this.errorMessage = null;
             
            },
            error => this.errorCallback());
    }

   

    createPermutations = function(playerNumber){
        
        this.adminService.createPermutations(playerNumber)
        .subscribe(
            data => {
                this.successMessage = "Permutazioni create";
                this.errorMessage = null;
            },
            error => this.errorCallback());
    }



    errorCallback = function(){
        this.successMessage = null;
        this.errorMessage = "Errore di comunicazione col server"
    }


}
