import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, ParamMap } from '@angular/router';
import { LeaguesService } from '../leagues.service';
import { HeaderService } from '../header.service';

@Component({
  selector: 'app-report',
  templateUrl: 'report.component.html',
  styles: []
})
export class ReportComponent implements OnInit {

    leagueShortName = null;
    competitionShortName = null;
   
    model = {
        leagueShortName: null,
        competitionShortName: null
    };

    loading = null;
    successMessage = null;
    errorMessage = null;
    loadingMessage = null;
    competitionName = null;
    report = null;

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

            this.model = {
                leagueShortName: this.leagueShortName,
                competitionShortName: this.competitionShortName
            }

            this.retrieveReport();
            


        });

    }

    
    retrieveReport() {
        this.loading = true;
        this.successMessage = null;
        this.errorMessage = null;
        this.loadingMessage = "Recupero Statistiche in corso...";


        let competition = this.model;
          

        this.leagueService.retrieveReport(competition)
        .subscribe(data => {
            this.loadingMessage = null;
            this.successMessage = "Report recuperato";
            this.errorMessage = null;
            this.loading = false;
            this.report = data.report;

            this.competitionName = data.report.competition.name;
            
         },

            error => {
                this.loadingMessage = null;
                this.successMessage = null;
                this.errorMessage = "Errore comunicazione col server";
                this.loading = false;
            });
    }
}
