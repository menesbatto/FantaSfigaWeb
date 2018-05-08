import { Component, Input, OnInit } from '@angular/core';
import { LoginComponent } from './login/login.component';
import { AuthenticationService } from './authentication.service';
import { Router } from '@angular/router';
import { HeaderService } from './header.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit  {
  
    title = 'app';
 
   
    constructor(
        private authenticationService: AuthenticationService,
        private router: Router,
        private headerService: HeaderService
        
      ) {
        this.router.events.pairwise().subscribe((event) => {
            headerService.changeTitleParam(null);
            console.log(event);
        });}

      ngOnInit() {
       
    } 
   
}
