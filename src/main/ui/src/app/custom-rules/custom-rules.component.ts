import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, ParamMap } from '@angular/router';
import { LeaguesService } from '../leagues.service';
import { Location } from '@angular/common';
import * as _ from 'lodash';
import { HeaderService } from '../header.service';

@Component({
    selector: 'app-custom-rules',
    templateUrl: 'custom-rules.component.html',
    styleUrls: ['custom-rules.component.css']
})
export class CustomRulesComponent implements OnInit {

modalText:string = ''; 
modalTitle:string='';

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
        private headerService: HeaderService

    ) { }

    ngOnInit() {
        this.route.paramMap.subscribe((params: ParamMap) => {
            let url1 = params.get('competition');
            this.competitionShortName = url1;

            let url2 = params.get('league');
            this.leagueShortName = url2;

        });

        this.retrieveCustomRules();

        this.headerService.changeCustomPage('CUSTOM');
      
    }

    updateModalTextDifferentRule(text:any, title:string){
        let message= '';
        if (typeof text === 'object'){
            for(let key in text) {
                message += key + ': ' + text[key] + ';<br>';
               
            }
        }
        else if (typeof text === 'boolean')
            if (text===true) message = 'SI';
            else message = 'NO';
        else {
            message = text;
        }
        this.modalText = '<p>' + 'Il valore originale è:' + '</p>' +  '<p>' + message + '</p>'
        this.modalTitle = title;
    }

    updateModalText(text:string, title:string){
        this.modalText = text;
        this.modalTitle = title;
    }

    isDifferent(o1:any, o2:any):boolean{
        // console.log("o1 " + JSON.stringify(o1));
        // console.log("o2" + JSON.stringify(o2));
        // return JSON.stringify(o1) != JSON.stringify(o2);


        let same:boolean = true;
        if (typeof o1 === 'object'){
            for(let key in o1) {
                for(let key2 in o2) {
                    if (key == key2){
                        let el1 = o1[key];
                        let el2 = o2[key2];
                        if ( typeof el1 == 'object'){
                            same = same && !this.isDifferent(el1, el2);
                        }
                        else 
                            same = same && o1[key] == o2[key2];
                    }
                    if (!same){
                        console.log(key + ":" + o1[key] + "-" + key2 + ":" + o2[key2]);
                    }
                }
            }
        }
        else 
            same= o1==o2;
           
        

        // let eq =  _.isEqualWith(o1, o2, this.customizer);
        return !same;
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
