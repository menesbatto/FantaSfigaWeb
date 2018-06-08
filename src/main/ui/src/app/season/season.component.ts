import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, ParamMap } from '@angular/router';
import { LeaguesService } from '../leagues.service';
import { HeaderService } from '../header.service';

@Component({
  selector: 'app-season',
  templateUrl: 'season.component.html',
  styles: []
})
export class SeasonComponent implements OnInit {

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private leagueService: LeaguesService,
        private headerService: HeaderService


    ) { }

    ctxSeason = { season: null };
    ctxRanking = { ranking: null };

    
    loadingMessage = null;
    errorMessage = null;
    successMessage = null;

    competitionShortName = null;
    leagueShortName = null;
    seasonPattern = null;
    team = null;
    rulesType = null;
    model = {
        leagueShortName: null,
        competitionShortName: null
    };

    ngOnInit() {
        this.route.paramMap.subscribe((params: ParamMap) => {
            let url1 = params.get('competition');
            this.competitionShortName = url1;

            let url2 = params.get('league');
            this.leagueShortName = url2;

            let url3 = params.get('season');
            this.seasonPattern = url3;

            let url4 = params.get('team');
            this.team = url4;

            let url5 = params.get('rulesType');
            this.rulesType = url5;

            this.model = {
                leagueShortName: this.leagueShortName,
                competitionShortName: this.competitionShortName
            }
            
            this.retrieveSeason(this.seasonPattern);
            

        });
  }

  retrieveSeason(pattern){
    this.loadingMessage = "Recupero della stagione in corso";
    this.successMessage = null;
    this.errorMessage = null;

    let req = {
        competition : this.model,
        pattern : pattern,
        rulesType : this.rulesType
    }
    this.leagueService.retrieveSeason(req) .subscribe(
        data => {
            this.loadingMessage = null;
            this.successMessage = "Stagione Recuperata";
            this.errorMessage = null;
            this.ctxSeason.season = data.season;
            this.ctxRanking = data.ranking;
            
            this.headerService.changeTitleParam(this.team);

            
        },
 
        error => {
            this.loadingMessage = null;
            this.successMessage = null;
            this.errorMessage = "Errore di comunicazione col server";
          
        });

  }

    goToCompetition(statsType){
        this. router.navigate(['/competition', {league : this.leagueShortName, competition : this.competitionShortName, type:statsType}])
    }

}
