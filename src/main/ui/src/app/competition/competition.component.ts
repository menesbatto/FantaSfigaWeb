import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, ParamMap } from '@angular/router';
import { LeaguesService } from '../leagues.service';
import { HeaderService } from '../header.service';

@Component({
    selector: 'app-competition',
    template: `
    <!-- prova su come si richiamanto components fratelli -->
    <!-- <span>message: {{message}}</span>
    <button class ="button" (click)="newMessage()"> prova a cambiare</button>
  -->
    <div  [ngClass]="{ 'customStats': rulesType && rulesType == 'CUSTOM'} ">
        <!--<h3 align="center">Competitione </h3>-->
        <!--<h3 align="center"><strong>{{competitionName | uppercase}}</strong></h3>-->

        <div>
            <div class="row nobor" >
                <div class="col-lg-10 col-md-10 col-sm-8 col-xs-8" >
                    <label>Scarica i dati</label>
                </div>
                <div class="col-lg-2 col-md-2 col-sm-4 col-xs-4">
                    <button [disabled]="loading || isAlreadyDownloaded() && false" class="btn btn-primary btn-block" (click) = "downloadInfo()"> OK </button>
                </div>
            </div>
                
            <div class="row nobor" >
                <div class="col-lg-10 col-md-10 col-sm-8 col-xs-8" >
                    <label>Report Errori</label>
                </div>
                <div class="col-lg-2 col-md-2 col-sm-4 col-xs-4">
                    <button [disabled]="loading" class="btn btn-primary btn-block" (click) = "goToReport()"> Vai </button>
                </div>
            </div>


            <!--<div class="row nobor">
                <div class="col-lg-10 col-md-10 col-sm-8 col-xs-8" >
                    <label>Scarica i risultati online delle partite giocate (dopo ogni giornata)</label>
                </div>
                <div class="col-lg-2 col-md-2 col-sm-4 col-xs-4">
                    <button [disabled]="loading" class="btn btn-primary btn-block" (click) = "saveOnlineSeasonAndTeams()"> OK </button>
                </div>
            </div>
            <div class="row nobor">
                <div class="col-lg-10 col-md-10 col-sm-8 col-xs-8">
                    <label> Scarica le formazioni inserite (dopo ogni giornata)</label>
                </div>
                <div class="col-lg-2 col-md-2 col-sm-4 col-xs-4">
                    <button [disabled]="loading" class="btn btn-primary btn-block" (click) = "downloadSeasonFromWeb()"> OK  </button>
                </div>
            </div>
            <div class="row nobor">
                <div class="col-lg-10 col-md-10 col-sm-8 col-xs-8">
                    <label> Ricalcola i risultati sulla base di quanto scaricato (dopo ogni giornata)</label>
                </div>
                <div class="col-lg-2 col-md-2 col-sm-4 col-xs-4">
                    <button [disabled]="loading" class="btn btn-primary btn-block" (click) = "calculateSeasonResult()"> OK  </button>
                </div>
            </div>-->
            
            
            <!--<div align = center> - - - - - </div>-->
            

            <!--<div class="row nobor">
                <div class="col-lg-10 col-md-10 col-sm-8 col-xs-8">
                    <label> Calcola statistiche leggere </label>
                </div>
                <div class="col-lg-2 col-md-2 col-sm-4 col-xs-4">
                    <button [disabled]="loading" class="btn btn-primary btn-block" (click) = "calculateRealStats(true)"> OK </button>
                </div>
            </div>-->
             <div class="row nobor">
                <div class="col-lg-10 col-md-10 col-sm-8 col-xs-8">
                    <label> Calcola statistiche pesanti (qualche secondo)</label>
                </div>
                <div class="col-lg-2 col-md-2 col-sm-4 col-xs-4">
                    <button [disabled]="loading || isAlreadyCalculated() && false" class="btn btn-primary btn-block" (click) = "calculateRealStats(false)"> OK </button>
                </div>
            </div>
            <!--<div class="row nobor">
                <div class="col-lg-10 col-md-10 col-sm-8 col-xs-8">
                    <label> Recupera le statistiche appena calcolate</label>
                </div>
                <div class="col-lg-2 col-md-2 col-sm-4 col-xs-4">
                    <button [disabled]="loading" class="btn btn-primary btn-block" (click) = "retrieveAllRankings()"> OK </button>
                </div>
            </div>-->

            
            <!--<div align = center> - - - - - </div>-->
            
            <div class="row nobor" *ngIf="rulesType=='REAL'">
                <div class="col-lg-10 col-md-10 col-sm-8 col-xs-8">
                    <label> Regole</label>
                </div>
                <div class="col-lg-2 col-md-2 col-sm-4 col-xs-4">
                    <button [disabled]="loading" class="btn btn-primary btn-block" (click) = "goToCompetitionRules()"> OK </button>
                </div>
            </div>

            <div class="row nobor" *ngIf="rulesType=='CUSTOM'">
                <div class="col-lg-10 col-md-10 col-sm-8 col-xs-8">
                    <label> Personalizza le regole</label>
                </div>
                <div class="col-lg-2 col-md-2 col-sm-4 col-xs-4">
                    <button [disabled]="loading" class="btn btn-primary btn-block" (click) = "goToCustomRules()"> OK </button>
                </div>
            </div>
           


        </div>

        <div class="alert alert-danger" *ngIf="errorMessage">
            <strong>{{errorMessage}}</strong>
        </div>
 
        <div class="alert alert-success" *ngIf="loadingMessage">
            <strong>{{loadingMessage}}</strong>
        </div>

        <div class="alert alert-success" *ngIf="successMessage">
            <strong>{{successMessage}}</strong>
        </div>

        
        <ng-template #rankingTable let-rankingIn="ranking">
        <div class="table-responsive" *ngIf= "rankingIn" >
            <table class="table table-striped">
                <thead class="thead-light">
                    <tr>
                        <th>Pos</th>
                        <th>Squadra</th>
                        <th *ngIf= "rankingIn.rows[0].points">Punti</th>
                        <th *ngIf= "rankingIn.rows[0].scoredGoals">Gol Fatti</th>
                        <th *ngIf= "rankingIn.rows[0].takenGoals">Gol Subiti</th>
                        <th *ngIf= "rankingIn.rows[0].sumAllVotes">Somma punteggio</th>
                        <th *ngIf= "rankingIn.rows[0].positions">1</th>
                        <th *ngIf= "rankingIn.rows[0].positions">2</th>
                        <th *ngIf= "rankingIn.rows[0].positions">3</th>
                        <th *ngIf= "rankingIn.rows[0].positions">4</th>
                        <th *ngIf= "rankingIn.rows[0].positions">5</th>
                        <th *ngIf= "rankingIn.rows[0].positions">6</th>
                        <th *ngIf= "rankingIn.rows[0].positions">7</th>
                        <th *ngIf= "rankingIn.rows[0].positions">8</th>
                        <th *ngIf= "rankingIn.rows[0].luckyEdge">Sculate</th>
                        <th *ngIf= "rankingIn.rows[0].luckyEdge">Punti Sculate</th>
                        <th *ngIf= "rankingIn.rows[0].luckyEdge">Sfighe</th>
                        <th *ngIf= "rankingIn.rows[0].luckyEdge">Punti persi</th>
                    </tr>
                </thead>
                <tbody>
                    <tr *ngFor = "let team of rankingIn.rows; index as i; ">
                        <td>{{i+1}}</td>
                        <td>{{team.name}}</td>
                        <td *ngIf= "team.points">{{team.points | number:'0.0-3'}}</td>
                        <td *ngIf= "team.scoredGoals">{{team.scoredGoals}}</td>
                        <td *ngIf= "team.takenGoals">{{team.takenGoals}}</td>
                        <td *ngIf= "team.sumAllVotes">{{team.sumAllVotes}}</td>
                        
                        <td *ngIf= "team.positions">
                            <button *ngIf="team.bestPosition== 1" class="btn btn-primary btn-block" (click) = "goToSeason(team.bestPattern, team.name)">{{team.positions[0]}}</button>
                            <button *ngIf="team.worstPosition== 1" class="btn btn-primary btn-block"(click) = "goToSeason(team.worstPattern, team.name)">{{team.positions[0]}}</button>
                            <span *ngIf="team.bestPosition!=1 && team.worstPosition!=1">{{team.positions[0]}}</span>
                        </td>
                        
                        <td *ngIf= "team.positions">
                            <button *ngIf="team.bestPosition== 2" class="btn btn-primary btn-block"(click) = "goToSeason(team.bestPattern, team.name)">{{team.positions[1]}}</button>
                            <button *ngIf="team.worstPosition== 2" class="btn btn-primary btn-block"(click) = "goToSeason(team.worstPattern, team.name)">{{team.positions[1]}}</button>
                            <span *ngIf="team.bestPosition!=2 && team.worstPosition!=2">{{team.positions[1]}}</span>
                        </td>
                       
                        <td *ngIf= "team.positions">
                            <button *ngIf="team.bestPosition==3" class="btn btn-primary btn-block"(click) = "goToSeason(team.bestPattern, team.name)">{{team.positions[2]}}</button>
                            <button *ngIf="team.worstPosition== 3" class="btn btn-primary btn-block"(click) = "goToSeason(team.worstPattern, team.name)">{{team.positions[2]}}</button>
                            <span *ngIf="team.bestPosition!=3 && team.worstPosition!=3">{{team.positions[2]}}</span>
                        </td>

                        <td *ngIf= "team.positions">
                            <button *ngIf="team.bestPosition== 4" class="btn btn-primary btn-block"(click) = "goToSeason(team.bestPattern, team.name)">{{team.positions[3]}}</button>
                            <button *ngIf="team.worstPosition== 4" class="btn btn-primary btn-block"(click) = "goToSeason(team.worstPattern, team.name)">{{team.positions[3]}}</button>
                            <span *ngIf="team.bestPosition!=4 && team.worstPosition!=4">{{team.positions[3]}}</span>
                        </td>

                        <td *ngIf= "team.positions">
                            <button *ngIf="team.bestPosition== 5" class="btn btn-primary btn-block"(click) = "goToSeason(team.bestPattern, team.name)">{{team.positions[4]}}</button>
                            <button *ngIf="team.worstPosition== 5" class="btn btn-primary btn-block"(click) = "goToSeason(team.worstPattern, team.name)">{{team.positions[4]}}</button>
                            <span *ngIf="team.bestPosition!=5 && team.worstPosition!=5">{{team.positions[4]}}</span>
                        </td>

                        <td *ngIf= "team.positions">
                            <button *ngIf="team.bestPosition== 6" class="btn btn-primary btn-block"(click) = "goToSeason(team.bestPattern, team.name)">{{team.positions[5]}}</button>
                            <button *ngIf="team.worstPosition== 6" class="btn btn-primary btn-block"(click) = "goToSeason(team.worstPattern, team.name)">{{team.positions[5]}}</button>
                            <span *ngIf="team.bestPosition!=6 && team.worstPosition!=6">{{team.positions[5]}}</span>
                        </td>

                        <td *ngIf= "team.positions">
                            <button *ngIf="team.bestPosition== 7" class="btn btn-primary btn-block"(click) = "goToSeason(team.bestPattern, team.name)">{{team.positions[6]}}</button>
                            <button *ngIf="team.worstPosition== 7" class="btn btn-primary btn-block"(click) = "goToSeason(team.worstPattern, team.name)">{{team.positions[6]}}</button>
                            <span *ngIf="team.bestPosition!=7 && team.worstPosition!=7">{{team.positions[6]}}</span>
                        </td>

                        <td *ngIf= "team.positions">
                            <button *ngIf="team.bestPosition== 8" class="btn btn-primary btn-block"(click) = "goToSeason(team.bestPattern, team.name)">{{team.positions[7]}}</button>
                            <button *ngIf="team.worstPosition== 8" class="btn btn-primary btn-block"(click) = "goToSeason(team.worstPattern, team.name)">{{team.positions[7]}}</button>
                            <span *ngIf="team.bestPosition!=8 && team.worstPosition!=8">{{team.positions[7]}}</span>
                        </td>
                        
                       
                        <td *ngIf= "team.luckyEdge">{{team.luckyEdge.luckyEdgeNumber}}</td>
                        <td *ngIf= "team.luckyEdge">{{team.luckyEdge.luckyEdgeGain}}</td>
                        <td *ngIf= "team.luckyEdge">{{team.luckyEdge.unluckyEdgeNumber}}</td>
                        <td *ngIf= "team.luckyEdge">{{team.luckyEdge.unluckyEdgeLose}}</td>
                     </tr>
                </tbody>
            </table>
        </div>
        
        </ng-template>



        <div class="container" *ngIf="ctxFairRanking.ranking">
            <h1 align="center"> FINALE </h1>

            <div class="row">
                <div class="col-lg-12">
                    <h3> Classifica Finale </h3>
                    <ng-container *ngTemplateOutlet="rankingTable;context:ctxRealRanking">
                    </ng-container>
                </div>
            </div>
        
        
            <div class="row">
                <div class="col-lg-12">
                    <h3> Classifica con fattore casa invertito </h3>
                    <ng-container *ngTemplateOutlet="rankingTable;context:ctxInvertHomeAwayRanking">
                    </ng-container>
                </div>
            </div>


            
            <h1 align="center"> PUNTI </h1>

            <div class="row">
                <div class="col-lg-4">
                    <h3> Classifica Reale </h3>
                    <ng-container *ngTemplateOutlet="rankingTable;context:ctxRealLightRanking">
                    </ng-container>
                </div>
                <div class="col-lg-4">
                    <h3> Classifica Giusta </h3>
                    <ng-container *ngTemplateOutlet="rankingTable;context:ctxFairRanking">
                    </ng-container>       
                </div>
                <div class="col-lg-4">
                    <h3> Differenza dai Punti giusti </h3>
                    <ng-container *ngTemplateOutlet="rankingTable;context:ctxDeltaFairRanking">
                    </ng-container>
                </div>
            </div>
        

            <BR>
            <BR>
            <BR>
            <BR>

            <h1 align="center"> POSIZIONI </h1>

            <div class="row">
                <div class="col-lg-12">
                    <h3> Classifica Posizioni </h3>
                    <ng-container *ngTemplateOutlet="rankingTable;context:ctxPositionsRanking">
                    </ng-container>       
                </div>
            </div>
            
            
            <div class="row">
                <div class="col-lg-12">
                    <h3> Classifica delle Posizioni Percentuali </h3>
                    <ng-container *ngTemplateOutlet="rankingTable;context:ctxPositionsPercentaleRanking">
                    </ng-container>
                </div>
            </div>

            <div class="row">
                <div class="col-lg-6">
                    <h3> Classifica Posizione Media </h3>
                    <ng-container *ngTemplateOutlet="rankingTable;context:ctxAveragePositionRanking">
                    </ng-container>
                </div>
                <div class="col-lg-6">
                    <h3> Classifica Differenza dalla Posizione Media </h3>
                    <ng-container *ngTemplateOutlet="rankingTable;context:ctxDeltaPositionRankings">
                    </ng-container>
                </div>
            </div>
        
          
        
            <BR>
            <BR>
            <BR>
            <BR>
            <h1 align="center"> DETTAGLI </h1>


            <div class="row">
                <div class="col-lg-12">
                    <h3> Classifica punti fatti o persi per mezzo punto </h3>
                    <ng-container *ngTemplateOutlet="rankingTable;context:ctxLuckyEdgeRanking05">
                    </ng-container>
                </div>
            </div>
        
            <BR>
            <BR>
            <BR>
            <BR>

            <div class="row">
            <div class="col-lg-12">
                <h3> Classifica punti fatti o persi per un punto </h3>
                <ng-container *ngTemplateOutlet="rankingTable;context:ctxLuckyEdgeRanking1">
                </ng-container>
            </div>
        </div>
    
        <BR>
        <BR>
        <BR>
        <BR>
        
        </div>
     

        <!--<ng-template #estimateTemplate let-lessonsCounter="estimate">
            <div> Approximately {{lessonsCounter}} lessons ...</div>
        </ng-template>
        
        
        <ng-container *ngTemplateOutlet="estimateTemplate;context:ctx">
        </ng-container>

        <ng-container *ngTemplateOutlet="estimateTemplate;context:ctx2">
        </ng-container>

        -->


        <!--<button class="btn btn-primary"(click) = "backToCompetitions()"> Torna alle competizioni </button>-->
       
    </div>

    `,
    styles: []
})
export class CompetitionComponent implements OnInit {
    
