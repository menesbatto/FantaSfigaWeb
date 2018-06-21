import { Component, OnInit, ViewChild } from '@angular/core';
import { LeaguesService } from '../leagues.service';
import { Router } from '@angular/router';
import { Observable, Subscription} from 'rxjs';
import { nullSafeIsEquivalent } from '@angular/compiler/src/output/output_ast';
import { MessageboxComponent } from '../messagebox/messagebox.component';


@Component({
    selector: 'app-leagues',
    template: `
    
    <div>
        <!--<h3 align = "center">Le tue leghe </h3>-->
        
        <div class="table-responsive">
            <table class="table">
            <tr *ngFor = "let league of leagueList" >
                <td> {{league.name}} </td>
                <td> <button  class="btn btn-primary" [disabled]="league.competitionsDownloaded"  (click)="downloadCompetitions(league)"> Scarica </button>  </td>
                <td> <button  class="btn btn-primary" [disabled]="!league.competitionsDownloaded || !league.rulesDownloaded"  (click) = "goToCompetitions(league)"> Vai </button> </td>
                <td> <button  class="btn btn-primary" (click) = "resetLeague(league)"> Reset </button> </td>
            <tr>
            </table>
        </div> 
        
      <!--<div class="alert alert-success" *ngIf="successMessage">
          <strong>{{successMessage}}</strong>
      </div>

      <div class="alert alert-success" *ngIf="loadingMessage" >
        <strong [innerHTML]= "loadingMessage"></strong>
      </div>

      <div class="alert alert-danger" *ngIf="errorMessage">
        <strong>{{errorMessage}}</strong>
      </div>-->
      <app-messagebox></app-messagebox>
      
  </div>

  <!--<td> <button  class="btn btn-primary"  (click)="success()"> success </button>  </td>
  <td> <button  class="btn btn-primary"  (click) = "loading()"> loading </button> </td>
  <td> <button  class="btn btn-primary" (click) = "loadingAdd()"> loadingAdd </button> </td>
  <td> <button  class="btn btn-primary" (click) = "error()"> error </button> </td>-->
 

  `,
    styles: []
})
export class LeaguesComponent implements OnInit {
    loading(league) {
        this.messagesBox.setMessage('loading', "Prova");
    }
    loadingAdd(league) {
        this.messagesBox.addMessage('loading', "<p>prova</p>");
    }
    error(league) {
        this.messagesBox.setMessage('error', "prova");
    }
    success(league) {
        this.messagesBox.setMessage('success', "prova");
    }
    constructor(
        private leagueService: LeaguesService,
        private router: Router,
    ) { }

    // loading = null;
    leagueList = [];
 
    sub:Subscription;

    @ViewChild(MessageboxComponent) messagesBox: MessageboxComponent;


    ngOnInit() {
        this.retrieveLeagues();
       
    }
    i = 0;
    messages = [
        "<p>Download regole Bonus/Malus...",
        "OK</p><p>Download regole Fonte dati...",
        "OK</p><p>Download regole Sostituzioni...",
        "OK</p><p>Download regole Punteggi...",
        "OK</p><p>Download regole Modificatori...",
        "OK</p><p>Download regole Competizione...",
        "OK</p><p>Salvataggio...</p>"
    ];

    startFakeServerUpdater(){
       


        this.sub = Observable.interval(3600)
            .subscribe(
                data => {
                    if (this.i < this.messages.length ){
                        this.messagesBox.addMessage('loading', this.messages[this.i]);
                        this.i++;
                    }
                },
                error => {
                    console.log(error);
                }) ;
    }
    
    stopFakeServerUpdater(){
        this.sub.unsubscribe();
    }

   
    goToCompetitions(league) {
        
        this.router.navigate(['/competitions', league.shortName])
    }
  
    resetLeague(league){
        this.messagesBox.setMessage('loading', 'Stiamo resettando la lega...');
        this.leagueService.resetLeague(league.shortName)
            .subscribe(
                data => {
                    this.messagesBox.setMessage('success', 'La lega è stata resettata');
                    league.competitionsDownloaded = false;
                    league.rulesDownloaded = false;
                },
                error => {
                })

    }
   


    downloadCompetitions(league) {
        this.messagesBox.addMessage('loading', '<p>Stiamo scaricando le competizioni delle lega selezionata...');
        this.leagueService.downloadCompetitions(league.shortName)
            .subscribe(
                data => {
                    this.messagesBox.addMessage('loading', 'OK</p>');
                    
                    
                    this.downloadCompetitionsCallback(league);

                },
                error => {
                })
    }


    retrieveLeagues() {
        this.leagueService.retrieveLeagues()
            .subscribe(
                data => {
                    this.leagueList = data
                },

                error => {
                });

    }

    onSelect(data) {
        console.log(data + " onSelect()")
    }

    downloadCompetitionsCallback(league) {
        league.competitionsDownloaded = true;
        this.messagesBox.setMessage('loading', '<p>Download delle regole in corso (impiega quale secondo)...</p>');
        this.startFakeServerUpdater();

        this.leagueService.downloadRules(league.shortName)
            .subscribe(
                data => {
                    this.messagesBox.setMessage('success', "Le regole della Lega '" + league.name + "' sono state scaricate.");
            
                    league.rulesDownloaded = true;
                    this.stopFakeServerUpdater();
                },
                error => {
                    this.messagesBox.setMessage('error', "Problemi durante il download delle regole della lega '" + league.name + "'");
                    league.rulesDownloaded = false;
                })


    }

}
