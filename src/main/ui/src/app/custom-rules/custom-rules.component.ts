import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, ParamMap } from '@angular/router';
import { LeaguesService } from '../leagues.service';
import { Location } from '@angular/common';

@Component({
    selector: 'app-custom-rules',
    templateUrl: 'custom-rules.component.html',
    styles: []
})
export class CustomRulesComponent implements OnInit {

modalText:string = ''; 

    errorMessage = null;
    successMessage = null;


    leagueShortName = null;
    competitionShortName = null;
    competitionBean = {
        leagueShortName: null,
        competitionShortName: null
    };
   
    realRules = null;
   
    bonusMalus: any = null;
    showBonusMalus:boolean = false;
    dataSource: any = null;
    showDataSource:boolean = false;
    points: any = null;
    showPoints:boolean = false;
    substitutions: any = null;
    showSubstitutions:boolean = false;
    modifiers: any = null;
    showModifiers:boolean = false;
    competitionRules: any = null;
    showCompetitionRules:boolean = false;






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

        this.retrieveCustomRules();
    }

    updateModalText(text:string){
        this.modalText = text;
    }

    retrieveCustomRules() {
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
                    
                    this.realRules= data.realRules;

                    this.bonusMalus = data.customRules.bonusMalus;
                    this.dataSource = data.customRules.dataSource;
                    this.points = data.customRules.points;
                    this.substitutions = data.customRules.substitutions;
                    this.modifiers = data.customRules.modifiers;
                    this.competitionRules = data.customRules.competitionRules;

                    // this.handlePostponements();

                },

                error => {
                    this.successMessage = null;
                    this.errorMessage = "Errore di comunicazione col server"
                }

            )


    }

    resetCustomRules(){
        let req = {
            leagueShortName : this.leagueShortName,
            competitionShortName : this.competitionShortName,
            rules : { 
                bonusMalus: this.realRules.bonusMalus,
                dataSource: this.realRules.dataSource,
                points: this.realRules.points,
                substitutions: this.realRules.substitutions,
                modifiers: this.realRules.modifiers,
                competitionRules: this.realRules.competitionRules,
                type : "CUSTOM"
            }    
        }
        this.leagueService.saveCustomRules(req)
        .subscribe(
            data => {
                this.bonusMalus = data.bonusMalus;
                this.dataSource = data.dataSource;
                this.points = data.points;
                this.substitutions = data.substitutions;
                this.modifiers = data.modifiers;
                this.competitionRules = data.competitionRules;


                this.successMessage = "Le regole custom sono state resettate";
                this.errorMessage = null;
            },

            error => {
                this.successMessage = null;
                this.errorMessage = "Errore di comunicazione col server"
            });
    }

    saveCustomRules() {
        this.competitionBean.leagueShortName = this.leagueShortName;
        this.competitionBean.competitionShortName = this.competitionShortName;
        
        let req = {
            leagueShortName : this.leagueShortName,
            competitionShortName : this.competitionShortName,
            rules : { 
                bonusMalus: this.bonusMalus,
                dataSource: this.dataSource,
                points: this.points,
                substitutions: this.substitutions,
                modifiers: this.modifiers,
                competitionRules: this.competitionRules,
                type : "CUSTOM"
            }    
        }

        this.leagueService.saveCustomRules(req)
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



    goToCompetition(statsType){
        this. router.navigate(['/competition', {league : this.leagueShortName, competition : this.competitionShortName, type:statsType}])
    }

    

}
