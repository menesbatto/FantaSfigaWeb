import { Component, OnInit } from '@angular/core';
import { LeaguesService } from '../leagues.service';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';

@Component({
  selector: 'app-competitions',
  template: `


  <div class="col-md-6 col-md-offset-3" >
    <h3>Le Competizioni della lega {{legueShortName}}</h3>
    
    <div class="table-responsive">
      <table class="table">
        <tr *ngFor = "let competition of competitionList" >
          <td> {{competition.name}} </td>
          <td> <button class="btn btn-primary"   (click)="goToCompetitionRules(competition)"> Aggiungi regole </button>  </td>
          <td> <button class="btn btn-primary"   (click) = "goToCompetition(competition)"> Vai </button> </td>
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

  ngOnInit() {
    this.route.paramMap.subscribe((params: ParamMap) => {
      let url1 = params.get('id');
      this.leagueShortName = url1;
    });


    this.retrieveCompetitions(this.leagueShortName);
  }

  
  goToCompetition(competition){
  }


  

  goToCompetitionRules(competition){
    this. router.navigate(['/competitionRules', {league : this.leagueShortName, competition : competition.competitionShortName}])

    // this.errorMessage = null;
    // this.loadingMessage = "Stiamo scaricando le competizioni delle lega selezionata...";

    // this.leagueService.downloadCompetitions(league.shortName)
    // .subscribe(
    //   data => {
    //     league.isCompetitionsDownloaded = true;
    //     this.loadingMessage = null;
    //     this.errorMessage = null;
    //     this.successMessage = "Le competizioni della Lega " + league.name + " sono state scaricate"
    //   },
    //   error => {
    //     this.loadingMessage = null;
    //     this.successMessage = null;
    //     this.errorMessage = "Errore del server";
    //   })
  }

  
  retrieveCompetitions(leagueShortName){
    this.leagueService.retrieveCompetitions(leagueShortName)
    .subscribe(
      data => {
        this.competitionList = data
      },

      error => {
        this.errorMessage = "Errore di comunicazione col server" 
      });

      this.errorMessage = null;
  }

  onSelect(data){
    console.log(data + " onSelect()")
  }
  

}
