import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class AdminService {

    constructor(private http: HttpClient) { }

    downloadVotes() {
        return this.http.get<any>('/app/api/downloadVotes');
    }

    cleanVotes() {
        return this.http.get<any>('/app/api/cleanVotes');
    }

    insertPostponement(req) {
        return this.http.post<any>('/app/api/insertPostponement', req);
    }

    removePostponement(req) {
        return this.http.post<any>('/app/api/removePostponement', req);
    }
    
    retrievePostponements() {
        return this.http.get<any>('/app/api/retrievePostponements');
    }
    

    createPermutations(playerNumber) {
        return this.http.get<any>('/app/api/createPermutations/'+ playerNumber);
    }

    users(playerNumber) {
        return this.http.get<any>('/app/api/users');
    }




}
