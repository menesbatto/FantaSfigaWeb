import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule, routingComponents } from './app-routing.module';

import { AppComponent } from './app.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { DepartmentDetailComponent } from './department-detail/department-detail.component';
import { HttpClientModule } from '@angular/common/http';
import { UserService } from './user.service';
import { LoginComponent } from './login/login.component';
import { FormsModule }   from '@angular/forms';
import { RegistrationComponent } from './registration/registration.component';
import { GazzettaCredentialsComponent } from './gazzetta-credentials/gazzetta-credentials.component';
import { LeaguesComponent } from './leagues/leagues.component';
import { LeaguesService } from './leagues.service';
import { AuthGuard } from './_guards/auth.guards';
import { CompetitionsComponent } from './competitions/competitions.component';




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
    CompetitionsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [UserService, LeaguesService, AuthGuard],
  bootstrap: [AppComponent]
})
export class AppModule { }
