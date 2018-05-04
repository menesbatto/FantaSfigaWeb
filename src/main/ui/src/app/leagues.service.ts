import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class LeaguesService {

  constructor(private http: HttpClient) { }

  downloadLeagues() {
    return this.http.get<any>('/app/api/downloadLeagues');
        
  }

  retrieveLeagues() {
    return this.http.get<any>('/app/api/retrieveLeagues');
        
  }

  downloadCompetitions(leagueShortName) {
    return this.http.get<any>('/app/api/downloadCompetitions/' + leagueShortName);
        
  }

  retrieveCompetitions(leagueShortName) {
    return this.http.get<any>('/app/api/retrieveCompetitions/' + leagueShortName);
        
  }

  downloadRules(leagueShortName) {
    return this.http.get<any>('/app/api/downloadRules/' + leagueShortName);
        
  }

  integrateRules(additionalRules){
    return this.http.post<any>('/app/api/integrateRules/', additionalRules);
  }


  calculateBinding(competitionBean){
    return this.http.post<any>('/app/api/calculateBinding/', competitionBean);
  }

  calculateCompetitionPattern(competitionBean){
    return this.http.post<any>('/app/api/calculateCompetitionPattern/', competitionBean);
  }

  saveOnlineSeasonAndTeams(competitionBean){
    return this.http.post<any>('/app/api/saveOnlineSeasonAndTeams/', competitionBean);
  }

  downloadSeasonFromWeb(competitionBean){
    return this.http.post<any>('/app/api/downloadSeasonFromWeb/', competitionBean);
  }

  calculateSeasonResult(competitionBean){
    return this.http.post<any>('/app/api/calculateSeasonResult/', competitionBean);
  }

  calculateRealStats(competitionBean){
    return this.http.post<any>('/app/api/calculateRealStats/', competitionBean);
  }

  retrieveAllRankings(competitionBean){
    return this.http.post<any>('/app/api/retrieveAllRankings/', competitionBean);
  }

  retrieveRules(retrieveRulesReq){
    return this.http.post<any>('/app/api/retrieveRules/', retrieveRulesReq);
  }

  saveCustomRules(retrieveRulesReq){
    return this.http.post<any>('/app/api/saveRules/', retrieveRulesReq);
  }
  
  retrieveSeason(req){
    return this.http.post<any>('/app/api/retrieveSeason', req);
  }
  
}
