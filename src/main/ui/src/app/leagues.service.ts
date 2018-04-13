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

}
