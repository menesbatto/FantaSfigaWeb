import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule, routingComponents } from './app-routing.module';
import { NgbModule} from '@ng-bootstrap/ng-bootstrap';
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
import { MyLoaderInterceptor } from './http.interceptor';
import { CompetitionRulesComponent } from './competition-rules/competition-rules.component';
import { CompetitionComponent } from './competition/competition.component';
import { CustomRulesComponent } from './custom-rules/custom-rules.component';
import { AdminComponent } from './admin/admin.component';
import { AdminService } from './admin.service';
import { AuthenticationService } from './authentication.service';
import { SeasonComponent } from './season/season.component';
import { HeaderComponent } from './header/header.component';
import { HeaderService } from './header.service';
import { ReportComponent } from './report/report.component';
import { TableComponent } from './table/table.component';
import { PostponementsComponent } from './postponements/postponements.component';
import { ModalComponent } from './modal2/modal.component';
import { SpinnerService } from './spinner.service';
import { MessageboxComponent } from './messagebox/messagebox.component';





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
    CompetitionComponent,
    CustomRulesComponent,
    AdminComponent,
    SeasonComponent,
    HeaderComponent,
    ReportComponent,
    TableComponent,
    PostponementsComponent,
    ModalComponent,
    MessageboxComponent
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
              AdminService,
              AuthenticationService,
              HeaderService,
              SpinnerService,
              { provide: HTTP_INTERCEPTORS, useClass: MyLoaderInterceptor, multi: true
              }],
              
  bootstrap: [AppComponent]
})
export class AppModule { }
