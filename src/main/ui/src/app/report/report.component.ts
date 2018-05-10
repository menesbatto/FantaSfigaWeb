import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, ParamMap } from '@angular/router';
import { LeaguesService } from '../leagues.service';
import { HeaderService } from '../header.service';

@Component({
  selector: 'app-report',
  template: `

    <div>
    <!--<h3 align = "center">Le tue leghe </h3>-->
  
    <div class="table-responsive">
        <table class="table">
            <thead>
                <tr>
                    <th>Giornata Serie A</th>
                    <th>Team</th>
                    <th>Giocatore</th>
                    <th>Voto Uff</th>
                    <th></th>
                    <th>Voto Lega</th>
                    <th>FantaVoto Uff</th>
                    <th></th>
                    <th>FantaVoto Lega</th>
                    <th>Squadra</th>
                </tr>
            </thead>
            <tbody>
                <tr *ngFor = "let miss of report.voteMismatches" >
                    <td> {{miss.serieASeasonDay}} </td>
                    <td> {{miss.pvcVote.team}} </td>
                    <td> {{miss.pvcVote.name}} </td>
                    <td> {{miss.pvcVote.vote}} </td>
                    <td><span class="glyphicon glyphicon-arrow-right"></span></td>
                    <td> {{miss.pvcVote.voteFromWeb}} </td>
                    <td> {{miss.pvcVote.fantavote}} </td>
                    <td><span class="glyphicon glyphicon-arrow-right"></span></td>
                    <td> {{miss.pvcVote.fantaVoteFromWeb}} </td>
                    <td> {{miss.player}} </td>
                <tr>
            </tbody>
        </table>
  </div> 


  <div class="table-responsive">
        <table class="table">
            <thead>
                <tr>
                    <!--<th>Giornata Serie A</th>-->
                    <th></th>    
                    <th class="bord"></th>
                    <th></th>
                    <th>App </th>
                    <th></th>
                    <th>Web</th>
                    <th></th>
                    
                    
                    <th class="bord"></th>
                    <th>App</th>
                    <th></th>
                    <th></th>
                    <th>Web</th>
               

                </tr>
            </thead>
            <thead>
                <tr>
                    <!--<th>Giornata Serie A</th>-->
                    <th># Com</th>
                    <th class="bord">Casa </th>
                    <th>Goal</th>
                    <th>Punti</th>
                    <th></th>
                    <th>Goal</th>
                    <th>Punti</th>
                    
                    <th class="bord" >Fuori </th>
                    <th>Goal</th>
                    <th>Punti</th>
                    <th></th>
                    <th>Goal</th>
                    <th>Punti</th>

                </tr>
            </thead>
            <tbody>
                <tr *ngFor = "let miss of report.matchMismatches" >
                    <!--<td> {{miss.serieASeasonDay}} </td>-->
                    <td> {{miss.competitonSeasonDay}} </td>
                    
                    <td class="bord"> {{miss.homeLulApp.teamName}} </td>
                    <td> {{miss.homeLulApp.goals}} </td>
                    <td> {{miss.homeLulApp.sumTotalPoints}} </td>
                    <td><span class="glyphicon glyphicon-arrow-right"></span></td>
                    <td> {{miss.homeLulWeb.goals}} </td>
                    <td> {{miss.homeLulWeb.sumTotalPoints}} </td>
                    

                    <td  class="bord"> {{miss.awayLulApp.teamName}} </td>
                    <td> {{miss.awayLulApp.goals}} </td>
                    <td> {{miss.awayLulApp.sumTotalPoints}} </td>
                    <td><span class="glyphicon glyphicon-arrow-right"></span></td>
                    <td> {{miss.awayLulWeb.goals}} </td>
                    <td> {{miss.awayLulWeb.sumTotalPoints}} </td>
                <tr>
            </tbody>
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
export class ReportComponent implements OnInit {

    leagueShortName = null;
    competitionShortName = null;
   
    model = {
        leagueShortName: null,
        competitionShortName: null
    };

    loading = null;
    successMessage = null;
    errorMessage = null;
    loadingMessage = null;
    competitionName = null;
    report = null;

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private leagueService: LeaguesService,
        private headerService: HeaderService

    ) { }
    
    //VIA
    //newMessage(){
    //    this.headerService.changeMessage("APPENA MOFIGICATO");
    //}

    ngOnInit() {
        //VIA
        //this.headerService.currentMessage.subscribe(message => this.message = message);
        //this.headerService.currentTitleParam.subscribe(titleParam => this.titleParam = titleParam);

        this.route.paramMap.subscribe((params: ParamMap) => {
            let url1 = params.get('competition');
            this.competitionShortName = url1;

            let url2 = params.get('league');
            this.leagueShortName = url2;

            this.model = {
                leagueShortName: this.leagueShortName,
                competitionShortName: this.competitionShortName
            }

            this.retrieveReport();
            


        });

    }

    
    retrieveReport() {
        this.loading = true;
        this.successMessage = null;
        this.errorMessage = null;
        this.loadingMessage = "Recupero Statistiche in corso...";


        let competition = this.model;
          

        this.leagueService.retrieveReport(competition)
        .subscribe(data => {
            this.loadingMessage = null;
            this.successMessage = "Report recuperato";
            this.errorMessage = null;
            this.loading = false;
            this.report = data.report;

            this.competitionName = data.report.competition.name;
            
         },

            error => {
                this.loadingMessage = null;
                this.successMessage = null;
                this.errorMessage = "Errore comunicazione col server";
                this.loading = false;
            });
    }
}
