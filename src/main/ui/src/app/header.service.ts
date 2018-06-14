import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable()
export class HeaderService {

  constructor() { }

   

    private titleParamSource = new BehaviorSubject<string>("");
    currentTitleParam = this.titleParamSource.asObservable();

    changeTitleParam(titleParamInput){
        this.titleParamSource.next(titleParamInput);
    }


    private  errorMessageSource = new BehaviorSubject<string>("");
    currentErrorMessage = this.errorMessageSource.asObservable();

    changeErrorMessage(errorMessageInput){
        this.errorMessageSource.next(errorMessageInput);
    }


  
}
