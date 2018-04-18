import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, ParamMap } from '@angular/router';
import { LeaguesService } from '../leagues.service';

@Component({
    selector: 'app-competition',
    template: `
    <div class="col-md-8 col-md-offset-2" >
        <h2>Competitione {{competitionShortName}} </h2>
        <div class="btn-toolbar">
               <button [disabled]="loading" class="btn btn-primary btn-block" (click) = "downloadSeasonFromWeb()"> 1 - Scarica le formazioni inserite </button>
               <button [disabled]="loading" class="btn btn-primary btn-block" (click) = "calculateSeasonResult()"> 2 - Ricalcola i risultati sulla base di quanto scaricato </button>

               <button [disabled]="loading" class="btn btn-primary btn-block" (click) = "calculateRealStats(true)"> 3 - Calcola le classifica via FantaSfiga </button>
               <button [disabled]="loading" class="btn btn-primary btn-block" (click) = "calculateRealStats(false)"> 4 - Calcola le statistiche (qualche secondo)</button>
               <button [disabled]="loading" class="btn btn-primary btn-block" (click) = "retrieveStats()"> 5 - Recupera le statistiche appena calcolate</button>

        </div>

        <div class="alert alert-danger" *ngIf="errorMessage">
            <strong>{{errorMessage}}</strong>
        </div>
 
        <div class="alert alert-success" *ngIf="loadingMessage">
            <strong>{{loadingMessage}}</strong>
        </div>

        <div class="alert alert-success" *ngIf="successMessage">
            <strong>{{successMessage}}</strong>
        </div>

        
        <ng-template #rankingTable let-rankingIn="ranking">
        <div *ngIf= "rankingIn" >
            <table class="table table-striped">
                <thead class="thead-light">
                    <tr>
                        <th>Posizione</th>
                        <th>Squadra</th>
                        <th *ngIf= "rankingIn.rows[0].points">Punti</th>
                        <th *ngIf= "rankingIn.rows[0].scoredGoals">Gol Fatti</th>
                        <th *ngIf= "rankingIn.rows[0].takenGoals">Gol Subiti</th>
                        <th *ngIf= "rankingIn.rows[0].sumAllVotes">Somma punteggio</th>
                        <th *ngIf= "rankingIn.rows[0].positions">1</th>
                        <th *ngIf= "rankingIn.rows[0].positions">2</th>
                        <th *ngIf= "rankingIn.rows[0].positions">3</th>
                        <th *ngIf= "rankingIn.rows[0].positions">4</th>
                        <th *ngIf= "rankingIn.rows[0].positions">5</th>
                        <th *ngIf= "rankingIn.rows[0].positions">6</th>
                        <th *ngIf= "rankingIn.rows[0].positions">7</th>
                        <th *ngIf= "rankingIn.rows[0].positions">8</th>
                    </tr>
                </thead>
                <tbody>
                    <tr *ngFor = "let team of rankingIn.rows; index as i; ">
                        <td>{{i+1}}</td>
                        <td>{{team.name}}</td>
                        <td *ngIf= "team.points">{{team.points}}</td>
                        <td *ngIf= "team.scoredGoals">{{team.scoredGoals}}</td>
                        <td *ngIf= "team.takenGoals">{{team.takenGoals}}</td>
                        <td *ngIf= "team.sumAllVotes">{{team.sumAllVotes}}</td>
                        <td *ngIf= "team.positions">{{team.positions[0]}}</td>
                        <td *ngIf= "team.positions">{{team.positions[1]}}</td>
                        <td *ngIf= "team.positions">{{team.positions[2]}}</td>
                        <td *ngIf= "team.positions">{{team.positions[3]}}</td>
                        <td *ngIf= "team.positions">{{team.positions[4]}}</td>
                        <td *ngIf= "team.positions">{{team.positions[5]}}</td>
                        <td *ngIf= "team.positions">{{team.positions[6]}}</td>
                        <td *ngIf= "team.positions">{{team.positions[7]}}</td>
                        <td *ngIf= "team.positions">{{team.positions[8]}}</td>
                    </tr>
                </tbody>
            </table>
        </div>
        </ng-template>
              
        <h3> Classifica Reale </h3>
        <ng-container *ngTemplateOutlet="rankingTable;context:ctxRealRanking">
        </ng-container>

        <h3> Classifica Giusta </h3>
        <ng-container *ngTemplateOutlet="rankingTable;context:ctxFairRanking">
        </ng-container>

        <h3> Classifica Posizioni </h3>
        <ng-container *ngTemplateOutlet="rankingTable;context:ctxPositionRanking">
        </ng-container>








        <ng-template #estimateTemplate let-lessonsCounter="estimate">
            <div> Approximately {{lessonsCounter}} lessons ...</div>
        </ng-template>
        
        
        <ng-container *ngTemplateOutlet="estimateTemplate;context:ctx">
        </ng-container>

        <ng-container *ngTemplateOutlet="estimateTemplate;context:ctx2">
        </ng-container>








        
  
        <button class="btn btn-primary"(click) = "backToCompetitions()"> Torna alle competizioni </button>
       
    </div>

    `,
    styles: []
})
export class CompetitionComponent implements OnInit {
    totalEstimate = 10;
    ctx = { estimate: this.totalEstimate };
    ctx2 = { estimate: 20 };

