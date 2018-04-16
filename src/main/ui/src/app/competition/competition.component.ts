import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, ParamMap } from '@angular/router';
import { LeaguesService } from '../leagues.service';

@Component({
    selector: 'app-competition',
    template: `
    <div class="col-md-6 col-md-offset-3" >
        <h2>Competitione {{competitionShortName}} </h2>
       
        <div><button [disabled]="loading" class="btn btn-primary" (click) = "downloadSeasonFromWeb()"> Scarica le formazioni inserite </button></div>
        <div><button [disabled]="loading" class="btn btn-primary" (click) = "calculateSeasonResult()"> Ricalcola i risultati sulla base di quanto scaricato </button></div>
        <div><button [disabled]="loading" class="btn btn-primary" (click) = "calculateRealStats()"> Calcola le statistiche </button></div>
        <div><button [disabled]="loading" class="btn btn-primary" (click) = "retrieveStats()"> Recupera le statistiche appena calcolate</button></div>

       
        <div class="alert alert-danger" *ngIf="errorMessage">
            <strong>{{errorMessage}}</strong>
        </div>
  
        <div class="alert alert-success" *ngIf="successMessage">
            <strong>{{successMessage}}</strong>
        </div>
  
        <button class="btn btn-primary"(click) = "backToCompetitions()"> Torna alle competizioni </button>
       
    </div>

    `,
    styles: []
})
export class CompetitionComponent implements OnInit {
    loading = false;
    leagueShortName = null;
    competitionShortName = null;
    errorMessage = null;
    successMessage = null;
    model = {
        leagueShortName: null,
        competitionShortName: null
    };


    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private leagueService: LeaguesService,

    ) { }



    ngOnInit() {
        this.route.paramMap.subscribe((params: ParamMap) => {
            let url1 = params.get('competition');
            this.competitionShortName = url1;

            let url2 = params.get('league');
            this.leagueShortName = url2;

            this.model = {
                leagueShortName: this.leagueShortName,
                competitionShortName: this.competitionShortName
            }

            this.retrieveStats();



        });

    }



    retrieveStats(){
        this.loading = true;
        this.leagueService.retrieveAllRankings(this.model).subscribe( data => {
            this.successMessage = "Statistiche recuperate";
            this.errorMessage = null;
            this.loading = false;
        },

        error => {
            this.successMessage = null;
            this.errorMessage = "Devi ancora calcolare le statistiche";
            this.loading = false;
        });
    }

    downloadSeasonFromWeb() {
        this.loading = true;
        this.leagueService.downloadSeasonFromWeb(this.model)
            .subscribe(
                data => {
                    this.successMessage = "I dati delle giornate mancanti sono stati scaricati";
                    this.errorMessage = null;
                    this.loading = false;
                },

                error => {
                    this.successMessage = null;
                    this.errorMessage = "Errore di comunicazione col server 1";
                    this.loading = false;
                });

        this.errorMessage = null;
    }


    calculateSeasonResult() {
        this.loading = true;
        this.leagueService.calculateSeasonResult(this.model)
            .subscribe(
                data => {
                    this.successMessage = "Il ricalcolo dei risultati della stagione è stato eseguito";
                    this.errorMessage = null;
                    this.loading = false;
                },

                error => {
                    this.successMessage = null;
                    this.errorMessage = "Errore di comunicazione col server 2";
                    this.loading = false;
                });

        this.errorMessage = null;
    }

    calculateRealStats() {
        this.loading = true;
        this.leagueService.calculateRealStats(this.model)
            .subscribe(
                data => {
                    this.successMessage = "Dati pronti";
                    this.errorMessage = null;
                    this.loading = false;
                },

                error => {
                    this.successMessage = null;
                    this.errorMessage = "Errore di comunicazione col server 3";
                    this.loading = false;
                });

        this.errorMessage = null;
    }

   

    backToCompetitions() {
        this.router.navigate(['/competitions/', this.leagueShortName])

    }


}
