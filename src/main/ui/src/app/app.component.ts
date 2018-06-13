import { Component, Input, OnInit, ChangeDetectorRef } from '@angular/core';
import { LoginComponent } from './login/login.component';
import { AuthenticationService } from './authentication.service';
import { Router, NavigationStart, NavigationEnd } from '@angular/router';
import { HeaderService } from './header.service';
import { SpinnerService } from './spinner.service';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

    title = 'app';
    isLoading: boolean = false;
   




    constructor(
        private authenticationService: AuthenticationService,
        private router: Router,
        private headerService: HeaderService,
        public spinnerService: SpinnerService,
        public cdRef:ChangeDetectorRef

    ) {

        

        this.router.events.pairwise().subscribe((event) => {
            headerService.changeTitleParam(null);
            console.log(event);
        });


        // this.router.events
        //     .filter(event => event instanceof NavigationStart)
        //     .subscribe(
        //         event => {
        //             this.isLoading = true
        //         }
        //     );

        // this.router.events
        //     .filter(event => event instanceof NavigationEnd)
        //     .subscribe(
        //         event => {
        //             this.isLoading = false
        //         });


       

       
    }

    ngAfterContentChecked() {

        
    
        this.cdRef.detectChanges();
        
            }

    ngOnInit() {


        // this.spinnerService.onLoadingChanged.subscribe(
        //     loadingFromService => {
               
        //         this.isLoading = loadingFromService;
        //         // this.cdref.detectChanges();
        //     }
        // );
 
      
    }
   

}