    ctxRealRanking = { ranking: null };
    ctxFairRanking = { ranking: null };
    ctxPositionRanking = { ranking: null };


    loading = false;
    leagueShortName = null;
    competitionShortName = null;
    loadingMessage = null;
    errorMessage = null;
    successMessage = null;

    stats = null;

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



    retrieveStats() {
        this.loading = true;
        this.successMessage = null;
        this.errorMessage = null;
        this.loadingMessage = "Recupero Statistiche in corso...";

        this.leagueService.retrieveAllRankings(this.model).subscribe(data => {
            this.loadingMessage = null;
            this.successMessage = "Statistiche recuperate";
            this.errorMessage = null;
            this.loading = false;
            this.stats = data;
            this.ctxRealRanking.ranking = this.stats.realRanking;
            this.ctxFairRanking.ranking = this.stats.fairRanking;
            this.ctxPositionRanking.ranking = this.stats.positionRanking;

        },

            error => {
                this.loadingMessage = null;
                this.successMessage = null;
                this.errorMessage = "Devi ancora calcolare le statistiche";
                this.loading = false;
            });
    }

    downloadSeasonFromWeb() {
        this.loading = true;
        this.successMessage = null;
        this.errorMessage = null;
        this.loadingMessage = "Download delle informazioni delle giornate mancanti in corso...";

        this.leagueService.downloadSeasonFromWeb(this.model)
            .subscribe(
                data => {
                    this.loadingMessage = null;
                    this.successMessage = "I dati delle giornate mancanti sono stati scaricati";
                    this.errorMessage = null;
                    this.loading = false;
                },

                error => {
                    this.loadingMessage = null;
                    this.successMessage = null;
                    this.errorMessage = "Errore di comunicazione col server 1";
                    this.loading = false;
                });

        this.errorMessage = null;
    }


    calculateSeasonResult() {
        this.loading = true;
        this.successMessage = null;
        this.errorMessage = null;
        this.loadingMessage = "Ricalcolo dei risultati della stagione in corso...";

        this.leagueService.calculateSeasonResult(this.model)
            .subscribe(
                data => {
                    this.loadingMessage = null;
                    this.successMessage = "Il ricalcolo dei risultati della stagione è stato eseguito";
                    this.errorMessage = null;
                    this.loading = false;
                },

                error => {
                    this.loadingMessage = null;
                    this.successMessage = null;
                    this.errorMessage = "Errore di comunicazione col server 2";
                    this.loading = false;
                });

        this.errorMessage = null;
    }

    calculateRealStats(light) {
        this.loading = true;
        this.successMessage = null;
        this.errorMessage = null;
        this.loadingMessage = "Calcolo delle statistiche in corso...";
        let req = {
            light : light,
            competition : this.model
        };
        this.leagueService.calculateRealStats(req)
            .subscribe(
                data => {
                    this.loadingMessage = null;
                    this.successMessage = "Calcolo delle statistiche terminato. Dati pronti";
                    this.errorMessage = null;
                    this.loading = false;
                },

                error => {
                    this.loadingMessage = null;
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
