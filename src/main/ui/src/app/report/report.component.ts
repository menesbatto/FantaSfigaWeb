import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router, ParamMap } from '@angular/router';
import { LeaguesService } from '../leagues.service';
import { HeaderService } from '../header.service';
import { MessageboxComponent } from '../messagebox/messagebox.component';

@Component({
  selector: 'app-report',
  templateUrl: 'report.component.html',
  styles: []
})
export class ReportComponent implements OnInit {


    @ViewChild(MessageboxComponent) messagesBox: MessageboxComponent;

    leagueShortName = null;
    competitionShortName = null;
   
    model = {
        leagueShortName: null,
        competitionShortName: null
    };

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
            
            let url3 = params.get('type');
            let rulesType = url3;

            this.model = {
                leagueShortName: this.leagueShortName,
                competitionShortName: this.competitionShortName
            }

            
            this.retrieveReport();
            
            this.headerService.changeCustomPage(rulesType);
            

        });

    }

    
    retrieveReport() {
        this.messagesBox.setMessage('loading', 'Recupero Statistiche in corso...');
        this.messagesBox.setMessage('success', null);
                  

        let competition = this.model;
          

        this.leagueService.retrieveReport(competition)
        .subscribe(data => {
            this.messagesBox.setMessage('loading', null);
            this.messagesBox.setMessage('success', 'Report recuperato');
            this.report = data.report;

            this.competitionName = data.report.competition.name;
            
         },

            error => {
            });
    }
}
