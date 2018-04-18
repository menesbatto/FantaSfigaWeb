import { Component, OnInit } from '@angular/core';
import { LeaguesService } from '../leagues.service';
import { Router } from '@angular/router';

@Component({
    selector: 'app-leagues',
    template: `

    <div class="col-md-6 col-md-offset-3" >
      <h3>Le tue leghe</h3>
      
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
        <strong>{{loadingMessage}}</strong>
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



    ngOnInit() {
        this.retrieveLeagues();
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
        this.loadingMessage = "Download delle regole in corso...";
        this.errorMessage = null;


        this.leagueService.downloadRules(league.shortName)
            .subscribe(
                data => {
                    this.loading = false;
                    this.loadingMessage = null;
                    this.errorMessage = null;
                    this.successMessage = "Le regole della Lega '" + league.name + "' sono state scaricate."
                    league.rulesDownloaded = true;
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
