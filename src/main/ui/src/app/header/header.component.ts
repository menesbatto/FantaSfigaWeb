import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { AuthenticationService } from '../authentication.service';
import { HeaderService } from '../header.service';

@Component({
  selector: 'app-header',
  template: `
<nav class="header navbar navbar-light bg-faded">

    <div class= "row">
        <div class="col-lg-1 col-md-1 col-sm-3 col-xs-3" >
            <a class="btn btn-sm btn-primary"  *ngIf= "existsBack()">
                <span  (click)="backClicked()" class="glyphicon glyphicon-chevron-left"> </span>
            </a>
        </div>
        <div class="center col-lg-10 col-md-10 col-sm-6 col-xs-6">
            <!--<span *ngIf="getUsername()!= null"> Ciao {{getUsername()}}</span>-->
            <h4 align = "center"> {{getPageName()}}</h4>
        </div>
        <div class="col-lg-1 col-md-1 col-sm-3 col-xs-3">
            <button *ngIf="isCustomerInSession()" class="btn btn-primary " [routerLink]="['../']" routerLinkActive = "strong"> Logout </button>
        </div>
    </div>

    <div class= "row">
        <div align = "center" class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
            <h4 align = "center"><strong> {{titleParam | uppercase}} </strong></h4>
        </div>
    </div>


    <!--<a class="navbar-brand" (click)="goHome()">iTunes Search App</a> 
    <ul class="nav navbar-nav">
        <li><button class="btn btn-primary " (click)="backClicked()"> ss </button></li>
        <li class="nav-item">
            <a class="nav-link" (click)="backClicked()">BACK</a> 
        </li>
        <li class="nav-item">
            <a class="nav-link" (click)="goHome()">Home</a> 
        </li>
        
        
    </ul>-->
</nav>
  `,
  styles: []
})
export class HeaderComponent implements OnInit {
    constructor(private router: Router, 
                private _location: Location,
                private authenticationService: AuthenticationService,
                private headerService: HeaderService
            ) {} 

    user = {username:"fantasfigato"};
    // VIA
    // message:string =null;
    titleParam:string = null;

    ngOnInit() {
        // VIA
        //this.headerService.currentMessage.subscribe(message => this.message = message);
        this.headerService.currentTitleParam.subscribe(titleParam => this.titleParam = titleParam);
        
        this.authenticationService.profile.subscribe(profile=>{
            //this.user = profile.user;
        },
        err=>{
            console.log(err);
        });
    }
            

    existsBack(){
        let path = this._location.path();
        return path!="/login";
    }

    backClicked() {
        this._location.back();
    }

    goHome() {
      this.router.navigate(['']); 
    }
  
    goBack() {
      this.router.navigate(['search']); 
    }

    isCustomerInSession(){
        return this.authenticationService.isCustomerInSession();
    }

    getUsername(){
        let user = this.authenticationService.getUser();
        return user.username;
    }



    namePages = {
        "login" : "Login",
        "registration" : "Registrazione",
        "gazzettaCredentials" : "Credenziali Gazzetta",
        "leagues" : "Leghe",
        "competitions" : "Competizioni della lega",
        "competitionRules" : "Integrazione regole",
        "competition" : "Statistiche della competizione",
        "customRules" : "Regole",
        "season" : "Stagione",
        "admin" : "Admin",
     }

    getParams(){
        let path = this._location.path();
        let currentRouteExtends = path.split(";")[0].substr(1);
        let currentRoute = currentRouteExtends.split("/")[0];
        let params = currentRouteExtends.split("/");
        let rulesType = null;
        for (var i=0; i<params.length; i++){
            var param = params[i];
            if (param.startsWith("type"))
                rulesType = param.substr(5);
        }

        return params
    }

    getParamRulesType(){
        let path = this._location.path();
        
        let params = path.split(";");
        let rulesType = null;
        for (var i=0; i<params.length; i++){
            var param = params[i];
            if (param.startsWith("type"))
                rulesType = param.substr(5);
        }
        return rulesType;
    }

    getPageName(){
        let path = this._location.path();
        let currentRouteExtends = path.split(";")[0].substr(1);
        let currentRoute = currentRouteExtends.split("/")[0];
        let title = this.namePages[currentRoute]
        if (this.getParamRulesType()=="CUSTOM")
            title = "Statistiche Custom della competizione";

        return title;
    }
    
   
}
