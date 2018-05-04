import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, ParamMap } from '@angular/router';
import { LeaguesService } from '../leagues.service';

@Component({
  selector: 'app-season',
  template: `
<div class="col-lg-8 col-md-offset-2" >
    <h2> Stagione di {{team}} </h2>
    






    <ng-template #rankingTable let-rankingIn="ranking">
        <div *ngIf= "rankingIn" >
            <table class="table table-striped">
                <thead class="thead-light">
                    <tr>
                        <th>Pos</th>
                        <th>Squadra</th>
                        <th *ngIf= "rankingIn.rows[0].points">Punti</th>
                        <th *ngIf= "rankingIn.rows[0].scoredGoals">Gol Fatti</th>
                        <th *ngIf= "rankingIn.rows[0].takenGoals">Gol Subiti</th>
                        <th *ngIf= "rankingIn.rows[0].sumAllVotes">Somma punteggio</th>
                    </tr>
                </thead>
                <tbody>
                    <tr *ngFor = "let team of rankingIn.rows; index as i; ">
                        <td>{{i+1}}</td>
                        <td>{{team.name}}</td>
                        <td *ngIf= "team.points">{{team.points | number:'0.0-3'}}</td>
                        <td *ngIf= "team.scoredGoals">{{team.scoredGoals}}</td>
                        <td *ngIf= "team.takenGoals">{{team.takenGoals}}</td>
                        <td *ngIf= "team.sumAllVotes">{{team.sumAllVotes}}</td>
                    </tr>
                </tbody>
            </table>
        </div>
        
        </ng-template>
    <div class="container" *ngIf="ctxRanking.ranking">
        <h1 align="center"> REALE </h1>

        <div class="row">
            <div class="col-lg-12">
                <h3> Classifica Reale </h3>
                <ng-container *ngTemplateOutlet="rankingTable;context:ctxRanking">
                </ng-container>
            </div>
        </div>
    </div>









    <ng-template #seasonTable let-seasonDayIn="seasonDay">
    <h3 align = "center">{{seasonDayIn.name}} ( Serie A {{seasonDayIn.serieANumber}} )</h3>
    <div *ngIf= "seasonDayIn" >
        <div   [ngClass]="{ 'c': match.homeTeam == team || match.awayTeam == team} " class="table-striped row" *ngFor = "let match of seasonDayIn.matches; index as i; ">
            <div class="col-lg-3 col-sm-3 right"><strong>{{match.homeTeam}}</strong></div>
            <div class="col-lg-2 col-sm-2 right">{{match.homeTeamResult.sumTotalPoints}}</div>
            <div class="col-lg-1 col-sm-1 right"><strong>{{match.homeTeamResult.goals}}</strong></div>
            <div class="col-lg-1 col-sm-1"><strong>{{match.awayTeamResult.goals}}</strong></div>
            <div class="col-lg-2 col-sm-2">{{match.awayTeamResult.sumTotalPoints}}</div>
            <div class="col-lg-3 col-sm-3"><strong>{{match.awayTeam}}</strong></div>
        </div> 




        <!--<table class="table table-striped">
            <thead class="thead-light">
                <tr>
                    <th>Home Team</th>
                    <th>Punti</th>
                    <th>Goal</th>
                    <th>Punti</th>
                    <th>Goal </th>
                    <th>Away Team</th>
                </tr>
            </thead>
            <tbody>
                <tr *ngFor = "let match of seasonDayIn.matches; index as i; ">
                    <td>{{match.homeTeam}}</td>
                    <td>{{match.homeTeamResult.sumTotalPoints}}</td>
                    <td>{{match.homeTeamResult.goals}}</td>
                    <td>{{match.awayTeamResult.goals}}</td>
                    <td>{{match.awayTeamResult.sumTotalPoints}}</td>
                    <td>{{match.awayTeam}}</td>
                </tr>
            </tbody>
        </table>-->
    </div>
    
    </ng-template>


    <!--
    <ng-template #seasonTable let-seasonDayIn="seasonDays[0]">
    <div *ngIf="ctxSeason.season">
        <ng-container *ngTemplateOutlet="seasonTable; context:ctxSeason.season">
        </ng-container>
    </div>-->

    <!--<div>
        <h3>All Templates</h3>
        <ul *ngFor="let number of numbers">
            <ng-container [ngTemplateOutlet]='number % 2 == 0 ? even_tpl : odd_tpl' [ngTemplateOutletContext]="{number:number}"></ng-container>
        </ul>
    </div>-->

    <div *ngIf="ctxSeason.season">
        <ul *ngFor="let seasonDay of ctxSeason.season.seasonDays">
            <ng-container [ngTemplateOutlet]='seasonTable' [ngTemplateOutletContext]="{seasonDay:seasonDay}"></ng-container>
        </ul>
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

    <button class="btn btn-primary btn-block" (click) = "goToCompetition('REAL')"> Torna alle statistiche </button>

</div>
  `,
  styles: []
})
export class SeasonComponent implements OnInit {

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private leagueService: LeaguesService,

    ) { }

    ctxSeason = { season: null };
    ctxRanking = { ranking: null };

    
    loadingMessage = null;
    errorMessage = null;
    successMessage = null;

    competitionShortName = null;
    leagueShortName = null;
    seasonPattern = null;
    team = null;
    model = {
        leagueShortName: null,
        competitionShortName: null
    };

    ngOnInit() {
        this.route.paramMap.subscribe((params: ParamMap) => {
            let url1 = params.get('competition');
            this.competitionShortName = url1;

            let url2 = params.get('league');
            this.leagueShortName = url2;

            let url3 = params.get('season');
            this.seasonPattern = url3;

            let url4 = params.get('team');
            this.team = url4;

            this.model = {
                leagueShortName: this.leagueShortName,
                competitionShortName: this.competitionShortName
            }
            
            this.retrieveSeason(this.seasonPattern);
            

        });
  }

  retrieveSeason(pattern){
    this.loadingMessage = "Recupero della stagione in corso";
    this.successMessage = null;
    this.errorMessage = null;

    let req = {
        competition : this.model,
        pattern : pattern
    }
    this.leagueService.retrieveSeason(req) .subscribe(
        data => {
            this.loadingMessage = null;
            this.successMessage = "Stagione Recuperata";
            this.errorMessage = null;
            this.ctxSeason.season = data.season;
            this.ctxRanking.ranking = data.ranking;
            
          
            console.log(data)
        },

        error => {
            this.loadingMessage = null;
            this.successMessage = null;
            this.errorMessage = "Errore di comunicazione col server";
          
        });

  }

    goToCompetition(statsType){
        this. router.navigate(['/competition', {league : this.leagueShortName, competition : this.competitionShortName, type:statsType}])
    }

}