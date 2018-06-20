import { Component, Input, OnInit, ChangeDetectorRef } from '@angular/core';
import { LoginComponent } from './login/login.component';
import { AuthenticationService } from './authentication.service';
import { Router, NavigationStart, NavigationEnd, ParamMap } from '@angular/router';
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
    customPage:string;

    constructor(
        private authenticationService: AuthenticationService,
        private router: Router,
        public headerService: HeaderService,
        public spinnerService: SpinnerService,
        public cdRef: ChangeDetectorRef
        
        

    ) {

        this.headerService.customPageParam.subscribe(customPageParam => this.customPage = customPageParam);
      

        this.router.events.pairwise().subscribe((event) => {
            this.headerService.changeTitleParam(null);
            this.headerService.changeCustomPage(null);
            // console.log(event);
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




    closeSplashScreen() {
        this.spinnerService.setLoading(false);
        this.headerService.changeErrorMessage(null);
    }

    ngOnInit() {
        this.headerService.currentErrorMessage.subscribe(
            errorMessageInput => {
                this.errorMessage = errorMessageInput;
            });



        // this.spinnerService.onLoadingChanged.subscribe(
        //     loadingFromService => {

        //         this.isLoading = loadingFromService;
        //         // this.cdref.detectChanges();
        //     }
        // );
    }

    errorMessage: string = null;

}
