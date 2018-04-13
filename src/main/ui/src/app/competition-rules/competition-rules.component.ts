import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, ParamMap } from '@angular/router';

@Component({
  selector: 'app-competition-rules',
  template: `
  <div class="col-md-6 col-md-offset-3" >
  <div *ngIf="!successMessageLeague">
      <h2>Regole della Competizione {{competitionShortName}} </h2>
      <h5>Indici queste regole che non siamo riusciti a scaricare da FantaGazzetta</h5>

      <form name="form" (ngSubmit)="saveCustomRules()" #f="ngForm" novalidate>

          
        <div class="form-group" >
            <label for="sel1"> Numero massimo di sostituzioni d'ufficio </label>
            <select class="form-control" id="sel1" name="maxOfficeVoteBehaviour" [(ngModel)]="model.maxOfficeVoteBehaviour" #maxOfficeVoteBehaviour="ngModel">
              <option value = "TILL_11"> Fino a 11 </option>
              <option value = "TILL_SUBSTITUTIONS"> Fino al numero di sostituzioni impostato </option>
            </select>
        </div> 

        <div class="form-group" >
            <label for="sel1"> Numero massimo di sostituzioni d'ufficio </label>
            <select class="form-control" id="sel1" name="maxOfficeVoteBehaviour" [(ngModel)]="model.maxOfficeVoteBehaviour" #maxOfficeVoteBehaviour="ngModel">
              <option value = "TILL_11"> Fino a 11 </option>
              <option value = "TILL_SUBSTITUTIONS"> Fino al numero di sostituzioni impostato </option>
            </select>
        </div> 

        <div class="form-group" >
            <label for="sel1"> Numero massimo di sostituzioni d'ufficio </label>
            <select class="form-control" id="sel1" name="maxOfficeVoteBehaviour" [(ngModel)]="model.maxOfficeVoteBehaviour" #maxOfficeVoteBehaviour="ngModel">
              <option value = "TILL_11"> Fino a 11 </option>
              <option value = "TILL_SUBSTITUTIONS"> Fino al numero di sostituzioni impostato </option>
            </select>
        </div> 

        <div class="form-group" >
            <label for="sel1"> Numero massimo di sostituzioni d'ufficio </label>
            <select class="form-control" id="sel1" name="maxOfficeVoteBehaviour" [(ngModel)]="model.maxOfficeVoteBehaviour" #maxOfficeVoteBehaviour="ngModel">
              <option value = "TILL_11"> Fino a 11 </option>
              <option value = "TILL_SUBSTITUTIONS"> Fino al numero di sostituzioni impostato </option>
            </select>
        </div> 

        <div class="form-group" >
            <label for="sel1"> Numero massimo di sostituzioni d'ufficio </label>
            <select class="form-control" id="sel1" name="maxOfficeVoteBehaviour" [(ngModel)]="model.maxOfficeVoteBehaviour" #maxOfficeVoteBehaviour="ngModel">
              <option value = "TILL_11"> Fino a 11 </option>
              <option value = "TILL_SUBSTITUTIONS"> Fino al numero di sostituzioni impostato </option>
            </select>
        </div> 


        
          
          <div class="form-group">
              <button [disabled]="loading" class="btn btn-primary">Salva</button>
          </div>

          <div class="alert alert-danger" *ngIf="errorMessage">
              <strong>{{errorMessage}}</strong>
          </div>

          <div class="alert alert-success" *ngIf="loadingMessage">
              <strong>{{loadingMessage}}</strong>
          </div>

          
          
      </form>
  
  </div>
  <div *ngIf="successMessageLeague">
      <div class="alert alert-success" >
          <strong>{{successMessageLeague}}</strong>
      </div>
      <button  class="btn btn-primary btn-block" (click) = "goToLeagues()"> VAI ALLE TUE LEGHE </button>
  </div>

</div>
  `,
  styles: []
})
export class CompetitionRulesComponent implements OnInit {

  leagueShortName = null;
  competitionShortName = null;

  model:any=
  {
    leagueShortName :			this.leagueShortName,
    competitionShortName :	this.competitionShortName,
    maxOfficeVoteBehaviour :	"TILL_SUBSTITUTIONS",
    postponementBehaviour :	"WAIT_MATCHES",
    autogolActive :			true,
    autogol :					59
  };

  constructor(
    private route: ActivatedRoute,
    private router: Router,
  ) { }


  ngOnInit() {
    this.route.paramMap.subscribe((params: ParamMap) => {
      let url1 = params.get('competitionShortName');
      this.competitionShortName = url1;

      let url2 = params.get('leagueShortName');
      this.leagueShortName = url2;

    });

  }

  saveCustomRules(){
    console.log(this.model);
  }


}
