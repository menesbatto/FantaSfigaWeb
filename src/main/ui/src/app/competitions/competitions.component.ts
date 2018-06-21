import { Component, OnInit, ViewChild } from '@angular/core';
import { LeaguesService } from '../leagues.service';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { HeaderService } from '../header.service';
import { MessageboxComponent } from '../messagebox/messagebox.component';

@Component({
    selector: 'app-competitions',
    template: `


  <div>
    <!--<h3 align = "center">Competizioni di lega</h3>-->
    <!--<h3 align = "center"><strong>{{leagueName | uppercase}}</strong></h3>-->
    
    <div class="table table-responsive">
        <table class="table">
            <tr *ngFor = "let competition of competitionList" >
                <td> {{competition.name}} </td>
                <td> <button [disabled] = "competition.type != 'CA'" class="btn btn-primary" *ngIf  = "competition.type == 'CA' && !competition.rulesIntegrated"  (click)="goToCompetitionRules(competition)"> Integra Regole </button>  </td>
                <td> <button [disabled] = "competition.type != 'CA'" class="btn btn-primary" *ngIf  = "competition.type == 'CA' && competition.rulesIntegrated && !competition.initialOnlineInfoDownloaded"  (click)="downloadAllCompetitionInfo(competition)"> Scarica tutti i dati </button>  </td>
                <td> <button [disabled] = "competition.type != 'CA'" class="btn btn-primary" *ngIf  = "competition.type == 'CA' && competition.rulesIntegrated && competition.initialOnlineInfoDownloaded"  (click) = "goToCompetition(competition, 'REAL') "> Stats </button> </td>
                <td> <button [disabled] = "competition.type != 'CA'" class="btn btn-primary" *ngIf  = "competition.type == 'CA' && competition.rulesIntegrated && competition.initialOnlineInfoDownloaded"  (click) = "goToCompetition(competition, 'CUSTOM') "> Stats custom </button> </td>

            <tr>
            
        </table>
    </div> 
    

    <!--<div class="alert alert-success" *ngIf="successMessage">
      <strong  [innerHTML]= "successMessage"></strong>
    </div>

    <div class="alert alert-success" *ngIf="loadingMessage" >
    <strong>{{loadingMessage}}</strong>
    </div>

    <div class="alert alert-danger" *ngIf="errorMessage">
      <strong>{{errorMessage}}</strong>
    </div>-->

    <app-messagebox></app-messagebox>

  </div>
  
 

  `,
    styles: []
})
export class CompetitionsComponent implements OnInit {

    constructor(
        private leagueService: LeaguesService,
        private route: ActivatedRoute,
        private router: Router,
        private headerService: HeaderService
    ) { }

    competitionList = [];

    leagueShortName = null;
    leagueName = null;
    @ViewChild(MessageboxComponent) messagesBox: MessageboxComponent;


    ngOnInit() {
        this.route.paramMap.subscribe((params: ParamMap) => {
            let url1 = params.get('id');
            this.leagueShortName = url1;
        });


        this.retrieveCompetitions(this.leagueShortName);
    }



    retrieveCompetitions(leagueShortName) {
        this.leagueService.retrieveCompetitions(leagueShortName)
            .subscribe(
                data => {
                    let i = data.length
                    while (i--) {
                        if (data[i].type != 'CA') {
                            data.splice(i, 1);
                        }
                    }
                    this.competitionList = data
                    this.leagueName = this.competitionList ? this.competitionList[0].leagueName : "NON HAI COMPETIZIONI";
                    this.headerService.changeTitleParam(this.leagueName);

                },

                error => {
                });

    }

    competitionBean = {
        leagueShortName: null,
        competitionShortName: null
    }

    downloadAllCompetitionInfo(competition) {
        // Calculate Binding
        // Calculate Competition Pattern
        // Save Online Season And Teams
      
        this.competitionBean.leagueShortName = this.leagueShortName;
        this.competitionBean.competitionShortName = competition.competitionShortName;
        this.messagesBox.addMessage('loading', '<p>Recupero Legame tra il calendario della competizione e il calendario della Serie A in corso...');
        this.leagueService.calculateBinding(this.competitionBean)
            .subscribe(
                data => {
                    this.messagesBox.addMessage('loading', 'OK </p>');
                    this.calculateCompetitionPattern(competition);
                },
                error => {

                });
    }

    calculateCompetitionPattern(competition) {
        this.messagesBox.addMessage('loading', '<p>Calcolo del pattern del calendario della competizione in corso...');
        this.leagueService.calculateCompetitionPattern(this.competitionBean)
            .subscribe(
                data => {
                    this.messagesBox.addMessage('loading', 'OK</p>');
                    this.saveOnlineSeasonAndTeams(competition);
                },
                error => {

                });
    }


    saveOnlineSeasonAndTeams(competition) {
        this.messagesBox.addMessage('loading', '<p>Calcolo i risultati online della competizione in corso...</p>');
        
        this.leagueService.saveOnlineSeasonAndTeams(this.competitionBean)
            .subscribe(
                data => {
                    this.messagesBox.setMessage('success', '<p>Calcolo i risultati online della competizione eseguito</p>');
                    competition.initialOnlineInfoDownloaded = true;
                },
                error => {

                });


    }


    goToCompetition(competition, rulesType) {
        this.router.navigate(['/competition', { league: this.leagueShortName, competition: competition.competitionShortName, type: rulesType }])

    }

    goToCompetitionRules(competition) {
        this.router.navigate(['/competitionRules', { league: this.leagueShortName, competition: competition.competitionShortName }])
    }




}
