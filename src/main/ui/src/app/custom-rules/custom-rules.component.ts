import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, ParamMap } from '@angular/router';
import { LeaguesService } from '../leagues.service';

@Component({
  selector: 'app-custom-rules',
  template: `
    <div class="container col-md-8 col-md-offset-2" >



        <div id="form-step1" class="step">
            <div class="col-lg-12">
                <div class="padding10 row">
                    <div class="col-lg-4 col-xs-4"></div>
                    <div class="col-lg-1 col-xs-2 acenter p padding10"><span class="role">P</span> </div>
                    <div class="col-lg-1 col-xs-2 acenter d padding10"><span class="role">D</span> </div>
                    <div class="col-lg-1 col-xs-2 acenter c padding10"><span class="role">C</span> </div>
                    <div class="col-lg-1 col-xs-2 acenter a padding10"><span class="role">A</span> </div>
                </div>
                <div class="col-lg-12 padding15 rowb" *ngIf="bonusMalus">
                    <div class="row">
                        <label class="col-lg-4 col-md-4 col-sm-4 col-xs-4 control-label" for="golsegnato">Gol segnato:</label>
                        <div class="col-lg-1 col-md-1 col-sm-2  col-xs-2">
                            <input name="ctl00$main$golsegnato1" [(ngModel)]="bonusMalus.scoredGoal.P" class="form-control chk" type="number">
                        </div>
                        <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2">
                            <input name="ctl00$main$golsegnato2" [(ngModel)]="bonusMalus.scoredGoal.D" class="form-control chk" type="number">
                        </div>
                        <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2">
                            <input name="ctl00$main$golsegnato3" [(ngModel)]="bonusMalus.scoredGoal.C" class="form-control chk" type="number">
                        </div>
                        <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2">
                            <input name="ctl00$main$golsegnato4" [(ngModel)]="bonusMalus.scoredGoal.A" class="form-control chk" type="number">
                        </div>
                        <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1"><i class="info-ico" rel="tooltip" title="" data-original-title="Inserisci per ogni ruolo il valore del gol segnato"></i></div>
                    </div>
                    <div class="row">
                        <label class="col-lg-4 col-md-4 col-sm-4 col-xs-4 control-label" for="golsubito">Gol subito:</label>
                        <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2">
                            <input name="ctl00$main$golsubito1" [(ngModel)]="bonusMalus.takenGoal.P" class="form-control chk" type="number">
                        </div>
                        <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2">
                            <input name="ctl00$main$golsubito2" [(ngModel)]="bonusMalus.takenGoal.D" class="form-control chk" type="number">
                        </div>
                        <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2">
                            <input name="ctl00$main$golsubito3" [(ngModel)]="bonusMalus.takenGoal.C" class="form-control chk" type="number">
                        </div>
                        <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2">
                            <input name="ctl00$main$golsubito4" [(ngModel)]="bonusMalus.takenGoal.A" class="form-control chk" type="number">
                        </div>
                        <div class="col-lg-2 col-md-1 col-sm-1 col-xs-1"><i class="info-ico" rel="tooltip" title="" data-original-title="Inserisci per ogni ruolo il valore del gol subito"></i></div>
                    </div>
                    <div class="row">
                        <label class="col-lg-4 col-md-4 col-sm-4 col-xs-4 control-label" for="rigoresegnato">Rigore segnato:</label>
                        <div class="col-lg-1 col-md-1 col-sm-2  col-xs-2">
                            <input name="ctl00$main$rigoresegnato1" [(ngModel)]="bonusMalus.scoredPenalty.P" class="form-control chk" type="number">
                        </div>
                        <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2">
                            <input name="ctl00$main$rigoresegnato2" [(ngModel)]="bonusMalus.scoredPenalty.D" class="form-control chk" type="number">
                        </div>
                        <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2">
                            <input name="ctl00$main$rigoresegnato3" [(ngModel)]="bonusMalus.scoredPenalty.C" class="form-control chk" type="number">
                        </div>
                        <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2">
                            <input name="ctl00$main$rigoresegnato4" [(ngModel)]="bonusMalus.scoredPenalty.A" class="form-control chk" type="number">
                        </div>
                        <div class="col-lg-2 col-md-1 col-sm-1 col-xs-1"><i class="info-ico" rel="tooltip" title="" data-original-title="Inserisci per ogni ruolo il valore del rigore segnato"></i></div>
                    </div>
                    <div class="row">
                        <label class="col-lg-4 col-md-4 col-sm-4 col-xs-4 control-label" for="rigoresbagliato">Rigore sbagliato:</label>
                        <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2">
                            <input name="ctl00$main$rigoresbagliato1" [(ngModel)]="bonusMalus.missedPenalty.P" class="form-control chk" type="number">
                        </div>
                        <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2">
                            <input name="ctl00$main$rigoresbagliato2" [(ngModel)]="bonusMalus.missedPenalty.D" class="form-control chk" type="number">
                        </div>
                        <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2">
                            <input name="ctl00$main$rigoresbagliato3" [(ngModel)]="bonusMalus.missedPenalty.C" class="form-control chk" type="number">
                        </div>
                        <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2">
                            <input name="ctl00$main$rigoresbagliato4" [(ngModel)]="bonusMalus.missedPenalty.A" class="form-control chk" type="number">
                        </div>
                        <div class="col-lg-2 col-md-1 col-sm-1 col-xs-1"><i class="info-ico" rel="tooltip" title="" data-original-title="Inserisci per ogni ruolo il valore del rigore sbagliato"></i></div>
                    </div>
                    <div class="row">
                        <label class="col-lg-4 col-md-4 col-sm-4 col-xs-4 control-label" for="rigoreparato">Rigore parato:</label>
                        <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2">
                            <input name="ctl00$main$rigoreparato1" [(ngModel)]="bonusMalus.savedPenalty.P" class="form-control chk" type="number">
                        </div>
                        <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2">
                            <input name="ctl00$main$rigoreparato2" [(ngModel)]="bonusMalus.savedPenalty.D" class="form-control chk" type="number">
                        </div>
                        <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2">
                            <input name="ctl00$main$rigoreparato3" [(ngModel)]="bonusMalus.savedPenalty.C" class="form-control chk" type="number">
                        </div>
                        <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2">
                            <input name="ctl00$main$rigoreparato4" [(ngModel)]="bonusMalus.savedPenalty.A" class="form-control chk" type="number">
                        </div>
                        <div class="col-lg-2 col-md-1 col-sm-1 col-xs-1"><i class="info-ico" rel="tooltip" title="" data-original-title="Inserisci per ogni ruolo il valore del rigore parato"></i></div>
                    </div>
                    <div class="row">
                        <label class="col-lg-4 col-md-4 col-sm-4 col-xs-4 control-label" for="ammonizione">Ammonizione:</label>
                        <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2">
                            <input name="ctl00$main$ammonizione1" [(ngModel)]="bonusMalus.yellowCard.P" class="form-control chk" type="number">
                        </div>
                        <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2">
                            <input name="ctl00$main$ammonizione2" [(ngModel)]="bonusMalus.yellowCard.D" class="form-control chk" type="number">
                        </div>
                        <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2">
                            <input name="ctl00$main$ammonizione3" [(ngModel)]="bonusMalus.yellowCard.C" class="form-control chk" type="number">
                        </div>
                        <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2">
                            <input name="ctl00$main$ammonizione4" [(ngModel)]="bonusMalus.yellowCard.A" class="form-control chk" type="number">
                        </div>
                        <div class="col-lg-2 col-md-1 col-sm-1 col-xs-1"><i class="info-ico" rel="tooltip" title="" data-original-title="Inserisci per ogni ruolo il valore dell'ammonizione"></i></div>
                    </div>
                    <div class="row">
                        <label class="col-lg-4 col-md-4 col-sm-4 col-xs-4 control-label" for="esplusione">Espulsione:</label>
                        <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2">
                            <input name="ctl00$main$esplusione1" [(ngModel)]="bonusMalus.redCard.P" class="form-control chk" type="number">
                        </div>
                        <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2">
                            <input name="ctl00$main$esplusione2" [(ngModel)]="bonusMalus.redCard.D" class="form-control chk" type="number">
                        </div>
                        <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2">
                            <input name="ctl00$main$esplusione3" [(ngModel)]="bonusMalus.redCard.C" class="form-control chk" type="number">
                        </div>
                        <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2">
                            <input name="ctl00$main$esplusione4" [(ngModel)]="bonusMalus.redCard.A" class="form-control chk" type="number">
                        </div>
                        <div class="col-lg-2 col-md-1 col-sm-1 col-xs-1"><i class="info-ico" rel="tooltip" title="" data-original-title="Inserisci per ogni ruolo il valore dell'espulsione"></i></div>
                    </div>
                    <div class="row">
                        <label class="col-lg-4 col-md-4 col-sm-4 col-xs-4 control-label" for="assist">Assist:</label>
                        <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2">
                            <input name="ctl00$main$assist1" [(ngModel)]="bonusMalus.movementAssist.P" class="form-control chk" type="number">
                        </div>
                        <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2">
                            <input name="ctl00$main$assist2" [(ngModel)]="bonusMalus.movementAssist.D" class="form-control chk" type="number">
                        </div>
                        <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2">
                            <input name="ctl00$main$assist3" [(ngModel)]="bonusMalus.movementAssist.C" class="form-control chk" type="number">
                        </div>
                        <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2">
                            <input name="ctl00$main$assist4" [(ngModel)]="bonusMalus.movementAssist.A" class="form-control chk" type="number">
                        </div>
                        <div class="col-lg-2 col-md-1 col-sm-1 col-xs-1"><i class="info-ico" rel="tooltip" title="" data-original-title="Inserisci per ogni ruolo il valore dell'assist"></i></div>
                    </div>
                    <div class="row">
                        <label class="col-lg-4 col-md-4 col-sm-4 col-xs-4 control-label" for="assistfermo">Assist da fermo:</label>
                        <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2">
                            <input name="ctl00$main$assistfermo1" [(ngModel)]="bonusMalus.stationaryAssist.P" class="form-control chk" type="number">
                        </div>
                        <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2">
                            <input name="ctl00$main$assistfermo2" [(ngModel)]="bonusMalus.stationaryAssist.D" class="form-control chk" type="number">
                        </div>
                        <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2">
                            <input name="ctl00$main$assistfermo3" [(ngModel)]="bonusMalus.stationaryAssist.C" class="form-control chk" type="number">
                        </div>
                        <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2">
                            <input name="ctl00$main$assistfermo4" [(ngModel)]="bonusMalus.stationaryAssist.A" class="form-control chk" type="number">
                        </div>
                        <div class="col-lg-2 col-md-1 col-sm-1 col-xs-1"><i class="info-ico" rel="tooltip" title="" data-original-title="Inserisci per ogni ruolo il valore dell'assist da fermo (solo redazioni napoli e italia)"></i></div>
                    </div>
                    <div class="row">
                        <label class="col-lg-4 col-md-4 col-sm-4 col-xs-4 control-label" for="autogol">Autogol:</label>
                        <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2">
                            <input name="ctl00$main$autogol1" [(ngModel)]="bonusMalus.autogoal.P" class="form-control chk" type="number">
                        </div>
                        <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2">
                            <input name="ctl00$main$autogol2" [(ngModel)]="bonusMalus.autogoal.D" class="form-control chk" type="number">
                        </div>
                        <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2">
                            <input name="ctl00$main$autogol3" [(ngModel)]="bonusMalus.autogoal.C" class="form-control chk" type="number">
                        </div>
                        <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2">
                            <input name="ctl00$main$autogol4" [(ngModel)]="bonusMalus.autogoal.A" class="form-control chk" type="number">
                        </div>
                        <div class="col-lg-2 col-md-1 col-sm-1 col-xs-1"><i class="info-ico" rel="tooltip" title="" data-original-title="Inserisci per ogni ruolo il valore dell'autogol"></i></div>
                    </div>
                    <div class="row">
                        <label class="col-lg-4 col-md-4 col-sm-4 col-xs-4 control-label" for="autogol">Gol decisivo pareggio:</label>
                        <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2">
                            <input name="ctl00$main$golpareggio1" [(ngModel)]="bonusMalus.evenGoal.P" class="form-control chk" type="number">
                        </div>
                        <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2">
                            <input name="ctl00$main$golpareggio2" [(ngModel)]="bonusMalus.evenGoal.D" class="form-control chk" type="number">
                        </div>
                        <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2">
                            <input name="ctl00$main$golpareggio3" [(ngModel)]="bonusMalus.evenGoal.C" class="form-control chk" type="number">
                        </div>
                        <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2">
                            <input name="ctl00$main$golpareggio4" [(ngModel)]="bonusMalus.evenGoal.A" class="form-control chk" type="number">
                        </div>
                        <div class="col-lg-2 col-md-1 col-sm-1 col-xs-1"><i class="info-ico" rel="tooltip" title="" data-original-title="Inserisci per ogni ruolo il valore del gol decisivo pareggio"></i></div>
                    </div>
                    <div class="row">
                        <label class="col-lg-4 col-md-4 col-sm-4 col-xs-4 control-label" for="golvittoria">Gol decisivo vittoria:</label>
                        <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2">
                            <input name="ctl00$main$golvittoria1" [(ngModel)]="bonusMalus.winGoal.P" class="form-control chk" type="number">
                        </div>
                        <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2">
                            <input name="ctl00$main$golvittoria2" [(ngModel)]="bonusMalus.winGoal.D" class="form-control chk" type="number">
                        </div>
                        <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2">
                            <input name="ctl00$main$golvittoria3" [(ngModel)]="bonusMalus.winGoal.C" class="form-control chk" type="number">
                        </div>
                        <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2">
                            <input name="ctl00$main$golvittoria4" [(ngModel)]="bonusMalus.winGoal.A" class="form-control chk" type="number">
                        </div>
                        <div class="col-lg-2 col-md-1 col-sm-1 col-xs-1"><i class="info-ico" rel="tooltip" title="" data-original-title="Inserisci per ogni ruolo il valore del gol decisivo vittoria"></i></div>
                    </div>
                </div>
            </div>
        </div>

        <button class="btn btn-primary" (click) = "prova()">prova</button>




















        <form name="form" (ngSubmit)="saveCustomRules()" #f="ngForm" novalidate>

            <div class="row">
                <div class="col-lg-6">
                    <div class="form-group" >
                        <label for="sel1"> Numero massimo di sostituzioni d'ufficio </label>
                        <select class="form-control" id="sel1" name="maxOfficeVoteBehaviour" [(ngModel)]="model.maxOfficeVoteBehaviour" #maxOfficeVoteBehaviour="ngModel">
                            <option value = "TILL_SUBSTITUTIONS"> Fino al numero di sostituzioni impostato </option>
                            <option value = "TILL_11"> Fino a 11 </option>
                        </select>
                    </div> 

                </div>
                <div class="col-lg-6">
                    <div class="form-group" >
                        <label for="sel1"> In casi di rinvio o sospensione delle partite </label>
                        <select class="form-control" id="sel1" name="postponementBehaviour" [(ngModel)]="model.postponementBehaviour" #postponementBehaviour="ngModel">
                            <option value = "ALL_6"> Tutti 6 </option>
                            <option value = "WAIT_MATCHES"> Attesa dei recuperi </option>
                        </select>
                    </div> 
                </div>
            </div>
            
            
           

           

            <div class="form-group" >
                <label for="sel1"> Autogol </label>
                <select class="form-control" id="sel1" name="autogolActive" [(ngModel)]="model.autogolActive" #autogolActive="ngModel">
                    <option value = true> Si </option>
                    <option value = false> No </option>
                </select>
            </div>


        
            
            <div class="form-group">
                <button class="btn btn-primary">Salva</button>
            </div>

        </form>
    </div>
  `,
  styles: []
})
export class CustomRulesComponent implements OnInit {
    leagueShortName = null;
    competitionShortName = null;
    errorMessage = null;
    successMessage = null;

