import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable()
export class HeaderService {

    constructor() { }

    private customPageSource = new BehaviorSubject<string>("");
    customPageParam = this.customPageSource.asObservable();

    changeCustomPage(customPageInput) {
        this.customPageSource.next(customPageInput);
    }




    private titleParamSource = new BehaviorSubject<string>("");
    currentTitleParam = this.titleParamSource.asObservable();

    changeTitleParam(titleParamInput) {
        this.titleParamSource.next(titleParamInput);
    }


    private errorMessageSource = new BehaviorSubject<string>("");
    currentErrorMessage = this.errorMessageSource.asObservable();

    changeErrorMessage(errorMessageInput) {
        this.errorMessageSource.next(errorMessageInput);
    }




    private alreadyStatsCalculatedCompetitions: string[] = [];

    public removeCompetitionCalculated(competitionShortName: string) {
        let index = this.alreadyStatsCalculatedCompetitions.indexOf(competitionShortName);
        if (index > -1) {
            this.alreadyStatsCalculatedCompetitions.splice(index, 1);
        }
    }

    public addCompetitionCalculated(competitionShortName: string) {
        this.alreadyStatsCalculatedCompetitions.push(competitionShortName);
    }

    public isCompetitionAlreadyCalculated(competitionShortName: string) {
        let index = this.alreadyStatsCalculatedCompetitions.indexOf(competitionShortName);
        return index >= 0;
    }




    private alreadyDownloadedCompetitions: string[] = [];

    public removeCompetitionDownloaded(competitionShortName: string) {
        let index = this.alreadyDownloadedCompetitions.indexOf(competitionShortName);
        if (index > -1) {
            this.alreadyDownloadedCompetitions.splice(index, 1);
        }
    }

    public addCompetitionDownloaded(competitionShortName: string) {
        this.alreadyDownloadedCompetitions.push(competitionShortName);
    }

    public isCompetitionAlreadyDownloaded(competitionShortName: string) {
        let index = this.alreadyDownloadedCompetitions.indexOf(competitionShortName);
        return index >= 0;
    }



    
    reset(){
        this.alreadyStatsCalculatedCompetitions = [];
        this.alreadyDownloadedCompetitions = [];
    }



}
