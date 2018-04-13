import { Component, OnInit } from '@angular/core';
import { LeaguesService } from '../leagues.service';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';

@Component({
  selector: 'app-competitions',
  template: `
  <h3>Le Competizioni della lega {{legueShortName}}</h3>
  
  <div class="table-responsive">
    <table class="table">
      <tr *ngFor = "let competition of competitionList" >
        <td> {{competition.name}} </td>
        <td> <button class="btn btn-primary"   (click)="downloadCompetitionRules(competition)"> Scarica Regole </button>  </td>
        <td> <button class="btn btn-primary"   (click) = "goToCompetition(competition)"> Vai </button> </td>
      <tr>
    </table>
  </div> 
  

  <div class="alert alert-danger" *ngIf="errorMessage">
    <strong>{{errorMessage}}</strong>
  </div>

 

  `,
  styles: []
})
export class CompetitionsComponent implements OnInit {

  constructor(
    private leagueService: LeaguesService,
    private route: ActivatedRoute,
    private router: Router,
  ) { }

  competitionList = [];
  errorMessage = null;
  loadingMessage  = null;
  successMessage = null;
  leagueShortName = null;

  ngOnInit() {
    this.route.paramMap.subscribe((params: ParamMap) => {
      let id = params.get('id');
      this.leagueShortName = id;
    });


    this.retrieveCompetitions(this.leagueShortName);
  }

  
  goToCompetition(competition){

  }


  

  downloadCompetitionRules(competition){
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