    model: any =
        {
            leagueShortName: null,
            competitionShortName: null,
            maxOfficeVoteBehaviour: "TILL_11",
            postponementBehaviour: "ALL_6",
            autogolActive: <boolean>false,
            autogol: 59,
            golSegnato1 : 3
        };

    bonusMalus: any = null;

    competitionBean = {
        leagueShortName : null,
        competitionShortName : null
    };



    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private leagueService: LeaguesService,

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

    prova(){
        console.log(this.bonusMalus);
    }

    retrieveCustomRules() {
        let retrieveRulesReq = {
            competition : {
                leagueShortName : this.leagueShortName,
                competitionShortName : this.competitionShortName
            }, 
            type : "CUSTOM"

        }
        // this.competitionBean.leagueShortName = this.leagueShortName;
        // this.competitionBean.competitionShortName = this.competitionShortName;
        this.leagueService.retrieveRules(retrieveRulesReq)
        .subscribe(
            data => {
                this.successMessage = "Le regole sono state recuperate";
                this.errorMessage = null;
                this.bonusMalus = data.bonusMalus;
            },

            error => {
                this.successMessage = null;
                this.errorMessage = "Errore di comunicazione col server"
            }

        )


    }



    saveCustomRules() {
        console.log(this.model);
        this.model.leagueShortName = this.leagueShortName;
        this.model.competitionShortName = this.competitionShortName;
        this.leagueService.integrateRules(this.model)
            .subscribe(
                data => {
                    this.successMessage = "Le regole sono state salvate";
                    this.errorMessage = null;

                },

                error => {
                    this.successMessage = null;
                    this.errorMessage = "Errore di comunicazione col server"
                });

        this.errorMessage = null;
    }

}
