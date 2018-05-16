import { Component, OnInit } from '@angular/core';
import { LeaguesService } from '../leagues.service';
import { Router } from '@angular/router';
import { Observable, Subscription} from 'rxjs';
import { nullSafeIsEquivalent } from '@angular/compiler/src/output/output_ast';


@Component({
    selector: 'app-leagues',
    template: `

    <div>
        <!--<h3 align = "center">Le tue leghe </h3>-->
        
        <div class="table-responsive">
            <table class="table">
            <tr *ngFor = "let league of leagueList" >
                <td> {{league.name}} </td>
                <td> <button  class="btn btn-primary" [disabled]="loading || league.competitionsDownloaded"  (click)="downloadCompetitions(league)"> Scarica </button>  </td>
                <td> <button  class="btn btn-primary" [disabled]="loading || !league.competitionsDownloaded || !league.rulesDownloaded"  (click) = "goToCompetitions(league)"> Vai </button> </td>
            <tr>
            </table>
        </div> 
        
      <div class="alert alert-success" *ngIf="successMessage">
          <strong>{{successMessage}}</strong>
      </div>

      <div class="alert alert-success" *ngIf="loadingMessage" >
        <strong [innerHTML]= "loadingMessage"></strong>
      </div>

      <div class="alert alert-danger" *ngIf="errorMessage">
        <strong>{{errorMessage}}</strong>
      </div>
  </div>



 

  `,
    styles: []
})
export class LeaguesComponent implements OnInit {

    constructor(
        private leagueService: LeaguesService,
        private router: Router,
    ) { }

    loading = null;
    leagueList = [];
    errorMessage = null;
    loadingMessage = null;
    successMessage = null;

    sub:Subscription;

    ngOnInit() {
        this.retrieveLeagues();
       
    }
    i = 0;
    messages = [
        "<p>Scaricate regole Bonus/Malus</p>",
        "<p>Scaricate regole Fonte dati</p>",
        "<p>Scaricate regole Sostituzioni</p>",
        "<p>Scaricate regole Punteggi</p>",
        "<p>Scaricate regole Modificatori</p>",
        "<p>Scaricate regole Competizione</p>",
        "<p>Salvataggio...</p>"
    ];

    startFakeServerUpdater(){
       


        this.sub = Observable.interval(3600)
            .subscribe(
                data => {
                    if (this.loadingMessage == null)
                        this.loadingMessage="";
                    if (this.i < this.messages.length ){
                        this.loadingMessage += this.messages[this.i];
                        this.i++;
                    }
                },
                error => {
                    console.log(error);
                }) ;
    }
    
    stopFakeServerUpdater(){
        this.sub.unsubscribe();
    }

    goToCompetitions(league) {
        this.router.navigate(['/competitions', league.shortName])
    }





    downloadCompetitions(league) {
        this.loading = true;
        this.errorMessage = null;
        this.loadingMessage = "Stiamo scaricando le competizioni delle lega selezionata...";

        this.leagueService.downloadCompetitions(league.shortName)
            .subscribe(
                data => {
                    this.loading = false;
                    this.successMessage = "Le competizioni della Lega " + league.name + " sono state scaricate. ";
                    this.downloadCompetitionsCallback(league);

                },
                error => {
                    this.loading = false;
                    this.loadingMessage = null;
                    this.successMessage = null;
                    this.errorMessage = "Errore del server";
                })
    }


    retrieveLeagues() {
        this.loading = true;
        this.leagueService.retrieveLeagues()
            .subscribe(
                data => {
                    this.loading = false;
                    this.leagueList = data
                },

                error => {
                    this.loading = false;
                    this.errorMessage = "Errore di comunicazione col server"
                });

        this.errorMessage = null;
    }

    onSelect(data) {
        console.log(data + " onSelect()")
    }

    downloadCompetitionsCallback(league) {
        this.loading = true;
        league.competitionsDownloaded = true;
        this.loadingMessage = "<p>Download delle regole in corso (impiega quale secondo)...</p>";
        this.errorMessage = null;
        this.startFakeServerUpdater();

        this.leagueService.downloadRules(league.shortName)
            .subscribe(
                data => {
                    this.loading = false;
                    this.loadingMessage = null;
                    this.errorMessage = null;
                    this.successMessage = "Le regole della Lega '" + league.name + "' sono state scaricate."
                    league.rulesDownloaded = true;
                    this.stopFakeServerUpdater();
                },
                error => {
                    this.loading = false;
                    this.loadingMessage = null;
                    this.successMessage = null;
                    this.errorMessage = "Problemi durante il download delle regole della lega '" + league.name + "'";
                    league.rulesDownloaded = false;
                })


    }

}
