import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, ParamMap } from '@angular/router';
import { LeaguesService } from '../leagues.service';
import { Location } from '@angular/common';

@Component({
    selector: 'app-competition-rules',
    template: `
  
    <div *ngIf="rules" >
        <!-- <h2>Regole della Competizione {{competitionShortName}} </h2>-->
        <h4>Inserisci queste regole che non siamo riusciti a scaricare da FantaGazzetta</h4>

       
            
        <div class="form-group" >
            <label for="sel1"> Numero massimo di sostituzioni d'ufficio </label>
            <select class="form-control" id="sel1" name="maxOfficeVoteBehaviour" [(ngModel)]="rules.substitutions.maxOfficeVotes" #maxOfficeVoteBehaviour="ngModel">
                <option value = "TILL_SUBSTITUTIONS"> Fino al numero di sostituzioni impostato </option>
                <option value = "TILL_ALL"> Fino a 11 </option>
            </select>
        </div> 



        <div class="form-group" >
            <label for="sel1"> Autogol </label>
            <select class="form-control" id="sel1" name="autogolActive" [(ngModel)]="rules.points.autogolActive" #autogolActive="ngModel">
                <option value = false> No </option>
                <option value = true> Si </option>
            </select>
        </div>


        <div class="form-group" *ngIf = "this.rules.points.autogolActive==true || this.rules.points.autogolActive=='true'">
            <label for="autogol"> Soglia Autogol </label>
            <input type="number" class="form-control" name="autogol" [(ngModel)]="rules.points.autogol" #autogol="ngModel" required />
            <div *ngIf="false" class="help-block">Devi inserire lo Username di Fantagazzetta</div>
        </div>


            
            
           

       


        <div class="form-group" >
            <label for="sel1"> In casi di rinvio o sospensione delle partite </label>
            <select (change)="behaviourChanged(rules.competitionRules.postponementBehaviour)" class="form-control" id="sel1" name="postponementBehaviour" [(ngModel)]="rules.competitionRules.postponementBehaviour" #postponementBehaviour="ngModel">
                <option value = "ALL_6"> Tutti 6 </option>
                <option value = "WAIT_MATCHES"> Attesa dei recuperi </option>
                <option value = "MIXED" hidden> Misto </option>
            </select>
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
                    <div>{{postponement.played ? 'SI' : 'NO'}}</div>
                </div>
                <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
                    <div>{{postponement.wait ? 'ATTESA' : 'TUTTI 6'}}</div>
                </div>
                <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
                    <div><button  class="btn btn-primary" (click)="invertWait(postponement)"> Cambia </button></div>
                </div>
            </div>
        </div>






        <br>
        <br>




        <div class="row">
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                <h4><strong>Giornate di Serie A da saltare</strong></h4>
            </div>
        </div>
        <div class="form-group">
            <label for="seasonDayToJumpText"> Aggiungi giornata di Serie A da saltare </label>
            <input type="number" name="seasonDayToJumpText" [(ngModel)]="seasonDayToJump" #seasonDayToJumpText="ngModel"/>
            <button class="btn btn-primary"  (click) = "addSeasonDayToJump()"> Aggiungi </button>
        </div>
        
        <div *ngIf="rules.competitionRules.seasonDaysToJump.length > 0">
            <div class="row nobor col-lg-10 col-md-10 col-sm-12 col-xs-12 col-lg-offset-1 col-md-offset-1 col-sm-offset-0 col-xs-offset-0">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <div> <strong> Giornata di Serie A</strong> </div>
                </div>
            </div>

            <div *ngFor="let seasonDay of rules.competitionRules.seasonDaysToJump" class="row nobor col-lg-10 col-md-10 col-sm-12 col-xs-12 col-lg-offset-1 col-md-offset-1 col-sm-offset-0 col-xs-offset-0">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">   
                    <div>{{seasonDay}}</div>
                </div>   
            </div>

            <button class="btn btn-primary nobor"  (click) = "resetSeasonDaysToJump()"> Reset Giornate da Saltare </button>
        </div>
        

        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 alert alert-danger" *ngIf="errorMessage">
            <strong>{{errorMessage}}</strong>
        </div>

        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12  alert alert-success" *ngIf="successMessage">
            <strong>{{successMessage}}</strong>
        </div>


    </div>
    
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <button (click) = "saveCustomRules()" class="btn btn-primary">Salva</button>
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
    seasonDayToJump = null;

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private leagueService: LeaguesService,
        private _location: Location,

    ) { }


    addSeasonDayToJump(){
        if (this.rules.competitionRules.seasonDaysToJump.indexOf(this.seasonDayToJump) < 0
                && this.seasonDayToJump>0 
                && this.seasonDayToJump<38) {
            this.rules.competitionRules.seasonDaysToJump.push(this.seasonDayToJump);
            this.errorMessage = null;
        }
        else {
            this.successMessage= null;
            this.errorMessage = "Inserisci una giornata di Serie A corretta";
        }
    }

    resetSeasonDaysToJump(){
        this.rules.competitionRules.seasonDaysToJump = [];
    }

    behaviourChanged(behaviour){
      
        this.map.forEach((value: string, key: string) => {
            for (var i = 0; i< value.length; i++){
                if (behaviour == "ALL_6")
                    value[i]["wait"]= false;
                else if (behaviour == "WAIT_MATCHES")
                    value[i]["wait"]= true;
            }
             
        });
    }
    
    invertWait(postponement){
        postponement.wait = !postponement.wait;
        let isAll6 = false;
        let isWait = false;
        this.map.forEach((value: string, key: string) => {
           for (var i = 0; i< value.length; i++){
                if (value[i]["wait"])    
                    isWait = true;
                else 
                    isAll6 = true;
                
           }
            
        });
        if (isWait && !isAll6)
            this.rules.competitionRules.postponementBehaviour = "WAIT_MATCHES";
        else if (!isWait && isAll6)
            this.rules.competitionRules.postponementBehaviour = "ALL_6";
        else if (isWait && isAll6)
            this.rules.competitionRules.postponementBehaviour = "MIXED";

    }

    getList(key){
        //console.log(this.map.get(key));
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
                   
                    // this.map.forEach((value: string, key: string) => {
                    //   console.log(key, value);
                    // });
                
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
        if (this.rules.competitionRules.postponementBehaviour== null){
            this.successMessage = null;
            this.errorMessage = "Devi scegliere la politica di assegnazione voto in caso di rinvi o sospensioni delle partite";
            return;
        }
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
