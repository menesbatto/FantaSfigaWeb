import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable()
export class HeaderService {

  constructor() { }

    private messageSource = new BehaviorSubject<string>("Default message");
    currentMessage = this.messageSource.asObservable();

    changeMessage(message){
        this.messageSource.next(message);
    }


    private titleParamSource = new BehaviorSubject<string>("");
    currentTitleParam = this.titleParamSource.asObservable();

    changeTitleParam(titleParam){
        this.titleParamSource.next(titleParam);
    }


}
