import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map'

import { Subject, BehaviorSubject } from "rxjs/Rx";

@Injectable()
export class AuthenticationService {
    constructor(private http: HttpClient) { }



    login(username: string, password: string) {
        return this.http.post<any>('/app/api/login', { username: username, password: password })
            .map(user => {
                // login successful if there's a jwt token in the response
                //if (user && user.token) {
                if (user) {
                    // store user details and jwt token in local storage to keep user logged in between page refreshes
                    localStorage.setItem('currentUser', JSON.stringify(user));
                    this.emit({user: user})
                }

                return user;
            });
    }

    isCustomerInSession(){
        var userString = localStorage.getItem('currentUser');
        var user = JSON.parse(userString);
        return user != null;
    }

    getUser(){
        var userString = localStorage.getItem('currentUser');
        var user = JSON.parse(userString);
        return user;
    }

    create(username: string, password: string, firstname: string, lastname: string, email: string) {
        return this.http.post<any>('/app/api/createUser', { username: username, 
                                                            password: password,
                                                            firstname: firstname,
                                                            lastname: lastname, 
                                                            email: email, 
                                                             })
        ;
    }


    saveGazzettaCredentials(username: string, password: string) {
        return this.http.post<any>('/app/api/saveFantaGazzettaCredentials', {   username: username, 
                                                                                password: password })
        ;
    }

    logout() {
        // remove user from local storage to log user out
        localStorage.removeItem('currentUser');
        localStorage.removeItem("alreadyDownloadInfo")
    }

    isAdmin() {
        var userString = localStorage.getItem('currentUser');
        var user = JSON.parse(userString);
        let isAdmin = user.role == "ADMIN";
        return isAdmin;
    }



    emit(value: any) {
        this.profile$.next(value);
      }

    profile$: Subject<any> = new BehaviorSubject<any>({});
  
    get profile(): BehaviorSubject<any> {
        return this.profile$ as BehaviorSubject<any>;
      }

   
}