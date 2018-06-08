import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, ParamMap } from '@angular/router';
import { LeaguesService } from '../leagues.service';
import { Location } from '@angular/common';

@Component({
    selector: 'app-competition-rules',
    templateUrl: 'competition-rules.html',
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
