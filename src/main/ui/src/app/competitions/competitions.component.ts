import { Component, OnInit } from '@angular/core';
import { LeaguesService } from '../leagues.service';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';

@Component({
  selector: 'app-competitions',
  template: `


  <div class="col-md-6 col-md-offset-3" >
    <h3>Le Competizioni della lega <strong>{{leagueName}}</strong></h3>
    
    <div class="table-responsive">
      <table class="table">
        <tr *ngFor = "let competition of competitionList" >
          <td > {{competition.name}} </td>
          <td> <button [disabled] = "competition.type != 'CA'" class="btn btn-primary" *ngIf  = "!competition.rulesIntegrated"  (click)="goToCompetitionRules(competition)"> Aggiungi regole </button>  </td>
          <!--<td> <button class="btn btn-primary" (click)="goToCompetitionRules(competition)"> Integra regole </button>  </td>-->
          <td> <button [disabled] = "competition.type != 'CA'" class="btn btn-primary" *ngIf  = "competition.rulesIntegrated && !competition.initialOnlineInfoDownloaded"  (click)="downloadAllCompetitionInfo(competition)"> Scarica tutti i dati </button>  </td>
          <td> <button [disabled] = "competition.type != 'CA'" class="btn btn-primary" *ngIf  = "competition.rulesIntegrated && competition.initialOnlineInfoDownloaded"  (click) = "goToCompetition(competition, 'REAL') "> Statistiche </button> </td>
          <td> <button [disabled] = "competition.type != 'CA'" class="btn btn-primary" *ngIf  = "competition.rulesIntegrated && competition.initialOnlineInfoDownloaded"  (click) = "goToCompetition(competition, 'CUSTOM') "> Statistiche custom </button> </td>

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

    <button class="btn btn-primary"(click) = "backToLeagues()"> Torna alle leghe </button>

  </div>

 

  `,
  styles: []
})
export class CompetitionsComponent implements OnInit {

  constructor(
    private leagueService: LeaguesService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  competitionList = [];
  errorMessage = null;
  loadingMessage  = null;
  successMessage = null;
  leagueShortName = null;
  leagueName = null;


  ngOnInit() {
    this.route.paramMap.subscribe((params: ParamMap) => {
      let url1 = params.get('id');
      this.leagueShortName = url1;
    });


    this.retrieveCompetitions(this.leagueShortName);
  }

  
  
  retrieveCompetitions(leagueShortName){
    this.leagueService.retrieveCompetitions(leagueShortName)
    .subscribe(
      data => {
          this.competitionList = data
        this.leagueName = this.competitionList  ? this.competitionList[0].leagueName : "NON HAI COMPETIZIONI";
      },

      error => {
        this.errorMessage = "Errore di comunicazione col server" 
      });

      this.errorMessage = null;
  }

  competitionBean = {
    leagueShortName : null,
    competitionShortName : null
  }

  downloadAllCompetitionInfo(competition){
    // Calculate Binding
    // Calculate Competition Pattern
    // Save Online Season And Teams
    this.loadingMessage = null;
    this.successMessage = null;
    this.errorMessage = null;

    this.competitionBean.leagueShortName = this.leagueShortName;
    this.competitionBean.competitionShortName = competition.competitionShortName;

    this.loadingMessage = "Recupero Legame tra il calendario della competizione e il calendario della Serie A in corso...";
    this.leagueService.calculateBinding(this.competitionBean)
    .subscribe(
      data => {
        this.loadingMessage = null;
        this.successMessage = ". . . . . . . . . . . . . . Legame tra il calendario della competizione e il calendario della Serie A eseguito";
        this.errorMessage = null;
        this.calculateCompetitionPattern(competition);
      },
      error => {
        this.loadingMessage = null;
        this.successMessage = null;
        this.errorMessage = "Errore di comunicazione col server 1";

      });
  }

  calculateCompetitionPattern(competition){
    this.loadingMessage = "Calcolo del pattern del calendario della competizione in corso...";
    this.leagueService.calculateCompetitionPattern(this.competitionBean)
    .subscribe(
      data => {
        this.loadingMessage = null;
        this.successMessage += ". . . . . . . . . . . . . . Calcolo del pattern del calendario della competizione  eseguito";
        this.errorMessage = null;
        this.saveOnlineSeasonAndTeams(competition);
      },
      error => {
        this.loadingMessage = null;
        this.successMessage = null;
        this.errorMessage = "Errore di comunicazione col server 2";

      });
    }


  saveOnlineSeasonAndTeams(competition){
    this.loadingMessage = "Calcolo i risultati online della competizione in corso...";
    this.leagueService.saveOnlineSeasonAndTeams(this.competitionBean)
    .subscribe(
      data => {
        this.loadingMessage = null;
        this.successMessage += ". . . . . . . . . . . . . . Calcolo i risultati online della competizione eseguito";
        this.errorMessage = null;
        competition.initialOnlineInfoDownloaded = true;
      },
      error => {
        this.loadingMessage = null;
        this.successMessage = null;
        this.errorMessage = "Errore di comunicazione col server 3";

   });
    

  }


  goToCompetition(competition,rulesType){
    this. router.navigate(['/competition', {league : this.leagueShortName, competition : competition.competitionShortName, type:rulesType}])

  }

  goToCompetitionRules(competition){
    this. router.navigate(['/competitionRules', {league : this.leagueShortName, competition : competition.competitionShortName}])
  }

  backToLeagues(){
    this. router.navigate(['/leagues'])

  }
  

}