    totalEstimate = 10;
    ctx = { estimate: this.totalEstimate };
    ctx2 = { estimate: 20 };

    ctxRealRanking = { ranking: null };
    ctxRealLightRanking = { ranking: null };
    

    ctxFairRanking = { ranking: null };
    ctxDeltaFairRanking = { ranking: null };
    
    
    ctxPositionsRanking = { ranking: null };
    ctxPositionsPercentaleRanking = { ranking: null };
    ctxAveragePositionRanking = { ranking: null };
    ctxDeltaPositionRankings = { ranking: null };

    ctxInvertHomeAwayRanking = { ranking: null };
    ctxLuckyEdgeRanking05 = { ranking: null };
    ctxLuckyEdgeRanking1 = { ranking: null };

    rulesType = null;

    loading = false;
    leagueShortName = null;
    competitionShortName = null;
    competitionName = null;
    loadingMessage = null;
    errorMessage = null;
    successMessage = null;

    stats = null;

    model = {
        leagueShortName: null,
        competitionShortName: null
    };



    // VIA 
    // message:string ="";
    titleParam:string ="";


    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private leagueService: LeaguesService,
        private headerService: HeaderService

    ) { }
    
    //VIA
    //newMessage(){
    //    this.headerService.changeMessage("APPENA MOFIGICATO");
    //}

    ngOnInit() {
        //VIA
        //this.headerService.currentMessage.subscribe(message => this.message = message);
        //this.headerService.currentTitleParam.subscribe(titleParam => this.titleParam = titleParam);

        this.route.paramMap.subscribe((params: ParamMap) => {
            let url1 = params.get('competition');
            this.competitionShortName = url1;

            let url2 = params.get('league');
            this.leagueShortName = url2;

            let url3 = params.get('type');
            this.rulesType = url3;

            this.model = {
                leagueShortName: this.leagueShortName,
                competitionShortName: this.competitionShortName
            }

            this.retrieveAllRankings();
            


        });

    }

    

    isAlreadyCalculated(){
        if (this.rulesType=="REAL"){
            let isStatslreadyCalculated = localStorage.getItem(this.competitionShortName + '-statsAlreadyCalculated');
            if (isStatslreadyCalculated == "true"){
                return true;
            }
        }
        else {
            return false;
        }
    }


    isAlreadyDownloaded(){
        let alreadyDownloaded = localStorage.getItem(this.competitionShortName + '-alreadyDownloadInfo');
        if (alreadyDownloaded == "true")
            return true;
        else 
            return false;
    }

    downloadInfo(){
        this.successMessage=null;
        this.errorMessage=null;
        this.loadingMessage = "Download dei risultati delle giornate mancanti e delle formazioni in corso...";
        this.loading = true;
        this.leagueService.saveOnlineSeasonAndTeams(this.model).subscribe(
            data => {
                this.leagueService.downloadSeasonFromWeb(this.model)
                    .subscribe(
                        data => {
                            this.leagueService.calculateSeasonResult(this.model)
                            .subscribe(
                                data => {
                                    this.loadingMessage = null;
                                    this.successMessage = "Il ricalcolo dei risultati della stagione è stato eseguito";
                                    this.errorMessage = null;
                                    this.loading = false;
                                    localStorage.setItem(this.competitionShortName + '-alreadyDownloadInfo', "true");
                                },
                
                                error => {
                                    this.loadingMessage = null;
                                    this.successMessage = null;
                                    this.errorMessage = "Errore di comunicazione col server 2";
                                    this.loading = false;
                                });
                        },

                        error => {
                            this.loadingMessage = null;
                            this.successMessage = null;
                            this.errorMessage = "Errore di comunicazione col server 1";
                            this.loading = false;
                        });
            },
            error => {
              this.loadingMessage = null;
              this.successMessage = null;
              this.errorMessage = "Errore di comunicazione col server 3";
      
         });
      
    }

    retrieveAllRankings() {
        this.loading = true;
        this.successMessage = null;
        this.errorMessage = null;
        this.loadingMessage = "Recupero Statistiche in corso...";


        let req = {
            competition : this.model,
            rulesType : this.rulesType.toUpperCase()
            
        } 

        this.leagueService.retrieveAllRankings(req).subscribe(data => {
            this.loadingMessage = null;
            this.successMessage = "Statistiche recuperate";
            this.errorMessage = null;
            this.loading = false;
            this.stats = data;
            this.competitionName = data.competition.name;
            
            this.ctxRealRanking.ranking = this.stats.realRanking;
            this.ctxRealLightRanking.ranking = this.stats.realLightRanking;
            

            this.ctxFairRanking.ranking = this.stats.fairRanking;
            this.ctxDeltaFairRanking.ranking = this.stats.deltaFairRanking;
            
            
            this.ctxPositionsRanking.ranking = this.stats.positionsRanking;
            this.ctxDeltaPositionRankings.ranking = this.stats.deltaPositionRankings;
            this.ctxAveragePositionRanking.ranking = this.stats.averagePositionRanking;
            this.ctxPositionsPercentaleRanking.ranking = this.stats.positionsPercentaleRanking;

            this.ctxInvertHomeAwayRanking.ranking =  this.stats.invertHomeAwayRanking;
            this.ctxLuckyEdgeRanking05.ranking =  this.stats.luckyEdgeRanking05;
            this.ctxLuckyEdgeRanking1.ranking =  this.stats.luckyEdgeRanking1;
        
            this.headerService.changeTitleParam(this.competitionName);

        },

            error => {
                this.loadingMessage = null;
                this.successMessage = null;
                this.errorMessage = "Devi ancora calcolare le statistiche";
                this.loading = false;
            });
    }

    saveOnlineSeasonAndTeams(){
        this.loadingMessage = "Download dei risultati delle giornate mancanti in corso...";

        this.leagueService.saveOnlineSeasonAndTeams(this.model).subscribe(
            data => {
              this.loadingMessage = null;
              this.successMessage = "I risultati delle giornate mancanti sono stati scaricati";
              this.errorMessage = null;
            },
            error => {
              this.loadingMessage = null;
              this.successMessage = null;
              this.errorMessage = "Errore di comunicazione col server 3";
      
         });
        
    }

    downloadSeasonFromWeb() {
        this.loading = true;
        this.successMessage = null;
        this.errorMessage = null;
        this.loadingMessage = "Download delle formazioni delle giornate mancanti in corso...";

        this.leagueService.downloadSeasonFromWeb(this.model)
            .subscribe(
                data => {
                    this.loadingMessage = null;
                    this.successMessage = "Le formazioni delle giornate mancanti sono stati scaricati";
                    this.errorMessage = null;
                    this.loading = false;
                },

                error => {
                    this.loadingMessage = null;
                    this.successMessage = null;
                    this.errorMessage = "Errore di comunicazione col server 1";
                    this.loading = false;
                });

        this.errorMessage = null;
    }


    calculateSeasonResult() {
        this.loading = true;
        this.successMessage = null;
        this.errorMessage = null;
        this.loadingMessage = "Ricalcolo dei risultati della stagione in corso...";

        this.leagueService.calculateSeasonResult(this.model)
            .subscribe(
                data => {
                    this.loadingMessage = null;
                    this.successMessage = "Il ricalcolo dei risultati della stagione è stato eseguito";
                    this.errorMessage = null;
                    this.loading = false;
                },

                error => {
                    this.loadingMessage = null;
                    this.successMessage = null;
                    this.errorMessage = "Errore di comunicazione col server 2";
                    this.loading = false;
                });

        this.errorMessage = null;
    }

    calculateRealStats(light) {
        this.loading = true;
        this.successMessage = null;
        this.errorMessage = null;
        this.loadingMessage = "Calcolo delle statistiche in corso...";
        let req = {
            light : light,
            rulesType : this.rulesType,
            competition : this.model
        };
        this.leagueService.calculateRealStats(req)
            .subscribe(
                data => {
                    this.loadingMessage = null;
                    this.successMessage = "Calcolo delle statistiche terminato. Dati pronti";
                    this.errorMessage = null;
                    this.loading = false;
                    if (this.rulesType == "REAL")
                        localStorage.setItem(this.competitionShortName + '-statsAlreadyCalculated', "true");
                    this.retrieveAllRankings();
                },

                error => {
                    this.loadingMessage = null;
                    this.successMessage = null;
                    this.errorMessage = "Errore di comunicazione col server 3";
                    this.loading = false;
                });

        this.errorMessage = null;
    }

    goToCompetitionRules(competition){
        this. router.navigate(['/competitionRules', {league : this.leagueShortName, competition : this.competitionShortName}])
    }

    goToSeason(pattern, teamIn) {
        this. router.navigate(['/season', {league : this.leagueShortName, competition : this.competitionShortName, season: pattern, team:teamIn, rulesType: this.rulesType}])
    }

    
    goToCustomRules() {
        this. router.navigate(['/customRules', {league : this.leagueShortName, competition : this.competitionShortName}])
    }

    backToCompetitions() {
        this.router.navigate(['/competitions/', this.leagueShortName])

    }

    
    goToReport() {
        this. router.navigate(['/report', {league : this.leagueShortName, competition : this.competitionShortName}])
    }
}
