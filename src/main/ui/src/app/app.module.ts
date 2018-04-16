import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule, routingComponents } from './app-routing.module';

import { AppComponent } from './app.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { DepartmentDetailComponent } from './department-detail/department-detail.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { UserService } from './user.service';
import { LoginComponent } from './login/login.component';
import { FormsModule }   from '@angular/forms';
import { RegistrationComponent } from './registration/registration.component';
import { GazzettaCredentialsComponent } from './gazzetta-credentials/gazzetta-credentials.component';
import { LeaguesComponent } from './leagues/leagues.component';
import { LeaguesService } from './leagues.service';
import { AuthGuard } from './_guards/auth.guards';
import { CompetitionsComponent } from './competitions/competitions.component';
import { MyHttpLogInterceptor } from './http.interceptor';
import { CompetitionRulesComponent } from './competition-rules/competition-rules.component';
import { CompetitionComponent } from './competition/competition.component';




@NgModule({
  declarations: [
    AppComponent,
    routingComponents,
    PageNotFoundComponent,
    DepartmentDetailComponent,
    LoginComponent,
    RegistrationComponent,
    GazzettaCredentialsComponent,
    LeaguesComponent,
    CompetitionsComponent,
    CompetitionRulesComponent,
    CompetitionComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [UserService, 
              LeaguesService,
              AuthGuard,
              { provide: HTTP_INTERCEPTORS, useClass: MyHttpLogInterceptor, multi: true }],
  bootstrap: [AppComponent]
})
export class AppModule { }
