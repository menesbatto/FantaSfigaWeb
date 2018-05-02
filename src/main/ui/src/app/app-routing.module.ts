import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { EmployeeListComponent } from './employee-list/employee-list.component';
import { DepartmentListComponent } from './department-list/department-list.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { DepartmentDetailComponent } from './department-detail/department-detail.component';
import { LoginComponent } from './login/login.component';
import { RegistrationComponent } from './registration/registration.component';
import { GazzettaCredentialsComponent } from './gazzetta-credentials/gazzetta-credentials.component';
import { LeaguesComponent } from './leagues/leagues.component';
import { AuthGuard } from './_guards/auth.guards';
import { CompetitionsComponent } from './competitions/competitions.component';
import { CompetitionRulesComponent } from './competition-rules/competition-rules.component';
import { CompetitionComponent } from './competition/competition.component';
import { CustomRulesComponent } from './custom-rules/custom-rules.component';
import { AdminComponent } from './admin/admin.component';

const routes: Routes = [
  {path: '', redirectTo: '/login', pathMatch : "full" },
  //{path: '', redirectTo: '/departments-list', pathMatch : "full" },
  //{path: '', canActivate: [AuthGuard] },
  {path: 'login', component: LoginComponent},
  {path: 'registration', component: RegistrationComponent},
  {path: 'gazzettaCredentials', component: GazzettaCredentialsComponent},
  {path: 'leagues', component: LeaguesComponent},
  {path: 'competitions/:id', component: CompetitionsComponent},
  {path: 'competitionRules', component: CompetitionRulesComponent},
  {path: 'competition', component: CompetitionComponent},
  {path: 'customRules', component: CustomRulesComponent},

  {path: 'admin', component: AdminComponent},

  {path: 'departments-list', component: DepartmentListComponent},
  {path: 'employees', component: EmployeeListComponent},
  {path: 'departments-list/:id', component: DepartmentDetailComponent},
  {path: '**', component: PageNotFoundComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
export const routingComponents = [DepartmentListComponent, 
                                  DepartmentDetailComponent,
                                  EmployeeListComponent, 
                                  PageNotFoundComponent,
                                  LoginComponent];
