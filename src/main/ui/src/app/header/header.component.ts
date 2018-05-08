import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { AuthenticationService } from '../authentication.service';

@Component({
  selector: 'app-header',
  template: `
<nav class="navbar navbar-light bg-faded">

    <div>
        <div class="col-lg-1 col-md-1 col-sm-3 col-xs-3">
            <a class="btn btn-sm btn-primary">
                <span  (click)="backClicked()" class="glyphicon glyphicon-chevron-left"> </span>
            </a>
        </div>
        <div class="center col-lg-10 col-md-10 col-sm-6 col-xs-6">
            <!--<span *ngIf="getUsername()!= null"> Ciao {{getUsername()}}</span>-->
        </div>
        <div class="col-lg-1 col-md-1 col-sm-3 col-xs-3">
            <button *ngIf="isCustomerInSession()" class="btn btn-primary " [routerLink]="['../']" routerLinkActive = "strong"> Logout </button>
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
                private authenticationService: AuthenticationService) {} 

    user = {username:"fantasfigato"};

    ngOnInit() {

        
        this.authenticationService.profile.subscribe(profile=>{
            //this.user = profile.user;
        },
        err=>{
            console.log(err);
        });
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
    
   
}
