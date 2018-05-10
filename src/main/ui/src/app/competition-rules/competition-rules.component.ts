import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, ParamMap } from '@angular/router';
import { LeaguesService } from '../leagues.service';
import { Location } from '@angular/common';

@Component({
    selector: 'app-competition-rules',
    template: `
  <div class="col-md-6 col-md-offset-3" >
  <div *ngIf="!successMessageLeague">
      <!-- <h2>Regole della Competizione {{competitionShortName}} </h2>-->
      <h5>Indica queste regole che non siamo riusciti a scaricare da FantaGazzetta</h5>

      <form name="form" (ngSubmit)="saveCustomRules()" #f="ngForm" novalidate>

          
        <div class="form-group" >
            <label for="sel1"> Numero massimo di sostituzioni d'ufficio </label>
            <select class="form-control" id="sel1" name="maxOfficeVoteBehaviour" [(ngModel)]="model.maxOfficeVoteBehaviour" #maxOfficeVoteBehaviour="ngModel">
                <option value = "TILL_SUBSTITUTIONS"> Fino al numero di sostituzioni impostato </option>
                <option value = "TILL_ALL"> Fino a 11 </option>
            </select>
        </div> 

        <div class="form-group" >
            <label for="sel1"> In casi di rinvio o sospensione delle partite </label>
            <select class="form-control" id="sel1" name="postponementBehaviour" [(ngModel)]="model.postponementBehaviour" #postponementBehaviour="ngModel">
                <option value = "ALL_6"> Tutti 6 </option>
                <option value = "WAIT_MATCHES"> Attesa dei recuperi </option>
            </select>
        </div> 

        <div class="form-group" >
            <label for="sel1"> Autogol </label>
            <select class="form-control" id="sel1" name="autogolActive" [(ngModel)]="model.autogolActive" #autogolActive="ngModel">
                <option value = false> No </option>
                <option value = true> Si </option>
            </select>
        </div>


        <!--<div class="form-group" *ngIf = "model.autogolActive=='true'" [ngClass]="{ 'has-error': f.submitted && !autogol.valid }">-->
        <div class="form-group" *ngIf = "isAutogolActive()" [ngClass]="{ 'has-error': f.submitted && !autogol.valid }">
          <label for="autogol"> Soglia Autogol </label>
          <input type="number" class="form-control" name="autogol" [(ngModel)]="model.autogol" #autogol="ngModel" required />
          <div *ngIf="f.submitted && !autogol.valid" class="help-block">Devi inserire lo Username di Fantagazzetta</div>
        </div>


        
          
          <div class="form-group">
              <button class="btn btn-primary">Salva</button>
          </div>

       </form>



      <div class="alert alert-danger" *ngIf="errorMessage">
          <strong>{{errorMessage}}</strong>
      </div>

      <div class="alert alert-success" *ngIf="successMessage">
          <strong>{{successMessage}}</strong>
      </div>

   
  </div>
  
  
      
  

</div>
  `,
    styles: []
})
export class CompetitionRulesComponent implements OnInit {
    leagueShortName = null;
    competitionShortName = null;
    errorMessage = null;
    successMessage = null;

    model: any =
        {
            leagueShortName: null,
            competitionShortName: null,
            maxOfficeVoteBehaviour: "TILL_ALL",
            postponementBehaviour: "ALL_6",
            autogolActive: <boolean>false,
            autogol: 59
        };

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private leagueService: LeaguesService,
        private _location: Location,

    ) { }

    ngOnInit() {
        this.route.paramMap.subscribe((params: ParamMap) => {
            let url1 = params.get('competition');
            this.competitionShortName = url1;

            let url2 = params.get('league');
            this.leagueShortName = url2;

        });

    }

    isAutogolActive() {
        let act = this.model.autogolActive;
        if (act == "true")
            return true;
        return false;
    }

    saveCustomRules() {
        this.model.leagueShortName = this.leagueShortName;
        this.model.competitionShortName = this.competitionShortName;
        this.leagueService.integrateRules(this.model)
            .subscribe(
                data => {
                    this.successMessage = "Le regole sono state salvate";
                    this.errorMessage = null;
                    this._location.back();
                },

                error => {
                    this.successMessage = null;
                    this.errorMessage = "Errore di comunicazione col server"
                });

        this.errorMessage = null;
    }

    
}
