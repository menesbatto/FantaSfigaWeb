import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, ParamMap } from '@angular/router';
import { LeaguesService } from '../leagues.service';
import { Location } from '@angular/common';
import { HeaderService } from '../header.service';

@Component({
    selector: 'app-competition-rules',
    templateUrl: 'competition-rules.component.html',
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
        private headerService: HeaderService

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
                   
                    this.rules = data.realRules;
                    if (this.rules.competitionRules.postponementBehaviour == null){
                        this.rules.competitionRules.postponementBehaviour = "ALL_6";

                    }

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
                    this.headerService.removeCompetitionCalculated(this.competitionShortName);
                    this.headerService.removeCompetitionDownloaded(this.competitionShortName);
                    // localStorage.setItem(this.competitionShortName + '-statsAlreadyCalculated', "false");
                    // localStorage.setItem(this.competitionShortName + '-alreadyDownloadInfo', "false");

                    this._location.back();
                },

                error => {
                    this.successMessage = null;
                    this.errorMessage = "Errore di comunicazione col server"
                });

        this.errorMessage = null;
    }

    
}
