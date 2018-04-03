import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { IUserBean } from './userBean';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';

@Injectable()
export class UserService {

  constructor(private http: HttpClient) { }

  private _url: string  = "/app/api/users";  

  getUsers(): Observable<IUserBean[]>{

    return this.http.get<IUserBean[]>(this._url).catch(this.errorHandler);
    
  }

  errorHandler(error: HttpErrorResponse){
    return Observable.throw(error.message || "Server Error");
  }
 
}
