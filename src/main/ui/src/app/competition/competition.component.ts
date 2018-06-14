import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, ParamMap } from '@angular/router';
import { LeaguesService } from '../leagues.service';
import { HeaderService } from '../header.service';
declare const $: any;


@Component({
    selector: 'app-competition',
    templateUrl: 'competition.component.html',
    styles: []
})


export class CompetitionComponent implements OnInit {


    totalEstimate = 10;
    ctx = { estimate: this.totalEstimate };
    ctx2 = { estimate: 20 };

    ctxRealRanking: any;
    ctxRealLightRanking: any;


    ctxFairRanking: any;
    ctxDeltaFairRanking: any;


    ctxPositionsRanking: any;
    ctxPositionsPercentaleRanking: any;
    ctxAveragePositionRanking: any;
    ctxDeltaPositionRankings: any;

    ctxInvertHomeAwayRanking: any;
    ctxLuckyEdgeRanking05: any;
    ctxLuckyEdgeRanking1: any;

    rulesType = null;

    leagueShortName = null;
    competitionShortName = null;
    competitionName = null;
    loadingMessage = null;
    errorMessage = null;
    successMessage = null;

    stats = null;

    teams = [];
    team1 = null;
    team2 = null;

    model = {
        leagueShortName: null,
        competitionShortName: null
    };



