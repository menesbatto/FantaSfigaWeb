import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, ParamMap } from '@angular/router';
import { LeaguesService } from '../leagues.service';
import { Location } from '@angular/common';

@Component({
    selector: 'app-competition-rules',
    template: `
  
    <div *ngIf="rules" >
        <!-- <h2>Regole della Competizione {{competitionShortName}} </h2>-->
        <h5>Indica queste regole che non siamo riusciti a scaricare da FantaGazzetta</h5>

        <form name="form" (ngSubmit)="saveCustomRules()" #f="ngForm" novalidate>

            
            <div class="form-group" >
                <label for="sel1"> Numero massimo di sostituzioni d'ufficio </label>
                <select class="form-control" id="sel1" name="maxOfficeVoteBehaviour" [(ngModel)]="rules.substitutions.maxOfficeVotes" #maxOfficeVoteBehaviour="ngModel">
                    <option value = "TILL_SUBSTITUTIONS"> Fino al numero di sostituzioni impostato </option>
                    <option value = "TILL_ALL"> Fino a 11 </option>
                </select>
            </div> 

            <div class="form-group" >
                <label for="sel1"> In casi di rinvio o sospensione delle partite </label>
                <select class="form-control" id="sel1" name="postponementBehaviour" [(ngModel)]="rules.competitionRules.postponementBehaviour" #postponementBehaviour="ngModel">
                    <option value = "ALL_6"> Tutti 6 </option>
                    <option value = "WAIT_MATCHES"> Attesa dei recuperi </option>
                </select>
            </div> 

            <div class="form-group" >
                <label for="sel1"> Autogol </label>
                <select class="form-control" id="sel1" name="autogolActive" [(ngModel)]="rules.points.autogolActive" #autogolActive="ngModel">
                    <option value = false> No </option>
                    <option value = true> Si </option>
                </select>
            </div>


            <!--<div class="form-group" *ngIf = "model.autogolActive=='true'" [ngClass]="{ 'has-error': f.submitted && !autogol.valid }">-->
            <div class="form-group" *ngIf = "isAutogolActive()" [ngClass]="{ 'has-error': f.submitted && !autogol.valid }">
            <label for="autogol"> Soglia Autogol </label>
            <input type="number" class="form-control" name="autogol" [(ngModel)]="rules.points.autogol" #autogol="ngModel" required />
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


        <div class="row nobor col-lg-10 col-md-10 col-sm-12 col-xs-12 col-lg-offset-1 col-md-offset-1 col-sm-offset-0 col-xs-offset-0">
            <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
                <div> <strong> Giornata</strong> </div>
            </div>
            <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
                <div> <strong>Casa </strong></div>
            </div>
            <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
                <div> <strong>Fuori</strong> </div>
            </div>
            <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
                <div><strong>Giocata</strong> </div>
            </div>
            <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
                <div> <strong>Voti</strong> </div>
            </div>
            <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
            </div>
        
        </div>
        <div *ngFor="let key of getKeys(map)">
            <div class="row nobor col-lg-10 col-md-10 col-sm-12 col-xs-12 col-lg-offset-1 col-md-offset-1 col-sm-offset-0 col-xs-offset-0"  *ngFor = "let postponement of getList(key)">
            <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
                    <div>{{postponement.seasonDay}}</div>
                </div>
                <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
                    <div>{{postponement.homeTeam}}</div>
                </div>
                <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
                    <div>{{postponement.awayTeam}}</div>
                </div>
                <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
                    <div>{{postponement.played}}</div>
                </div>
                <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
                    <div>{{postponement.wait ? 'ATTESA' : 'TUTTI 6'}}</div>
                </div>
                <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
                    <div><button  class="btn btn-primary" (click)="postponement.wait = !postponement.wait"> Cambia </button></div>
                </div>
            </div>
        </div>

     


   <!-- <div class="table-responsive nobor" *ngIf="rules">
        <table class="table">
            <thead>
                <tr>
                    <th> Giornata </th>
                    <th> Recuperi </th>
                <tr>
            </thead>
            <tbody>

                <tr *ngFor="let key of getKeys(map)">
                    <td> {{key}} </td>
                    <td> 
                        <div class="table-responsive nobor" *ngIf="rules">
                            <table class="table">
                                <thead>
                                    <tr>
                                      
                                        <th> In casa </th>
                                        <th> Fuori casa </th>
                                        <th> Giocata </th>
                                        <td> Voti attesi </td>
                                        <td> </td>
                                    <tr>
                                </thead>
                                <tbody>
                                    <tr *ngFor = "let postponement of getList(key)" >
                                      
                                        <td> {{postponement.homeTeam}} </td>
                                        <td> {{postponement.awayTeam}} </td>
                                        <td> {{postponement.played}} </td>
                                        <td> {{postponement.wait ? 'ATTESA' : 'TUTTI 6'}} </td>
                                        <td> <button  class="btn btn-primary" (click)="postponement.wait = !postponement.wait"> Cambia </button>  </td>
                                    <tr>
                                </tbody>
                           </table>
                        </div>
                        
                    </td>
                 <tr>
            </tbody>
        </table>
    </div>-->


     




    </div>
  
  
      
  

  `,
    styles: []
})
export class CompetitionRulesComponent implements OnInit {
    leagueShortName = null;
    competitionShortName = null;
    errorMessage = null;
    successMessage = null;
    rules = null;
   

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private leagueService: LeaguesService,
        private _location: Location,

    ) { }

    
    getList(key){
        console.log(this.map)
        console.log(this.map.get(key));
        return this.map.get(key);
    }

    map = new Map<String, String>();

    getKeys(map){
        return Array.from(map.keys());
    }

    ngOnInit() {
        this.route.paramMap.subscribe((params: ParamMap) => {
            let url1 = params.get('competition');
            this.competitionShortName = url1;

            let url2 = params.get('league');
            this.leagueShortName = url2;

        });
        this.retrieveRules();
    }


    retrieveRules() {
        let retrieveRulesReq = {
            competition: {
                leagueShortName: this.leagueShortName,
                competitionShortName: this.competitionShortName
            }
         }
        
        this.leagueService.retrieveRules(retrieveRulesReq)
            .subscribe(
                data => {
                    this.successMessage = "Le regole sono state recuperate";
                    this.errorMessage = null;
                    let mapPost = data.realRules.competitionRules.postponementMap;
                    for (var property in mapPost) {
                        if (mapPost.hasOwnProperty(property)) {
                            this.map.set(property, mapPost[property]);
                        }
                    };
                   
                    this.map.forEach((value: string, key: string) => {
                      console.log(key, value);
                    });
                
                    this.rules = data.realRules;
                },

                error => {
                    this.successMessage = null;
                    this.errorMessage = "Errore di comunicazione col server"
                }

            )


    }

    isAutogolActive() {
        let act = this.rules.points.autogolActive;
        if (act == "true")
            return true;
        return false;
    }

    saveCustomRules() {
        
        let req = {
            leagueShortName : this.leagueShortName,
            competitionShortName : this.competitionShortName,
            rules : this.rules
        }
        this.leagueService.integrateRules(req)
            .subscribe(
                data => {
                    this.successMessage = "Le regole sono state salvate";
                    this.errorMessage = null;
                    localStorage.setItem(this.competitionShortName + '-statsAlreadyCalculated', "false");
                    localStorage.setItem(this.competitionShortName + '-alreadyDownloadInfo', "false");

                    this._location.back();
                },

                error => {
                    this.successMessage = null;
                    this.errorMessage = "Errore di comunicazione col server"
                });

        this.errorMessage = null;
    }

    
}