    // VIA 
    // message:string ="";
    titleParam: string = "";


    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private leagueService: LeaguesService,
        private headerService: HeaderService

    ) { }

   
    ngOnInit() {
       
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

    setSuccessMessage(text) {
        this.successMessage = text;
        $("#loading-alert").fadeTo(1000, 0).slideUp(1000);
        setTimeout(() => {
            $("#success-alert").fadeTo(1000, 0).slideUp(1000);

        }, 3000);
    }

    setLoadingMessage(text) {
        this.loadingMessage = text;
        $("#success-alert").fadeTo(1000, 0).slideUp(1000);
        setTimeout(() => {
            // $("#loading-alert").fadeTo(1000, 0).slideUp(1000);

        }, 3000);
    }

    setErrorMessage(text) {
        this.headerService.changeErrorMessage(text)
        // this.loadingMessage = text;
        // $("#success-alert").fadeTo(1000, 0).slideUp(1000);
        // setTimeout(() => {
        //     // $("#loading-alert").fadeTo(1000, 0).slideUp(1000);

        // }, 3000);
    }



    isAlreadyCalculated() {
        if (this.rulesType == "REAL") {
            let isStatslreadyCalculated = localStorage.getItem(this.competitionShortName + '-statsAlreadyCalculated');
            if (isStatslreadyCalculated == "true") {
                return true;
            }
        }
        else {
            return false;
        }
    }


    isAlreadyDownloaded() {
        let alreadyDownloaded = localStorage.getItem(this.competitionShortName + '-alreadyDownloadInfo');
        if (alreadyDownloaded == "true")
            return true;
        else
            return false;
    }

    downloadInfo() {
        this.setSuccessMessage(null);
        this.setErrorMessage(null);
        this.setLoadingMessage("Download dei risultati delle giornate mancanti e delle formazioni in corso...");
        this.leagueService.saveOnlineSeasonAndTeams(this.model).subscribe(
            data => {
                this.leagueService.downloadSeasonFromWeb(this.model)
                    .subscribe(
                        data => {
                            this.leagueService.calculateSeasonResult(this.model)
                                .subscribe(
                                    data => {
                                        this.setLoadingMessage(null);
                                        this.setSuccessMessage("Il ricalcolo dei risultati della stagione è stato eseguito");
                                        this.setErrorMessage(null);
                                        localStorage.setItem(this.competitionShortName + '-alreadyDownloadInfo', "true");
                                    },

                                    error => {
                                        this.setLoadingMessage(null);
                                        this.setSuccessMessage(null);
                                        this.setErrorMessage("Errore di comunicazione col server 2");
                                    });
                        },

                        error => {
                            this.setLoadingMessage(null);
                            this.setSuccessMessage(null);
                            this.setErrorMessage("Errore di comunicazione col server 1");
                        });
            },
            error => {
                this.setLoadingMessage(null);
                this.setSuccessMessage(null);
                this.setErrorMessage("Errore di comunicazione col server 3");

            });

    }

    retrieveAllRankings() {
        this.setSuccessMessage(null);
        this.setErrorMessage(null);
        this.setLoadingMessage("Recupero Statistiche in corso...");


        let req = {
            competition: this.model,
            rulesType: this.rulesType.toUpperCase()

        }

        this.leagueService.retrieveAllRankings(req).subscribe(data => {
            this.setLoadingMessage(null);
            this.setSuccessMessage("Statistiche recuperate");
            this.setErrorMessage(null);
            this.stats = data;
            this.competitionName = data.competition.name;

            this.ctxRealRanking = this.stats.realRanking;
            this.ctxRealLightRanking = this.stats.realLightRanking;


            this.ctxFairRanking = this.stats.fairRanking;
            this.ctxDeltaFairRanking = this.stats.deltaFairRanking;


            this.ctxPositionsRanking = this.stats.positionsRanking;
            this.ctxDeltaPositionRankings = this.stats.deltaPositionRankings;
            this.ctxAveragePositionRanking = this.stats.averagePositionRanking;
            this.ctxPositionsPercentaleRanking = this.stats.positionsPercentaleRanking;


            this.ctxInvertHomeAwayRanking = this.stats.invertHomeAwayRanking;
            this.ctxLuckyEdgeRanking05 = this.stats.luckyEdgeRanking05;
            this.ctxLuckyEdgeRanking1 = this.stats.luckyEdgeRanking1;

            this.headerService.changeTitleParam(this.competitionName);


            for (let i = 0; i < this.ctxRealRanking.rows.length; i++) {
                this.teams.push(this.ctxRealRanking.rows[i].name);
            }
            this.teams.sort();

        },

            error => {
                this.setLoadingMessage(null);
                this.setSuccessMessage(null);
                this.setErrorMessage("Devi ancora calcolare le statistiche");
            });
    }

    saveOnlineSeasonAndTeams() {
        this.setLoadingMessage("Download dei risultati delle giornate mancanti in corso...");

        this.leagueService.saveOnlineSeasonAndTeams(this.model).subscribe(
            data => {
                this.setLoadingMessage(null);
                this.setSuccessMessage("I risultati delle giornate mancanti sono stati scaricati");
                this.setErrorMessage(null);
            },
            error => {
                this.setLoadingMessage(null);
                this.setSuccessMessage(null);
                this.setErrorMessage("Errore di comunicazione col server 3");

            });

    }

    downloadSeasonFromWeb() {
        this.setSuccessMessage(null);
        this.setErrorMessage(null);
        this.setLoadingMessage("Download delle formazioni delle giornate mancanti in corso...");

        this.leagueService.downloadSeasonFromWeb(this.model)
            .subscribe(
                data => {
                    this.setLoadingMessage(null);
                    this.setSuccessMessage("Le formazioni delle giornate mancanti sono stati scaricati");
                    this.setErrorMessage(null);
                },

                error => {
                    this.setLoadingMessage(null);
                    this.setSuccessMessage(null);
                    this.setErrorMessage("Errore di comunicazione col server 1");
                });

        this.setErrorMessage(null);
    }


    calculateSeasonResult() {
        this.setSuccessMessage(null);
        this.setErrorMessage(null);
        this.setLoadingMessage("Ricalcolo dei risultati della stagione in corso...");

        this.leagueService.calculateSeasonResult(this.model)
            .subscribe(
                data => {
                    this.setLoadingMessage(null);
                    this.setSuccessMessage("Il ricalcolo dei risultati della stagione è stato eseguito");
                    this.setErrorMessage(null);
                },

                error => {
                    this.setLoadingMessage(null);
                    this.setSuccessMessage(null);
                    this.setErrorMessage("Errore di comunicazione col server 2");
                });

        this.setErrorMessage(null);
    }

    calculateRealStats(light) {
        this.setSuccessMessage(null);
        this.setErrorMessage(null);
        this.setLoadingMessage("Calcolo delle statistiche in corso...");
        let req = {
            light: light,
            rulesType: this.rulesType,
            competition: this.model
        };
        this.leagueService.calculateRealStats(req)
            .subscribe(
                data => {
                    this.setLoadingMessage(null);
                    this.setSuccessMessage("Calcolo delle statistiche terminato. Dati pronti");
                    this.setErrorMessage(null);
                    if (this.rulesType == "REAL")
                        localStorage.setItem(this.competitionShortName + '-statsAlreadyCalculated', "true");
                    this.retrieveAllRankings();
                },

                error => {
                    this.setLoadingMessage(null);
                    this.setSuccessMessage(null);
                    this.setErrorMessage("Errore di comunicazione col server 3");
                });

        this.setErrorMessage(null);
    }

    goToCompetitionRules(competition) {
        this.router.navigate(['/competitionRules', { league: this.leagueShortName, competition: this.competitionShortName }])
    }


    invertSeason() {


        let index1 = this.teams.indexOf(this.team1);
        let index2 = this.teams.indexOf(this.team2);

        let patternInit = "ABCDEFGHIKJLMNOP";
        let pattern = patternInit.substr(0, this.teams.length);

        let letter1 = pattern[index1];
        let letter2 = pattern[index2];

        pattern = pattern.slice(0, index1) + letter2 + pattern.slice(index1 + 1, pattern.length);
        pattern = pattern.slice(0, index2) + letter1 + pattern.slice(index2 + 1, pattern.length);

        this.goToSeason(pattern, "Invertita " + this.team1 + " - " + this.team2);

    }

    goToSeason(pattern, teamIn) {
        this.router.navigate(['/season', { league: this.leagueShortName, competition: this.competitionShortName, season: pattern, team: teamIn, rulesType: this.rulesType }])
    }

    goToSeasonNew(req) {
        let pattern = req.pattern;
        let teamIn = req.team;
        this.router.navigate(['/season', { league: this.leagueShortName, competition: this.competitionShortName, season: pattern, team: teamIn, rulesType: this.rulesType }])
    }


    goToCustomRules() {
        this.router.navigate(['/customRules', { league: this.leagueShortName, competition: this.competitionShortName }])
    }

    backToCompetitions() {
        this.router.navigate(['/competitions/', this.leagueShortName])

    }


    goToReport() {
        this.router.navigate(['/report', { league: this.leagueShortName, competition: this.competitionShortName }])
    }


}
